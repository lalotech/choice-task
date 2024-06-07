#FROM maven:3.8.4-openjdk-8-slim AS build

#WORKDIR /app
#COPY pom.xml .
#COPY src ./src

#RUN mvn package -DskipTests

FROM tomcat:8.5-jdk8-openjdk-slim

#COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/


#FROM tomcat:8.5-jdk8-openjdk
#ADD target/spring-mvc-rest.war /usr/local/tomcat/webapps/spring-mvc-rest.war
EXPOSE 8080
CMD ["catalina.sh", "run"]