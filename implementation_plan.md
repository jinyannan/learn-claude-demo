# Implementation Plan: 宠物社交“发现大厅” (Google Premium 风格)

## 1. 目标概述
基于 Google Material 3 及高级美学标准，实现宠物社交应用的核心功能——**发现大厅 (Discovery Feed)**。
本阶段涵盖后端的数据存储与 API 设计，以及 iOS (SwiftUI) 平台的高质感组件还原。

## 2. 后端设计 (Java Spring Boot)

### 2.1 数据模型 (Entities)
我们将主要基于现有的或新建的动态相关表结构：
*   **`CommunityPost` (社区动态)**: `id`, `user_id`, `pet_id`, `content`, `location`, `created_at`
*   **`PostImage` (动态图片)**: `id`, `post_id`, `image_url`, `aspect_ratio`
*   **`Interaction` (互动数据)**: `id`, `post_id`, `user_id`, `type` (LIKE, COMMENT)

### 2.2 API 规范 (RESTful Endpoints)
*   **[GET] `/api/v1/feed`**: 获取大厅信息流 (支持分页 `page`, `size`)。
    *   *响应示例*: 包含发布者信息、宠物信息、文案、图片列表、点赞数、评论数。
*   **[POST] `/api/v1/posts`**: 发布新的宠物动态。
    *   *请求示例*: 包含文案、图片 URL 列表、定位信息。
*   **[POST] `/api/v1/posts/{postId}/like`**: 点赞/取消点赞动态。

## 3. iOS 前端设计 (SwiftUI + MVVM)

### 3.1 核心视图与组件结构
*   **`FeedView`**: 发现大厅主视图，包含顶部玻璃拟态导航栏和瀑布流/列表布局。
*   **`PostCardView`**: 封装单条动态的 Material 3 卡片组件：
    *   使用深圆角 (`cornerRadius: 24`)。
    *   底部使用分级阴影 (`radius: 12, y: 8`, 透明度极低) 营造悬浮感。
    *   头部：发布者头像、名称、时间。
    *   主体：高质量大图展示 (考虑到不同比例裁切的视觉优化)。
    *   底部：互动操作区 (点赞、评论、分享)，使用柔和色调的图标。
*   **`FloatingActionButton (FAB)`**: 右下角显眼的“大爪印”悬浮按钮，带有品牌主色的渐变发光效果。

### 3.2 视图模型 (ViewModel)
*   **`FeedViewModel`**:
    *   职责：从 `PostService` 拉取分页数据，处理点赞交互状态，管理加载中 (Loading) 与错误视图。

### 3.3 样式系统 (Design System / Theme)
*   **色彩 (Colors)**:
    *   Primary: 橙色爪印渐变 (如 `LinearGradient` 从浅橙到深珊瑚色)。
    *   Background: 极浅的暖白 (`#FAFAFA` 或带一点黄色调的乳白) 而非死白。
    *   Card Background: 纯白，依靠阴影分离。
*   **字体 (Typography)**:
    *   选用现代无衬线字体 (如系统默认且排版清晰的 SF Pro，重度加粗标题)。

## 4. 实施步骤拆解 (3-Step Roadmap)

*   **Step 1**: 后端 Entity & Repository 搭建，完成基础接口编写，并用 Swagger 生成接口测试。
*   **Step 2**: iOS 端的基础网络层 (`NetworkManager`)、模型 (`Models`) 及 ViewModel 编写。
*   **Step 3**: 集中精力打磨 UI，实现 `PostCardView` 的高保真还原与连贯的滚动体验。

## 5. 潜在风险与注意事项
*   **图片加载性能**: 发现大厅含有大量高清图片，iOS 端必须使用 `AsyncImage` 或三方库（如 Kingfisher）进行内存和缓存管理。
*   **长列表性能**: 即使在使用 `LazyVStack` 的前提下，卡片内多层叠放可能导致掉帧，需优化视图层级。
