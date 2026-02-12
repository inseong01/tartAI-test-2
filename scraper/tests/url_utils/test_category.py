import pytest
from bs4 import BeautifulSoup
from scraper.url_utils import (
    get_category_tag,
    parsing_category_name,
    parsing_category_url,
)

HTML_WITH_CATEGORY = """
<article>
    <div class="uicore-post-category">
        <a href="/category/news" title="View News posts">News</a>
    </div>
</article>
"""

HTML_WITHOUT_DIV = """
<article>
    <div class="other-class">
        <a href="/category/news" title="View News posts">News</a>
    </div>
</article>
"""

HTML_WITHOUT_A = """
<article>
    <div class="uicore-post-category"></div>
</article>
"""

HTML_WITHOUT_TITLE = """
<article>
    <div class="uicore-post-category">
        <a href="/category/news"></a>
    </div>
</article>
"""

HTML_WITHOUT_HREF = """
<article>
    <div class="uicore-post-category">
        <a title="View News posts"></a>
    </div>
</article>
"""

HTML_WITH_INVALID_TITLE = """
<article>
    <div class="uicore-post-category">
        <a href="/category/news" title="Something else"></a>
    </div>
</article>
"""


@pytest.mark.parametrize(
    "html, expected_a",
    [
        (HTML_WITH_CATEGORY, True),
        (HTML_WITHOUT_DIV, None),
        (HTML_WITHOUT_A, None),
    ],
)
def test_get_category_tag(html, expected_a):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    tag = get_category_tag(article)
    if expected_a is True:
        assert tag.name == "a"
    else:
        assert tag is None


@pytest.mark.parametrize(
    "html, expected_name",
    [
        (HTML_WITH_CATEGORY, "News"),
        (HTML_WITHOUT_DIV, ""),
        (HTML_WITHOUT_A, ""),
        (HTML_WITHOUT_TITLE, ""),
        (HTML_WITH_INVALID_TITLE, ""),
    ],
)
def test_parsing_category_name(html, expected_name):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert parsing_category_name(article) == expected_name


@pytest.mark.parametrize(
    "html, expected_url",
    [
        (HTML_WITH_CATEGORY, "/category/news"),
        (HTML_WITHOUT_DIV, ""),
        (HTML_WITHOUT_A, ""),
        (HTML_WITHOUT_HREF, ""),
    ],
)
def test_parsing_category_url(html, expected_url):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert parsing_category_url(article) == expected_url
