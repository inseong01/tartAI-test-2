# PRAP 사이트 스크래핑 API

이 프로젝트는 **PRAP 사이트의 게시글을 스크래핑하여 JSON API로 제공**하는 Spring Boot 기반 애플리케이션입니다.

- Kotlin + Spring Boot 사용
- `Render` 배포

## 디렉터리 구성

```
test-2/
├─ scraper/         ← Python 스크래퍼
├─ api/             ← Spring API
└─ README.md        ← 저장소 README
```

## 스크래퍼

특정 URL에서 게시글 목록과 세부 데이터를 추출하는 모듈입니다.

[Scraper 리드미 보기](./scraper/README.md)

## API

스크래핑 데이터를 JSON 형식으로 제공하는 REST API입니다.

- GET /api/articles → 전체 게시글 목록 조회
- DTO 기반 응답, 예외 처리:
  - 클라이언트 요청 오류 → 400/404/405
  - 서비스/검증 오류 → 500

[API 리드미 보기](./api/README.md)

## 배포

이 프로젝트는 `Render`에 배포되어 있으며, 스크래핑 API를 바로 사용 가능합니다.

**배포 URL**

[https://prap-api-d2q7.onrender.com](https://prap-api-d2q7.onrender.com)

**사용 예시**

전체 게시글 조회

```bash
curl https://prap-api-d2q7.onrender.com/api/articles
```

**참고 사항**

`Render` 무료 인스턴스는 일정 시간 비활성 시 서버가 스핀다운 됩니다. 따라서 첫 요청은 50초 이상 지연될 수 있습니다.
