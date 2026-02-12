import pytest

from scraper.exceptions.scraper_error import ScraperError
from scraper.loop_articles import loop_processing_articles

SUCCESS_CASE = (
    ["article0", "article1"],
    [
        {
            "id": "1",
            "url": "https://a.com",
            "title": "A",
            "excerpt": "ex",
            "thumbnail": "https://img.com/a.png",
            "published_date": "2026-01-01",
        },
        {
            "id": "2",
            "url": "https://b.com",
            "title": "B",
            "excerpt": "ex",
            "thumbnail": "https://img.com/b.png",
            "published_date": "2026-01-01",
        },
    ],
    2,
)

SCRAPPER_ERROR_CASE = (
    ["article0", "article1", "article2"],
    [
        {
            "id": "1",
            "url": "https://a.com",
            "title": "A",
            "excerpt": "ex",
            "thumbnail": "https://img.com/a.png",
            "published_date": "2026-01-01",
        },
        {
            "id": "",
            "url": "",
            "title": "",
            "excerpt": "",
            "thumbnail": "",
            "published_date": "",
        },
        {
            "id": "3",
            "url": "https://c.com",
            "title": "C",
            "excerpt": "ex",
            "thumbnail": "https://img.com/c.png",
            "published_date": "2026-01-01",
        },
    ],
    2,
)

ALL_VALUE_EMPTY_CASE = (
    ["article0", "article1"],
    [
        {
            "id": "",
            "url": "",
            "title": "",
            "excerpt": "",
            "thumbnail": "",
            "published_date": "",
        },
        {
            "id": "",
            "url": "",
            "title": "",
            "excerpt": "",
            "thumbnail": "",
            "published_date": "",
        },
    ],
    0,
)


@pytest.mark.parametrize(
    "articles, mock_parse_results, expected_articles_count",
    [
        SUCCESS_CASE,
        SCRAPPER_ERROR_CASE,
        ALL_VALUE_EMPTY_CASE,
    ],
)
def test_article_processing_loop(
    monkeypatch, articles, mock_parse_results, expected_articles_count
):
    # 모킹
    def mock_parse_article_tag(article):
        idx = int(article.replace("article", ""))
        return mock_parse_results[idx]

    monkeypatch.setattr(
        "scraper.loop_articles.parse_article_tag", mock_parse_article_tag
    )

    def mock_validate_required_key(data):
        if data["id"] == "":
            raise ScraperError(
                code="INVALID_ARTICLE_DATA",
                message="필수 값이 비어 있습니다.",
                detail={"key": "id"},
            )

    monkeypatch.setattr(
        "scraper.loop_articles.validate_required_key", mock_validate_required_key
    )
    monkeypatch.setattr("scraper.loop_articles.validate_key_format", lambda data: None)

    # 실행
    data = loop_processing_articles(articles)

    # 검사
    assert len(data) == expected_articles_count
    for article in data:
        for _, value in article.items():
            assert value != ""
