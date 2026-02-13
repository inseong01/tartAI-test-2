# API

웹에서 스크래핑한 데이터를 외부에 제공하는 API 모듈입니다.

## 기술 스택

- JDK 21
- Kotlin 2.2.21
- Spring Boot 4.0.2
- Gradle 9.3.0

## 실행 방법

<!-- 작성 필요 -->

## 프로젝트 구조

<!-- 세부 작성 필요 -->

```
api/
│
├── README.md                  # 프로젝트 소개 문서
└── src/
    ├── main/
    └── test/
```

## API 엔드포인트

| 엔드포인트           | HTTP 메서드 | 요청 파라미터 | 응답 예시    | 설명           |
|-----------------|----------|---------|----------|--------------|
| `/api/articles` | `GET`    | 없음      | JSON 리스트 | 전체 게시글 목록 조회 |

### 예시 응답

`GET` /api/articles

```
[
   {
    "id": "3027",
    "url": "https://prap.kr/?p=3027",
    "title": "앤디 워홀, 예술과 대중을 잇다",
    "thumbnail": "https://prap.kr/wp-content/uploads/2025/11/앤디워홀-03_00-650x421.png",
    "category": { "name": "프랩칼럼", "url": "https://prap.kr/?cat=5" },
    "excerpt": "팝아트의 황제, 앤디 워홀이 열었던 예술과 대중의 새로운 시대! 실크스크린 기법, 팝아트 철학, 그리고 대중과 잇는 공간 ‘더 팩토리’까지, 워홀이 예술의 틀을 뛰어넘은 방법들을 탐구해보세요.",
    "author": { "name": "prap", "url": "https://prap.kr/?author=2" },
    "published_date": "2025-11-25"
  },
  ...
]
```

## 동작 흐름

1. API 요청 수신
2. 스크래핑 서비스 호출
3. JSON 형식 리스트로 API 응답 반환

## 예외 처리 전략

- 클라이언트 요청 오류
    - 잘못된 엔드포인트 접근 → `404 Not Found`
    - 지원하지 않는 HTTP 메서드 요청 → `405 Method Not Allowed`

- 서비스 내부 오류
    - 스크래핑 서비스 실행 중 오류 발생 → `500 Internal Server Error`
    - API 서버 자체 오류 → `Render`에서 관리
