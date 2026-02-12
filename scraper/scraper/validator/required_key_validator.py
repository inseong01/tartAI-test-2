from scraper.exceptions.invalid_article_error import InvalidArticleDataError


def validate_required_key(data: dict[str, any]):
    required_keys = [
        "id",
        "url",
        "title",
        "excerpt",
        "thumbnail",
        "published_date",
    ]

    for key in required_keys:
        value = data.get(key)
        if value == "":
            raise InvalidArticleDataError(
                code="INVALID_ARTICLE_DATA",
                message="필수 값이 비어 있습니다.",
                detail={"key": key},
            )
