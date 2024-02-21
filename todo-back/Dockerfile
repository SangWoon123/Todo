FROM openjdk:17-jdk
# ec2 배포시 jar파일 위치 고려
ARG JAR_FILE=./*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java","-jar","/app.jar" ]