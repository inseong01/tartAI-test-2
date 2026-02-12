import pytest

from scraper.url_utils import get_articles
from scraper.exceptions.scraper_error import ScraperError

HTML_WITH_ARTICLES = """
<html>
  <body>
    <article class="uicore-post">Post 1</article>
    <article class="uicore-post">Post 2</article>
  </body>
</html>
"""


@pytest.mark.parametrize(
    "html_text, expected_count",
    [
        (HTML_WITH_ARTICLES, 2),
    ],
)
def test_success_get_articles(html_text, expected_count):
    articles = get_articles(html_text)
    assert len(articles) == expected_count
    assert all(a.name == "article" for a in articles)
    assert all("uicore-post" in a.get("class", []) for a in articles)


HTML_WITHOUT_ARTICLES = """
<html>
  <body>
    <div>No posts here</div>
  </body>
</html>
"""


@pytest.mark.parametrize(
    "html_text",
    [
        (HTML_WITHOUT_ARTICLES),
    ],
)
def test_fail_get_articles(html_text):
    with pytest.raises(ScraperError) as exc:
        get_articles(html_text)

    assert exc.value.code == "NO_ARTICLES_FOUND"
    assert (
        exc.value.message
        == "HTML 문서에서 'article.uicore-post' 요소를 찾을 수 없습니다."
    )
    assert exc.value.detail["html_length"] == len(html_text)
