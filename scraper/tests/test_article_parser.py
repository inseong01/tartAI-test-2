from bs4 import BeautifulSoup
from scraper.article_parser import parse_article_tag

FULL_HTML = """
<article>
  <div class="uicore-post-wrapper">
    <a href="https://example.com/?p=123">link</a>
  </div>

  <h4 class="uicore-post-title">
    <span>테스트 제목</span>
  </h4>

  <div class="uicore-cover-img" style="background-image: url(https://example.com/image.jpg);"></div>

  <div class="uicore-post-category">
    <a href="https://example.com/category/dev" title="View Dev posts">Dev</a>
  </div>

  <div class="uicore-post-info">
    <p>  발췌   내용   테스트  </p>
  </div>

  <a rel="author" href="https://example.com/author/admin">Admin</a>

  <div class="uicore-post-footer">
    <span>meta</span>
    <span>2026-02-12</span>
  </div>
</article>
"""

EMPTY_HTML = "<article></article>"


def test_article_parser_full_data():
    soup = BeautifulSoup(FULL_HTML, "html.parser")
    article = soup.find("article")

    result = parse_article_tag(article)

    assert result == {
        "id": "123",
        "url": "https://example.com/?p=123",
        "title": "테스트 제목",
        "thumbnail": "https://example.com/image.jpg",
        "category": {
            "name": "Dev",
            "url": "https://example.com/category/dev",
        },
        "excerpt": "발췌 내용 테스트",
        "author": {
            "name": "Admin",
            "url": "https://example.com/author/admin",
        },
        "published_date": "2026-02-12",
    }


def test_article_parser_empty_article():
    soup = BeautifulSoup(EMPTY_HTML, "html.parser")
    article = soup.find("article")

    result = parse_article_tag(article)

    assert result == {
        "id": "",
        "url": "",
        "title": "",
        "thumbnail": "",
        "category": {
            "name": "",
            "url": "",
        },
        "excerpt": "",
        "author": {
            "name": "",
            "url": "",
        },
        "published_date": "",
    }
