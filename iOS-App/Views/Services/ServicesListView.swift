import SwiftUI

struct ServicesListView: View {
    @StateObject private var viewModel = ServiceViewModel()
    @State private var selectedServiceType: ServiceType?
    @State private var searchText = ""

    var body: some View {
        NavigationView {
            VStack(spacing: 0) {
                // Search Bar
                SearchBar(text: $searchText)
                    .padding()

                // Service Types
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 12) {
                        ForEach(ServiceType.allCases, id: \.rawValue) { type in
                            ServiceTypeChip(
                                type: type,
                                isSelected: selectedServiceType == type
                            ) {
                                withAnimation {
                                    selectedServiceType = type
                                    viewModel.loadServicesByType(typeId: type.id)
                                }
                            }
                        }
                    }
                    .padding(.horizontal)
                    .padding(.vertical, 12)
                }
                .background(Color.white)

                Divider()

                // Services List
                Group {
                    if filteredServices.isEmpty {
                        emptyView
                    } else {
                        servicesList
                    }
                }

                if viewModel.isLoading {
                    ProgressView("加载中...")
                        .padding()
                }
            }
            .navigationTitle("宠物服务")
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        // Add service
                    } label: {
                        Image(systemName: "plus.circle.fill")
                    }
                }
            }
            .refreshable {
                viewModel.loadAllServices()
            }
            .onAppear {
                viewModel.loadMockData()
            }
        }
    }

    private var filteredServices: [ServicePublish] {
        if searchText.isEmpty {
            return viewModel.services
        }
        return viewModel.services.filter { service in
            service.title.localizedCaseInsensitiveContains(searchText) ||
            (service.description?.localizedCaseInsensitiveContains(searchText) ?? false)
        }
    }

    private var emptyView: some View {
        VStack(spacing: 16) {
            Image(systemName: "pawprint.circle")
                .font(.system(size: 60))
                .foregroundColor(Theme.textSecondary)

            Text("没有找到服务")
                .font(.headline)
                .foregroundColor(Theme.textPrimary)

            Text("试试其他搜索条件")
                .font(.subheadline)
                .foregroundColor(Theme.textSecondary)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }

    private var servicesList: some View {
        ScrollView {
            LazyVStack(spacing: 16) {
                ForEach(filteredServices) { service in
                    ServiceCard(service: service)
                }
            }
            .padding()
        }
    }
}

// MARK: - Search Bar
struct SearchBar: View {
    @Binding var text: String

    var body: some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .foregroundColor(Theme.textSecondary)

            TextField("搜索服务", text: $text)
                .textFieldStyle(.plain)

            if !text.isEmpty {
                Button {
                    text = ""
                } label: {
                    Image(systemName: "xmark.circle.fill")
                        .foregroundColor(Theme.textSecondary)
                }
            }
        }
        .padding(.horizontal, 12)
        .padding(.vertical, 8)
        .background(Theme.backgroundColor)
        .cornerRadius(10)
    }
}

// MARK: - Service Type Chip
struct ServiceTypeChip: View {
    let type: ServiceType
    let isSelected: Bool
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            HStack(spacing: 6) {
                Image(systemName: iconName)
                Text(type.rawValue)
            }
            .font(.subheadline)
            .fontWeight(isSelected ? .semibold : .regular)
            .foregroundColor(isSelected ? .white : Theme.textPrimary)
            .padding(.horizontal, 16)
            .padding(.vertical, 10)
            .background(isSelected ? Theme.primaryColor : Color.gray.opacity(0.2))
            .cornerRadius(20)
        }
    }

    private var iconName: String {
        switch type {
        case .boarding: return "house.circle.fill"
        case .grooming: return "scissors"
        case .walking: return "figure.walk"
        case .training: return "graduationcap"
        case .sitting: return "heart.circle.fill"
        }
    }
}

// MARK: - Service Card
struct ServiceCard: View {
    let service: ServicePublish

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            // Header
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text(service.title)
                        .font(.headline)
                        .foregroundColor(Theme.textPrimary)

                    if let description = service.description {
                        Text(description)
                            .font(.subheadline)
                            .foregroundColor(Theme.textSecondary)
                            .lineLimit(2)
                    }
                }

                Spacer()

                VStack(alignment: .trailing, spacing: 4) {
                    if let price = service.price, let priceUnit = service.priceUnit {
                        Text("¥\(Int(price))")
                            .font(.title3)
                            .fontWeight(.bold)
                            .foregroundColor(Theme.accentColor)

                        Text(priceUnit)
                            .font(.caption)
                            .foregroundColor(Theme.textSecondary)
                    }
                }
            }

            Divider()

            // Details
            HStack(spacing: 20) {
                Label {
                    Text(serviceTypeTitle)
                        .font(.caption)
                        .foregroundColor(Theme.textSecondary)
                } icon: {
                    Image(systemName: "tag.fill")
                        .foregroundColor(Theme.primaryColor)
                        .font(.caption)
                }

                if let radius = service.serviceRadius {
                    Label {
                        Text("\(radius)km")
                            .font(.caption)
                            .foregroundColor(Theme.textSecondary)
                    } icon: {
                        Image(systemName: "location.fill")
                            .foregroundColor(Theme.primaryColor)
                            .font(.caption)
                    }
                }

                Spacer()

                if let availableTime = service.availableTime {
                    Label {
                        Text(availableTime)
                            .font(.caption)
                            .foregroundColor(Theme.textSecondary)
                    } icon: {
                        Image(systemName: "clock.fill")
                            .foregroundColor(Theme.primaryColor)
                            .font(.caption)
                    }
                }
            }

            // Action Button
            Button {
                // Book service
            } label: {
                Text("预约服务")
                    .font(.subheadline)
                    .fontWeight(.semibold)
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 12)
                    .background(Theme.primaryColor)
                    .cornerRadius(10)
            }
        }
        .padding()
        .background(Color.white)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.05), radius: 3, x: 0, y: 1)
    }

    private var serviceTypeTitle: String {
        ServiceType.allCases.first { $0.id == service.serviceTypeId }?.rawValue ?? "服务"
    }
}

#Preview {
    ServicesListView()
}
