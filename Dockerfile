FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY build/libs/*.jar app.jar

EXPOSE 19096
ENTRYPOINT ["java", "-jar", "app.jar"]




## 1단계: 빌더 스테이지 (소스 코드를 컴파일하고 JAR 파일을 만듦)
 ## JDK 환경은 빌드에만 사용됩니다.
 #FROM gradle:8.4.0-jdk17-alpine AS builder
 #
 ## 작업 디렉토리 설정
 #WORKDIR /app
 #
 ## Gradle 관련 파일 복사 (의존성 캐싱을 위해 먼저 복사)
 #COPY gradlew .
 #COPY gradle gradle
 #COPY build.gradle settings.gradle .
 #
 ## 소스 코드 복사
 #COPY src src
 #
 ## 빌드 실행
 #RUN chmod +x ./gradlew
 #RUN ./gradlew bootJar --no-daemon
 #
 ## 2단계: 실행 스테이지 (경량 JRE 환경)
 ## 실행에 필요한 JRE 환경만 사용됩니다 (이미지 크기 대폭 감소).
 #FROM openjdk:17-jre-slim
 #
 ## 포트 노출 (application.yml의 기본 포트와 일치)
 #EXPOSE 19096
 #
 ## 빌더 스테이지에서 만든 JAR 파일 복사
 #ARG JAR_FILE=/app/build/libs/*.jar
 #COPY --from=builder ${JAR_FILE} app.jar
 #
 ## 컨테이너 실행 명령어
 #ENTRYPOINT ["java", "-jar", "/app.jar"]