# PetConnect 宠物社交 App

一个基于 iOS + Spring Boot 的宠物社交平台，支持宠物资料展示、附近宠物发现、社交互动、线下活动组织和宠物服务（寄养、洗澡、美容）预约功能。

## 项目结构

```
PetConnect-Project/
├── backend/          # Spring Boot 后端
├── iOS-App/          # iOS SwiftUI 前端
└── README.md         # 项目说明
```

## 技术栈

| 层级 | 技术栈 |
|------|--------|
| **后端** | Spring Boot 3.4, MySQL 8, JPA, MyBatis, Swagger |
| **iOS** | SwiftUI, URLSession, CoreLocation |

## 快速开始

### 后端启动

```bash
cd backend
./gradlew bootRun
```

访问 Swagger 文档: `http://localhost:8080/swagger-ui.html`

### iOS 启动

使用 Xcode 打开 `iOS-App` 目录并运行。

## 功能模块

| 模块 | 后端 API | iOS 视图 |
|------|----------|----------|
| 宠物管理 | 6 个端点 | HomeView, ProfileView |
| 社区动态 | 12 个端点 | FeedView, HomeView |
| 附近发现 | 4 个端点 | NearbyPetsView |
| 服务预约 | 14 个端点 | ServicesListView |
| 好友系统 | 7 个端点 | - |
| 消息功能 | 7 个端点 | - |
| 文件上传 | 3 个端点 | - |

**总计：53 个 API 端点**

## 详细文档

- [后端实现说明](backend/README_IMPLEMENTATION.md)
- [API 文档](http://localhost:8080/swagger-ui.html) (需先启动后端)

## 许可证

本项目仅供学习参考使用。
