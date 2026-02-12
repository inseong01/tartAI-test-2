import pytest
from bs4 import BeautifulSoup
from scraper.url_utils import parsing_post_url, get_post_url_tag, parsing_id

HTML_WITH_URL = """
<article>
    <div class="uicore-post-wrapper">
        <a href="https://example.com/?p=12345">Post Link</a>
    </div>
</article>
"""

HTML_WITHOUT_WRAPPER = """
<article>
    <div class="other-class">
        <a href="https://example.com/?p=12345">Post Link</a>
    </div>
</article>
"""

HTML_WITHOUT_A = """
<article>
    <div class="uicore-post-wrapper">
        <span>No link here</span>
    </div>
</article>
"""

HTML_WITHOUT_HREF = """
<article>
    <div class="uicore-post-wrapper">
        <a>No href attribute</a>
    </div>
</article>
"""


@pytest.mark.parametrize(
    "html, expected_url",
    [
        (HTML_WITH_URL, "https://example.com/?p=12345"),
        (HTML_WITHOUT_WRAPPER, None),
        (HTML_WITHOUT_A, None),
        (HTML_WITHOUT_HREF, None),
    ],
)
def test_get_post_url_tag(html, expected_url):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert get_post_url_tag(article) == expected_url


@pytest.mark.parametrize(
    "html, expected_url",
    [
        (HTML_WITH_URL, "https://example.com/?p=12345"),
        (HTML_WITHOUT_WRAPPER, ""),
        (HTML_WITHOUT_A, ""),
        (HTML_WITHOUT_HREF, ""),
    ],
)
def test_parsing_post_url(html, expected_url):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert parsing_post_url(article) == expected_url


@pytest.mark.parametrize(
    "html, expected_id",
    [
        (HTML_WITH_URL, "12345"),
        (HTML_WITHOUT_WRAPPER, ""),
        (HTML_WITHOUT_A, ""),
        (HTML_WITHOUT_HREF, ""),
    ],
)
def test_parsing_id(html, expected_id):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert parsing_id(article) == expected_id
