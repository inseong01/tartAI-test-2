import pytest
from bs4 import BeautifulSoup
from scraper.url_utils import get_post_footer_tags, parsing_published_date

HTML_WITH_FOOTER = """
<article>
    <div class="uicore-post-footer">
        <span>작성자</span>
        <span>2026-02-12</span>
    </div>
</article>
"""

HTML_WITHOUT_DIV = """
<article>
    <div class="other-class">
        <span>2026-02-12</span>
    </div>
</article>
"""

HTML_WITHOUT_SPAN = """
<article>
    <div class="uicore-post-footer"></div>
</article>
"""


@pytest.mark.parametrize(
    "html, expected_div",
    [
        (HTML_WITH_FOOTER, True),
        (HTML_WITHOUT_DIV, None),
        (HTML_WITHOUT_SPAN, True),
    ],
)
def test_get_post_footer_tags(html, expected_div):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    div = get_post_footer_tags(article)
    if expected_div is True:
        assert div.name == "div"
        assert "uicore-post-footer" in div.get("class", [])
    else:
        assert div is None


@pytest.mark.parametrize(
    "html, expected_date",
    [
        (HTML_WITH_FOOTER, "2026-02-12"),
        (HTML_WITHOUT_DIV, ""),
        (HTML_WITHOUT_SPAN, ""),
    ],
)
def test_parsing_published_date(html, expected_date):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert parsing_published_date(article) == expected_date
