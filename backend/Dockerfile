# 第一阶段：构建阶段
FROM eclipse-temurin:20-jdk-alpine AS build
WORKDIR /app

# 复制 gradle 相关文件
COPY gradle gradle
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .

# 赋予执行权限并预下载依赖（利用 Docker 缓存）
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

# 复制源码并构建
COPY src src
RUN ./gradlew bootJar --no-daemon

# 第二阶段：运行阶段
FROM eclipse-temurin:20-jre-alpine
WORKDIR /app

# 复制构建好的 jar 包
# 注意：生成的 jar 文件名通常是 {project-name}-0.0.1-SNAPSHOT.jar
COPY --from=build /app/build/libs/*.jar app.jar

# 设置时区为亚洲/上海
RUN apk add --no-cache tzdata
ENV TZ=Asia/Shanghai

# 开放端口
EXPOSE 8080

# 启动命令
# 使用生产环境配置文件启动（假设您会创建 application-prod.yml）
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
