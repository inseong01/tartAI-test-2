import pytest
from bs4 import BeautifulSoup
from scraper.url_utils import get_author_tag, parsing_author_name, parsing_author_url

HTML_WITH_AUTHOR = """
<article>
    <a rel="author" href="/author/test">테스트 작성자</a>
</article>
"""

HTML_WITHOUT_AUTHOR = """
<article>
    <a href="/author/test">테스트 작성자</a>
</article>
"""

HTML_WITHOUT_TEXT = """
<article>
    <a rel="author" href="/author/test"></a>
</article>
"""

HTML_WITHOUT_HREF = """
<article>
    <a rel="author"></a>
</article>
"""


@pytest.mark.parametrize(
    "html, expected_a",
    [
        (HTML_WITH_AUTHOR, True),
        (HTML_WITHOUT_AUTHOR, None),
    ],
)
def test_get_author_tag(html, expected_a):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    tag = get_author_tag(article)
    if expected_a is True:
        assert tag.name == "a"
        assert tag.get("rel") == ["author"]
    else:
        assert tag is None


@pytest.mark.parametrize(
    "html, expected_name",
    [
        (HTML_WITH_AUTHOR, "테스트 작성자"),
        (HTML_WITHOUT_AUTHOR, ""),
        (HTML_WITHOUT_TEXT, ""),
    ],
)
def test_parsing_author_name(html, expected_name):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert parsing_author_name(article) == expected_name


@pytest.mark.parametrize(
    "html, expected_url",
    [
        (HTML_WITH_AUTHOR, "/author/test"),
        (HTML_WITHOUT_AUTHOR, ""),
        (HTML_WITHOUT_HREF, ""),
    ],
)
def test_parsing_author_url(html, expected_url):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert parsing_author_url(article) == expected_url
