import SwiftUI

struct ContentView: View {
    @State private var selectedTab = 0
    @StateObject private var authService = AuthService.shared

    var body: some View {
        if authService.isAuthenticated {
            TabView(selection: $selectedTab) {
                HomeView()
                    .tabItem {
                        Label("首页", systemImage: selectedTab == 0 ? "house.fill" : "house")
                    }
                    .tag(0)

                NearbyPetsView()
                    .tabItem {
                        Label("附近", systemImage: selectedTab == 1 ? "location.fill" : "location")
                    }
                    .tag(1)

                FeedView()
                    .tabItem {
                        Label("社区", systemImage: selectedTab == 2 ? "text.bubble.fill" : "text.bubble")
                    }
                    .tag(2)

                ServicesListView()
                    .tabItem {
                        Label("服务", systemImage: selectedTab == 3 ? "heart.circle.fill" : "heart.circle")
                    }
                    .tag(3)

                ProfileView()
                    .tabItem {
                        Label("我的", systemImage: selectedTab == 4 ? "person.fill" : "person")
                    }
                    .tag(4)
            }
            .accentColor(Theme.primaryColor)
        } else {
            LoginView()
        }
    }
}

// MARK: - Profile View
struct ProfileView: View {
    @StateObject private var petViewModel = PetViewModel()
    @StateObject private var serviceViewModel = ServiceViewModel()
    @State private var showingSettings = false

    var body: some View {
        NavigationView {
            List {
                // Profile Header
                Section {
                    HStack(spacing: 16) {
                        Circle()
                            .fill(Theme.primaryColor.opacity(0.2))
                            .frame(width: 70, height: 70)
                            .overlay(
                                Image(systemName: "person.circle.fill")
                                    .font(.system(size: 50))
                                    .foregroundColor(Theme.primaryColor)
                            )

                        VStack(alignment: .leading, spacing: 4) {
                            Text("萌宠达人")
                                .font(.title2)
                                .fontWeight(.bold)
                                .foregroundColor(Theme.textPrimary)

                            Text("ID: 100")
                                .font(.caption)
                                .foregroundColor(Theme.textSecondary)
                        }

                        Spacer()

                        Button {
                            showingSettings = true
                        } label: {
                            Image(systemName: "gearshape.fill")
                                .font(.title2)
                                .foregroundColor(Theme.textSecondary)
                        }
                    }
                    .padding(.vertical, 8)
                }

                // My Pets Section
                Section("我的宠物") {
                    if petViewModel.myPets.isEmpty {
                        Text("还没有添加宠物")
                            .foregroundColor(Theme.textSecondary)
                    } else {
                        ForEach(petViewModel.myPets) { pet in
                            HStack(spacing: 12) {
                                if let avatar = pet.avatar, let url = URL(string: avatar) {
                                    AsyncImage(url: url) { image in
                                        image.resizable()
                                            .aspectRatio(contentMode: .fill)
                                    } placeholder: {
                                        Circle()
                                            .fill(Theme.secondaryColor.opacity(0.3))
                                    }
                                    .frame(width: 50, height: 50)
                                    .clipShape(Circle())
                                } else {
                                    Circle()
                                        .fill(Theme.secondaryColor.opacity(0.3))
                                        .frame(width: 50, height: 50)
                                        .overlay(
                                            Image(systemName: "pawprint.fill")
                                                .foregroundColor(Theme.secondaryColor)
                                        )
                                }

                                VStack(alignment: .leading, spacing: 4) {
                                    Text(pet.petName)
                                        .font(.headline)
                                        .foregroundColor(Theme.textPrimary)

                                    Text(pet.petType)
                                        .font(.subheadline)
                                        .foregroundColor(Theme.textSecondary)
                                }

                                Spacer()

                                Image(systemName: "chevron.right")
                                    .foregroundColor(Theme.textSecondary)
                            }
                        }
                    }

                    Button {
                        // Add pet
                    } label: {
                        HStack {
                            Image(systemName: "plus.circle.fill")
                                .foregroundColor(Theme.primaryColor)
                            Text("添加宠物")
                                .foregroundColor(Theme.primaryColor)
                        }
                    }
                }

                // My Orders Section
                Section("我的订单") {
                    if serviceViewModel.myOrders.isEmpty {
                        Text("还没有订单")
                            .foregroundColor(Theme.textSecondary)
                    } else {
                        ForEach(serviceViewModel.myOrders) { order in
                            HStack(spacing: 12) {
                                Image(systemName: "receipt")
                                    .font(.title2)
                                    .foregroundColor(Theme.primaryColor)
                                    .frame(width: 40)

                                VStack(alignment: .leading, spacing: 4) {
                                    Text("订单 #\(order.id ?? 0)")
                                        .font(.headline)
                                        .foregroundColor(Theme.textPrimary)

                                    if let status = order.status {
                                        Text(statusText(status))
                                            .font(.subheadline)
                                            .foregroundColor(statusColor(status))
                                    }
                                }

                                Spacer()

                                if let price = order.price {
                                    Text("¥\(Int(price))")
                                        .font(.headline)
                                        .foregroundColor(Theme.accentColor)
                                }
                            }
                        }
                    }
                }

                // Menu Items
                Section {
                    MenuItem(icon: "message.circle.fill", title: "我的消息", color: .blue)
                    MenuItem(icon: "heart.fill", title: "我的收藏", color: .red)
                    MenuItem(icon: "bookmark.fill", title: "浏览历史", color: .orange)
                    MenuItem(icon: "questionmark.circle.fill", title: "帮助与反馈", color: .green)
                }

                Section {
                    Button {
                        // Handle logout
                    } label: {
                        HStack {
                            Image(systemName: "arrow.right.square.fill")
                                .foregroundColor(Theme.textSecondary)
                            Text("退出登录")
                                .foregroundColor(Theme.textSecondary)
                        }
                    }
                }
            }
            .navigationTitle("我的")
            .listStyle(.insetGrouped)
            .sheet(isPresented: $showingSettings) {
                SettingsView()
            }
            .onAppear {
                petViewModel.loadMockData()
                serviceViewModel.loadMockData()
            }
        }
    }

    private func statusText(_ status: String) -> String {
        switch status {
        case "pending": return "待确认"
        case "confirmed": return "已确认"
        case "completed": return "已完成"
        case "cancelled": return "已取消"
        default: return status
        }
    }

    private func statusColor(_ status: String) -> Color {
        switch status {
        case "pending": return .orange
        case "confirmed": return .blue
        case "completed": return .green
        case "cancelled": return .red
        default: return .gray
        }
    }
}

// MARK: - Menu Item
struct MenuItem: View {
    let icon: String
    let title: String
    let color: Color

    var body: some View {
        HStack(spacing: 16) {
            Image(systemName: icon)
                .font(.title2)
                .foregroundColor(color)
                .frame(width: 30)

            Text(title)
                .foregroundColor(Theme.textPrimary)

            Spacer()

            Image(systemName: "chevron.right")
                .foregroundColor(Theme.textSecondary)
        }
    }
}

// MARK: - Settings View
struct SettingsView: View {
    @Environment(\.dismiss) var dismiss

    var body: some View {
        NavigationView {
            List {
                Section("账号设置") {
                    HStack {
                        Text("手机号")
                        Spacer()
                        Text("138****8888")
                            .foregroundColor(Theme.textSecondary)
                    }
                    HStack {
                        Text("邮箱")
                        Spacer()
                        Text("pet@example.com")
                            .foregroundColor(Theme.textSecondary)
                    }
                }

                Section("通知设置") {
                    Toggle("接收新消息通知", isOn: .constant(true))
                    Toggle("接收点赞通知", isOn: .constant(true))
                    Toggle("接收评论通知", isOn: .constant(true))
                }

                Section("隐私设置") {
                    Toggle("显示我的位置", isOn: .constant(true))
                    Toggle("允许陌生人查看我的宠物", isOn: .constant(false))
                }

                Section {
                    HStack {
                        Text("版本")
                        Spacer()
                        Text("1.0.0")
                            .foregroundColor(Theme.textSecondary)
                    }
                }
            }
            .navigationTitle("设置")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("完成") {
                        dismiss()
                    }
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
