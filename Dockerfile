# 베이스 이미지 jdk 17
FROM openjdk:17-jdk-alpine

COPY . /app
WORKDIR /app

# build/libs/PIN-1.0-SNAPSHOT.jar을 /app/PIN.jar로
COPY build/libs/PIN-1.0-SNAPSHOT.jar /app/PIN.jar

# 컨테이너로 띄울 때 항상 실행되어야 하는 명령어 java -jar /app/PIN.jar
ENTRYPOINT ["java", "-jar", "/app/PIN.jar"]
