# 1-ci mərhələ: Rəsmi gradle imici ilə build edirik
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon

# 2-ci mərhələ: İşlətmək üçün təmiz amazoncorretto imici
FROM amazoncorretto:21
WORKDIR /app

# Wildcard (*) istifadə edirik ki, fayl adı necə olur-olsun avtomatik tapsın
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]