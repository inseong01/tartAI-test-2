import pytest
from bs4 import BeautifulSoup
from scraper.url_utils import parsing_title

HTML_WITH_TITLE = """
<article>
    <h4 class="uicore-post-title">
        <span>테스트 제목</span>
    </h4>
</article>
"""

HTML_WITHOUT_H4 = """
<article>
    <div>제목 없음</div>
</article>
"""

HTML_WITHOUT_SPAN = """
<article>
    <h4 class="uicore-post-title"></h4>
</article>
"""


@pytest.mark.parametrize(
    "html, expected_title",
    [
        (HTML_WITH_TITLE, "테스트 제목"),
        (HTML_WITHOUT_H4, ""),
        (HTML_WITHOUT_SPAN, ""),
    ],
)
def test_parsing_title(html, expected_title):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert parsing_title(article) == expected_title
