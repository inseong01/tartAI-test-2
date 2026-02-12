import pytest
from scraper.validator.key_format_validator import validate_key_format
from scraper.exceptions.scraper_error import ScraperError


VALID_URL_VALUE = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

VALID_THUMBNAIL_VALUE = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

VALID_PUBLISHED_DATE_1 = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

VALID_PUBLISHED_DATE_2 = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-1-3",
}


@pytest.mark.parametrize(
    "data",
    [
        (VALID_URL_VALUE),
        (VALID_THUMBNAIL_VALUE),
        (VALID_PUBLISHED_DATE_1),
        (VALID_PUBLISHED_DATE_2),
    ],
)
def test_success_validate_key_format(data):
    result = validate_key_format(data)
    assert result is None


INVALID_URL_VALUE = {
    "id": "3132",
    "url": "localhost:5173",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

INVALID_THUMBNAIL_VALUE = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "THIS_IS_URL",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

INVALID_PUBLISHED_DATE_1 = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-13-30",
}

INVALID_PUBLISHED_DATE_2 = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-01-00",
}


@pytest.mark.parametrize(
    "data, key",
    [
        (INVALID_URL_VALUE, "url"),
        (INVALID_THUMBNAIL_VALUE, "thumbnail"),
        (INVALID_PUBLISHED_DATE_1, "published_date"),
        (INVALID_PUBLISHED_DATE_2, "published_date"),
    ],
)
def test_fail_validate_key_format(data, key):
    with pytest.raises(ScraperError) as exc:
        validate_key_format(data)

    if key == "url":
        assert exc.value.code == "INVALID_ARTICLE_DATA"
        assert exc.value.message == "URL 형식이 올바르지 않습니다."
        assert exc.value.detail == {"key": "url"}

    elif key == "thumbnail":
        assert exc.value.code == "INVALID_ARTICLE_DATA"
        assert exc.value.message == "섬네일 URL 형식이 올바르지 않습니다."
        assert exc.value.detail == {"key": "thumbnail"}

    elif key == "published_date":
        assert exc.value.code == "INVALID_ARTICLE_DATA"
        assert exc.value.message == "날짜 형식이 올바르지 않습니다."
        assert exc.value.detail == {"key": "published_date"}
