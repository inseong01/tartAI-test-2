import pytest
from bs4 import BeautifulSoup
from scraper.url_utils import parsing_thumbnail

HTML_WITH_THUMBNAIL = """
<article>
    <div class="uicore-cover-img" style="background-image: url(https://example.com/image.jpg);"></div>
</article>
"""

HTML_WITHOUT_DIV = """
<article>
    <div class="other-class"></div>
</article>
"""

HTML_WITHOUT_STYLE = """
<article>
    <div class="uicore-cover-img"></div>
</article>
"""

HTML_WITH_INVALID_STYLE = """
<article>
    <div class="uicore-cover-img" style="background-color: red;"></div>
</article>
"""


@pytest.mark.parametrize(
    "html, expected_url",
    [
        (HTML_WITH_THUMBNAIL, "https://example.com/image.jpg"),
        (HTML_WITHOUT_DIV, ""),
        (HTML_WITHOUT_STYLE, ""),
        (HTML_WITH_INVALID_STYLE, ""),
    ],
)
def test_parsing_thumbnail(html, expected_url):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert parsing_thumbnail(article) == expected_url
