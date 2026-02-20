# API

웹에서 스크래핑한 데이터를 외부에 제공하는 API 모듈입니다.

## 기술 스택

- JDK 21
- Kotlin 2.2.21
- Spring Boot 3.3.5
- Gradle 9.3.0

## 실행 방법

### 1. 프로젝트 위치 이동

```bash
cd api
```

### 2. 빌드

```bash
./gradlew build
```

### 3. 서버 실행

**방법 1. Gradle로 실행 (권장)**

```bash
./gradlew bootRun
```

**방법 2. 빌드된 Jar 실행**

빌드 후 생성된 파일을 실행합니다.

```bash
java -jar build/libs/api-0.0.1-SNAPSHOT.jar
```

### 4. API 확인

서버 실행 후 `Postman` 또는 브라우저에서 아래 주소로 확인할 수 있습니다.

```
http://localhost:8080/api/articles
```

## 테스트

### 테스트 실행 및 결과 확인

아래 명령어를 실행하면 테스트가 수행되며,
테스트 리포트와 커버리지 리포트가 함께 생성됩니다.

```bash
./gradlew test
```

실행이 완료되면 브라우저가 자동으로 열리며 다음 두 가지 리포트를 확인할 수 있습니다.

- 테스트 결과 리포트
- JaCoCo 커버리지 리포트

자동으로 열리지 않는 경우 아래 경로에서 직접 확인할 수 있습니다.

**테스트 리포트**

```
build/reports/tests/test/index.html
```

**커버리지 리포트**

```
build/reports/jacoco/test/html/index.html
```

### 커버리지만 확인하는 경우

커버리지만 다시 생성하려면 아래 명령어를 실행합니다.

```bash
./gradlew jacocoTestReport
```

**리포트 경로**

```
build/reports/jacoco/test/html/index.html
```

## 프로젝트 구조

```
api/
├── README.md                          # 프로젝트 문서
├── build.gradle.kts                   # Gradle 빌드 및 의존성 관리 설정
└── src/
    ├── main/
    │   ├── kotlin/com/prap/api/
    │   │   ├── config/                # 스프링 Bean 구성
    │   │   ├── controller/            # REST API 요청 처리
    │   │   ├── service/               # 비즈니스 로직 처리
    │   │   ├── dto/                   # 데이터 객체
    │   │   ├── exception/             # 예외 처리 관리
    │   │   └── python/                # 파이썬 스크래퍼 연동 로직
    │   └── resources/                 # 환경 설정 파일
    └── test/                          # 단위 및 통합 테스트 코드
```

## API 엔드포인트

| 엔드포인트      | HTTP 메서드 | 요청 파라미터 | 응답 예시   | 설명                  |
| --------------- | ----------- | ------------- | ----------- | --------------------- |
| `/api/articles` | `GET`       | 없음          | JSON 리스트 | 전체 게시글 목록 조회 |

### 예시 응답

**`GET` /api/articles**

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
  - 잘못된 엔드포인트 접근 → `400 Bad Request`
  - 지원하지 않는 HTTP 메서드 요청 → `405 Method Not Allowed`

- 서비스 내부 오류
  - 스크래핑 서비스 실행 중 오류 발생 → `500 Internal Server Error`
  - API 서버 자체 오류 → `Render`에서 관리
