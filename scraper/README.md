# 스크랩퍼

지정된 웹 페이지의 게시글 목록을 스크래핑하여 정형화된 JSON 데이터 리스트로 반환하는 모듈입니다.

## 기술 스택

- Python 3.12.7
- requests
- BeautifulSoup4
- pytest

## 실행 방법

명령어는 `/scraper` 디렉터리 위치에서 실행 가능함으로, 먼저 `/scraper` 디렉터리 위치로 이동해야 합니다.

```bash
cd scraper
```

### 1. 환경 준비

Python 3.10 이상의 환경에서 실행 가능합니다. 프로젝트마다 독립된 패키지 관리가 필요하므로 가상환경을 생성하고 활성화합니다.

#### 1-1. 가상환경 생성

```bash
python -m venv venv
```

#### 1-2. 가상환경 실행

**macOS/Linux**

```bash
source venv/bin/activate   # macOS/Linux
```

**Windows**

```bash
venv\Scripts\activate      # Windows
```

#### 1-3. 가상환경 종료

모든 작업이 종료 되었을 때 아래 명령어를 통해 원래 터미널 상태로 돌아갈 수 있습니다.

```bash
deactivate
```

### 2. 의존성 설치

```bash
pip install -r requirements.txt
```

### 3. 실행

```bash
python -m scraper.main
```

### 4. 테스트

단위 테스트 실행

```bash
pytest -v
```

## 프로젝트 구조

```
scraper/
│
├── README.md                  # 프로젝트 소개 문서
├── requirements.txt           # 필요한 패키지 목록
├── tests/                     # pytest 테스트 코드 모음
└── scraper/
    ├── exceptions/            # 커스텀 예외 클래스 정의
    ├── mock/                  # mock 데이터
    ├── validator/             # 데이터 검증 로직
    ├── article_parser.py      # HTML 기사 파싱 로직
    ├── loop_articles.py       # 기사 반복 처리 로직
    ├── main.py                # 실행 진입점
    ├── parsing_utils.py       # HTML 파싱 유틸리티 함수 모음
    └── url_utils.py           # URL 요청 처리 유틸리티 모음
```

## 데이터 구조 (DTO)

스크래핑 결과 리스트는 아래와 같은 구조를 가지고 있습니다.

```json
{
  "id": "string",
  "url": "string",
  "title": "string",
  "thumbnail": "string",
  "category": {
    "name": "string",
    "url": "string"
  },
  "excerpt": "string",
  "author": {
    "name": "string",
    "url": "string"
  },
  "published_date": "string"
}
```

### 필드 설명

| 필드           | 타입 | 설명                   |
| -------------- | ---- | ---------------------- |
| id             | str  | 게시글 고유 ID         |
| url            | str  | 게시글 상세 페이지 URL |
| title          | str  | 게시글 제목            |
| thumbnail      | str  | 썸네일 이미지 URL      |
| category.name  | str  | 카테고리 이름          |
| category.url   | str  | 카테고리 URL           |
| excerpt        | str  | 게시글 요약            |
| author.name    | str  | 작성자 이름            |
| author.url     | str  | 작성자 페이지 URL      |
| published_date | str  | 게시 날짜              |

## 동작 흐름

1. 카테고리 페이지 요청
2. 게시글 목록 HTML 파싱
3. 각 게시글 데이터 추출
   - 데이터 값 존재 확인
   - 데이터 값 유형 확인
4. JSON 리스트 반환

## 예외 처리 전략

- HTTP 요청 실패 시 예외 발생
- None 값은 빈 문자열로 변환
- 1차 검증: 필수 필드 누락 시 예외 발생
- 2차 검증: 날짜, URL 형식 검증 후 반환
