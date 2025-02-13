FROM openjdk:17-slim
MAINTAINER shvm.cloud
COPY target/BankAccountServices-1.0-SNAPSHOT.jar BankAccountServices-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/BankAccountServices-1.0-SNAPSHOT.jar"]