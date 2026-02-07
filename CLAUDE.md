# CLAUDE.md

本文件为 Claude Code (claude.ai/code) 在此代码库中工作时提供指导。

## 项目概述

PetConnect 是一个全栈宠物社交应用，包含：
- **后端**: Spring Boot 3.4.0 REST API + MySQL 8
- **前端**: iOS SwiftUI 应用，采用 MVVM 架构

应用功能包括：宠物档案、社区动态、好友关系、即时消息和宠物服务预订（寄养、美容、遛狗）。

## 开发命令

### 后端 (Spring Boot + Gradle)
```bash
cd backend

# 构建项目
./gradlew build

# 运行应用（默认端口 8080）
./gradlew bootRun

# 运行测试
./gradlew test

# 打包可执行 JAR
./gradlew bootJar
```

### 数据库
- MySQL 8，运行在 `localhost:3306`
- 数据库名: `learn_claude`
- 开发环境启用 DDL 自动更新 (`spring.jpa.hibernate.ddl-auto=update`)

## 架构

### 后端结构
```
backend/src/main/java/com/example/learnclaudedemo/
├── controller/          # REST API 端点（共 53 个）
├── service/            # 业务逻辑接口
├── service/impl/       # 服务实现
├── entity/             # JPA 实体（User, Pet, CommunityPost, FriendRelation, Message, ServicePublish, ServiceOrder, UserAddress, SysConfig）
├── repository/         # JPA 仓库
├── dto/                # 数据传输对象
└── config/             # 配置（CORS、异常处理、文件上传）
```

**关键架构决策：**
- **双 ORM**: 同时使用 JPA（主要实体）和 MyBatis（特定查询）
- **统一响应格式**: 所有 API 响应封装在 `Result<T>` 类中
- **全局异常处理**: `GlobalExceptionHandler` 统一处理错误
- **CORS 已启用**: 配置前后端通信

### iOS 结构
```
iOS-App/
├── Models/             # 匹配后端实体的数据模型
├── Network/            # 基于 URLSession 的 API 客户端层
├── ViewModels/         # MVVM 的 ViewModel，负责状态管理
├── Views/              # SwiftUI 视图（首页、发现、社区、服务、我的）
├── ContentView.swift   # 主标签页视图
└── PetConnectApp.swift # 应用入口
```

## API 模块

| 模块 | 端点数 | 用途 |
|--------|-----------|---------|
| `/api/pets` | 6 | 宠物档案 CRUD |
| `/api/posts` | 12 | 社区动态和评论 |
| `/api/friends` | 7 | 好友请求和关系 |
| `/api/messages` | 7 | 即时消息 |
| `/api/services` | 7 | 服务发布 |
| `/api/orders` | 7 | 服务预订订单 |
| `/api/location` | 4 | 附近的宠物和服务 |
| `/api/upload` | 3 | 文件/图片上传 |

交互式 API 文档地址：`http://localhost:8080/swagger-ui.html`

## 核心配置文件

- `backend/src/main/resources/application.properties` - 数据库连接、JPA 设置、文件上传限制
- `backend/build.gradle.kts` - 依赖和构建配置
- `backend/src/main/java/com/example/learnclaudedemo/config/` - 全局配置类

## 技术栈总结

**后端**: Spring Boot 3.4.0, Java 20, Gradle 9.3.0 (Kotlin DSL), MySQL 8, JPA + MyBatis, Swagger/OpenAPI 3, Lombok

**前端**: Swift, SwiftUI, iOS 14+, MVVM 模式, URLSession 网络请求, CoreLocation
