# ---- STEP 1: Build Spring Boot app ----
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /build
COPY AOS_BE .

# ✅ Cấp quyền thực thi cho mvnw
RUN chmod +x mvnw

# Sử dụng Maven Wrapper để build
RUN ./mvnw clean package -DskipTests


# ---- STEP 2: Run built JAR app ----
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy JAR file từ build stage
COPY --from=builder /build/target/*.jar app.jar

# Khởi chạy ứng dụng
CMD ["java", "-jar", "app.jar"]
