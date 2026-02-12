import pytest
from bs4 import BeautifulSoup
from scraper.url_utils import parsing_excerpt

HTML_WITH_EXCERPT = """
<article>
    <div class="uicore-post-info">
        <p>  이건   테스트   발췌 문장입니다.  </p>
    </div>
</article>
"""

HTML_WITHOUT_DIV = """
<article>
    <div class="other-class">
        <p>내용 있음</p>
    </div>
</article>
"""

HTML_WITHOUT_P = """
<article>
    <div class="uicore-post-info"></div>
</article>
"""


@pytest.mark.parametrize(
    "html, expected_excerpt",
    [
        (HTML_WITH_EXCERPT, "이건 테스트 발췌 문장입니다."),
        (HTML_WITHOUT_DIV, ""),
        (HTML_WITHOUT_P, ""),
    ],
)
def test_parsing_excerpt(html, expected_excerpt):
    soup = BeautifulSoup(html, "html.parser")
    article = soup.find("article")
    assert parsing_excerpt(article) == expected_excerpt
