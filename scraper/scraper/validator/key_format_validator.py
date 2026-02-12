from scraper.exceptions.scraper_error import ScraperError
from datetime import datetime
import re


def validate_key_format(data: dict[str, str]):
    url = data.get("url")
    validte_url(url)

    thumbnail = data.get("thumbnail")
    validte_thumbnail(thumbnail)

    published_date = data.get("published_date")
    validte_published_date(published_date)


def validte_url(url):
    if not url.startswith(("http://", "https://")):
        raise ScraperError(
            code="INVALID_ARTICLE_DATA",
            message="URL 형식이 올바르지 않습니다.",
            detail={"key": "url"},
        )


def validte_thumbnail(thumbnail):
    IMAGE_PATTERN = re.compile(
        r"^https?://.+\.(png|jpg|jpeg|webp|gif|svg)$",
        re.IGNORECASE,
    )

    if not IMAGE_PATTERN.fullmatch(thumbnail):
        raise ScraperError(
            code="INVALID_ARTICLE_DATA",
            message="섬네일 URL 형식이 올바르지 않습니다.",
            detail={"key": "thumbnail"},
        )


def validte_published_date(published_date):
    try:
        datetime.strptime(published_date, "%Y-%m-%d")
    except (ValueError, TypeError):
        raise ScraperError(
            code="INVALID_ARTICLE_DATA",
            message="날짜 형식이 올바르지 않습니다.",
            detail={"key": "published_date"},
        )
