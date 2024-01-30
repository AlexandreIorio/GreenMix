FROM eclipse-temurin:17

WORKDIR /app

COPY target/GreenMix-1.0-SNAPSHOT.jar /app/GreenMix-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "GreenMix-1.0-SNAPSHOT.jar" , "8080"]