---
description: 针对 iOS SwiftUI (MVVM) 的前端界面与逻辑开发专家
---

# iOS SwiftUI Workflow

I will help you create modern SwiftUI views and their associated ViewModels following the MVVM pattern.

## Guardrails
- **MVVM Strictness**: Never put business or network logic in the View. Use ViewModels.
- **State Management**: Use `@StateObject` or `@ObservedObject` for ViewModels.
- **UI Consistency**: Follow Apple's Human Interface Guidelines.
- **Networking**: Use the project's existing API client patterns.

## Steps

### 1. Define Model
Ensure the Swift Model matches the backend JSON/Entity structure.

### 2. Create ViewModel
- Implement `ObservableObject`.
- Add `@Published` properties for UI state (loading, data, error).
- Implement async fetch/post methods using `URLSession` or `Network` layer.

### 3. Build SwiftUI View
- Create the View file with `body: some View`.
- Use `VStack`, `HStack`, `List` etc. for layout.
- Bind the UI elements to the ViewModel.

### 4. Verification
- Ensure the code compiles in the Swift context.
- (Manual) User should open Xcode to verify Preview.
