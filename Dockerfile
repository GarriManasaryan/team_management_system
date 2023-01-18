FROM bellsoft/liberica-openjdk-debian:17

# скопировать все файлы из локальной машины в app
COPY build/output-docker/team-management-system.jar /app/

# задать рабоч директорию
WORKDIR /app

ENTRYPOINT ["java", "-jar" ,"team-management-system.jar"]