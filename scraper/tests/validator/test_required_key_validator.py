import pytest
from scraper.validator.required_key_validator import validate_required_key
from scraper.exceptions.invalid_article_error import InvalidArticleDataError

SUCCESS = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

ID_EMPTY_VALUE = {
    "id": "",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

TITLE_EMPTY_VALUE = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

URL_EMPTY_VALUE = {
    "id": "3132",
    "url": "",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

THUMBNAIL_EMPTY_VALUE = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

EXCERPT_EMPTY_VALUE = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "2026-02-09",
}

PUBLISHED_DATE_EMPTY_VALUE = {
    "id": "3132",
    "url": "https://prap.kr/?p=3132",
    "title": "한돈 투자계약증권? 실물자산 투자의 시대!",
    "thumbnail": "https://prap.kr/wp-content/uploads/2026/02/한돈-투자계약증권_1-650x445.png",
    "category": {"name": "프랩칼럼", "url": "https://prap.kr/?cat=5"},
    "excerpt": "한우에 이어 한돈까지 확장된 투자계약증권! 실물자산 투자 구조부터 STO로 넓어지는 투자 가능성까지, 한돈 투자가 여는 새로운 투자 패러다임을 살펴보세요.",
    "author": {"name": "prap", "url": "https://prap.kr/?author=2"},
    "published_date": "",
}


@pytest.mark.parametrize(
    "data, should_raise, key",
    [
        (SUCCESS, False, None),
        (ID_EMPTY_VALUE, True, "id"),
        (TITLE_EMPTY_VALUE, True, "title"),
        (URL_EMPTY_VALUE, True, "url"),
        (THUMBNAIL_EMPTY_VALUE, True, "thumbnail"),
        (EXCERPT_EMPTY_VALUE, True, "excerpt"),
        (PUBLISHED_DATE_EMPTY_VALUE, True, "published_date"),
    ],
)
def test_validate_required_key(data, should_raise, key):
    if should_raise:
        with pytest.raises(InvalidArticleDataError) as exc:
            validate_required_key(data)

        assert exc.value.code == "INVALID_ARTICLE_DATA"
        assert exc.value.message == "필수 값이 비어 있습니다."
        assert exc.value.detail == {"key": key}

    else:
        result = validate_required_key(data)
        assert result is None
