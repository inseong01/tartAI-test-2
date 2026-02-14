# 1. Spring 빌드
FROM gradle:9.3.0-jdk21 AS builder
WORKDIR /app
COPY . .

WORKDIR /app/api
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

# 2. 실행 이미지
FROM eclipse-temurin:21-jre

# - ProcessBuilder 경로 설정
ENV SCRAPER_PATH=/app/scraper   

# - Python 설치
RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    apt-get clean

WORKDIR /app

# - Spring JAR 복사
COPY --from=builder /app/api/build/libs/*.jar app.jar

# - Scraper 복사
COPY scraper ./scraper

# - Python 의존성 설치
RUN pip3 install --no-cache-dir --break-system-packages -r scraper/requirements.txt

ENTRYPOINT ["java", "-jar", "app.jar"]
