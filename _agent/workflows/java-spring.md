---
description: 针对 Java Spring Boot 架构的代码开发与重构专家
---

# Java Spring Boot Workflow

I will help you build and refactor Spring Boot components following the layered architecture (Controller -> Service -> Repository/Mapper).

## Guardrails
- **No Logic in Controllers**: Keep controllers thin, move business logic to Services.
- **DTO Separation**: Use DTOs for data transfer, never expose Entities directly to the API.
- **Persistence Patterns**: Identify if the task involves JPA or MyBatis and match the existing pattern.
- **Lombok Usage**: Use @Data, @Builder, and @AllArgsConstructor as per project conventions.

## Steps

### 1. Analyze Core Entity
Check `backend/src/main/java/com/example/learnclaudedemo/entity/` for relevant classes.

### 2. Scaffold Layers
- Create/Update DTO in `dto/`.
- Implement Service interface and its `impl`.
- If MyBatis: Update Mapper interface and XML in `resources/mapper/`.
- If JPA: Update Repository interface.

### 3. Implement Controller
- Add REST endpoints with proper @Tag, @Operation (Swagger).
- Ensure `Result<T>` wrapper is used for all responses.

### 4. Verification
- Run `./gradlew compileJava` to check for syntax errors.
- Propose a unit test using `@SpringBootTest` or `@MockBean`.
