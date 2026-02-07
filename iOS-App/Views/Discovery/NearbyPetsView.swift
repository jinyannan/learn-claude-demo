import SwiftUI

struct NearbyPetsView: View {
    @StateObject private var viewModel = LocationViewModel()
    @State private var selectedPetType: String? = nil
    @State private var showingFilterSheet = false

    private let petTypes = ["全部", "狗", "猫", "鸟", "兔子", "仓鼠", "其他"]

    var body: some View {
        NavigationView {
            VStack(spacing: 0) {
                // Filter Bar
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 12) {
                        ForEach(petTypes, id: \.self) { type in
                            FilterChip(
                                title: type,
                                isSelected: selectedPetType == nil && type == "全部" || selectedPetType == type
                            ) {
                                withAnimation {
                                    selectedPetType = type == "全部" ? nil : type
                                    viewModel.searchNearbyPets(petType: selectedPetType)
                                }
                            }
                        }
                    }
                    .padding(.horizontal)
                    .padding(.vertical, 12)
                }
                .background(Color.white)

                Divider()

                // Pet List or Map
                Group {
                    if viewModel.nearbyPets.isEmpty {
                        emptyView
                    } else {
                        petList
                    }
                }

                if viewModel.isLoading {
                    ProgressView("搜索中...")
                        .padding()
                }
            }
            .navigationTitle("附近宠物")
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        // Toggle map view
                    } label: {
                        Image(systemName: "map.fill")
                    }
                }
            }
            .refreshable {
                viewModel.searchNearbyPets()
            }
            .onAppear {
                viewModel.loadMockData()
            }
        }
    }

    private var emptyView: some View {
        VStack(spacing: 16) {
            Image(systemName: "location.slash")
                .font(.system(size: 60))
                .foregroundColor(Theme.textSecondary)

            Text("附近没有找到宠物")
                .font(.headline)
                .foregroundColor(Theme.textPrimary)

            Text("试试扩大搜索范围或稍后再试")
                .font(.subheadline)
                .foregroundColor(Theme.textSecondary)

            Button("刷新") {
                viewModel.searchNearbyPets()
            }
            .buttonStyle(.bordered)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }

    private var petList: some View {
        ScrollView {
            LazyVStack(spacing: 16) {
                ForEach(viewModel.nearbyPets) { pet in
                    PetNearbyCard(pet: pet, viewModel: viewModel)
                }
            }
            .padding()
        }
    }
}

// MARK: - Filter Chip
struct FilterChip: View {
    let title: String
    let isSelected: Bool
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.subheadline)
                .fontWeight(isSelected ? .semibold : .regular)
                .foregroundColor(isSelected ? .white : Theme.textPrimary)
                .padding(.horizontal, 16)
                .padding(.vertical, 8)
                .background(isSelected ? Theme.primaryColor : Color.gray.opacity(0.2))
                .cornerRadius(20)
        }
    }
}

// MARK: - Pet Nearby Card
struct PetNearbyCard: View {
    let pet: Pet
    let viewModel: LocationViewModel

    var body: some View {
        HStack(spacing: 12) {
            // Avatar
            if let avatar = pet.avatar, let url = URL(string: avatar) {
                AsyncImage(url: url) { image in
                    image.resizable()
                        .aspectRatio(contentMode: .fill)
                } placeholder: {
                    Circle()
                        .fill(Theme.secondaryColor.opacity(0.3))
                        .overlay(
                            Image(systemName: "pawprint.fill")
                                .foregroundColor(Theme.secondaryColor)
                        )
                }
                .frame(width: 70, height: 70)
                .clipShape(Circle())
            } else {
                Circle()
                    .fill(Theme.secondaryColor.opacity(0.3))
                    .frame(width: 70, height: 70)
                    .overlay(
                        Image(systemName: "pawprint.fill")
                            .font(.title2)
                            .foregroundColor(Theme.secondaryColor)
                    )
            }

            // Info
            VStack(alignment: .leading, spacing: 4) {
                HStack {
                    Text(pet.petName)
                        .font(.headline)
                        .foregroundColor(Theme.textPrimary)

                    Text(pet.petType)
                        .font(.caption)
                        .foregroundColor(Theme.textSecondary)
                        .padding(.horizontal, 8)
                        .padding(.vertical, 2)
                        .background(Theme.secondaryColor.opacity(0.2))
                        .cornerRadius(8)
                }

                if let breed = pet.breed {
                    Text(breed)
                        .font(.subheadline)
                        .foregroundColor(Theme.textSecondary)
                }

                if let personality = pet.personality {
                    Text(personality)
                        .font(.caption)
                        .foregroundColor(Theme.textSecondary)
                        .lineLimit(1)
                }
            }

            Spacer()

            // Distance & Action
            VStack(spacing: 8) {
                if let distance = calculateDistance() {
                    Text("\(String(format: "%.1f", distance))km")
                        .font(.caption)
                        .foregroundColor(Theme.textSecondary)
                }

                Button {
                    // View profile
                } label: {
                    Image(systemName: "chevron.right")
                        .foregroundColor(Theme.textSecondary)
                }
            }
        }
        .padding()
        .background(Color.white)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.05), radius: 3, x: 0, y: 1)
    }

    private func calculateDistance() -> Double? {
        // For demo, return a random distance
        return Double.random(in: 0.5...10.0)
    }
}

#Preview {
    NearbyPetsView()
}
