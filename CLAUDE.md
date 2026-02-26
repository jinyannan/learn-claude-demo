# CLAUDE.md

本文件为 Claude Code / Antigravity 在此代码库中工作时提供指导。

## 项目概述

PetConnect 是一个全栈宠物社交生态系统，包含：
- **后端**: Spring Boot 3.4.0 REST API + MySQL 8 + RabbitMQ
- **移动端**: iOS SwiftUI 应用 (MVVM 架构)
- **Web 端**: React 应用 (位于 `web-app/`)
- **小程序**: 微信小程序 (位于 `miniprogram/`)

应用功能包括：宠物档案、社区动态、好友关系、即时消息、消息推送以及宠物服务预订（寄养、美容、遛狗）。

## 开发命令

### 后端 (Spring Boot + Gradle)
```bash
cd backend
./gradlew build    # 构建项目
./gradlew bootRun  # 运行应用（端口 8080）
./gradlew test     # 运行测试
```

### Web 应用 (React)
```bash
cd web-app
npm install  # 安装依赖
npm start    # 启动开发服务器（通常端口 3000）
npm run build # 构建生产版本
```

### 小程序 (WeChat Mini-program)
- 使用 **微信开发者工具** 导入 `miniprogram` 目录
- 调试预览：在开发者工具中点击“编译”

### 基础设施
- **MySQL**: `localhost:3306`, 数据库 `learn_claude`
- **RabbitMQ**: 默认 `localhost:5672`，管理后台 `http://localhost:15672` (guest/guest)
- **文件上传**: 存储在 `backend/uploads` 目录

## 架构

### 后端结构
`backend/src/main/java/com/example/learnclaudedemo/`
- `controller/`: REST API 端点
- `service/`: 业务逻辑接口与实现
- `entity/`: JPA 实体
- `repository/`: JPA 仓库 (Spring Data JPA)
- `mapper/`: MyBatis 映射接口与 XML (处理复杂查询)
- `config/`: RabbitMQ, CORS, Swagger 等配置

### 小程序结构
`miniprogram/`
- `pages/`: 页面组件与逻辑
- `utils/`: 工具函数
- `app.json`: 全局配置

### Web 端结构
`web-app/`
- `src/components/`: 可复用组件
- `src/App.js`: 入口文件

### iOS 结构
`iOS-App/` (SwiftUI + MVVM)

## 技术栈总结

- **后端**: Java 20, Spring Boot 3.4.0, Gradle, MySQL 8, JPA, MyBatis, RabbitMQ
- **前端 (Web)**: React, JSX, CSS
- **移动端**: Swift, SwiftUI (iOS 14+)
- **小程序**: 原生微信小程序框架 (WXML, WXSS, JS)

