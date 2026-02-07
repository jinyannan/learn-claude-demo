import SwiftUI

struct HomeView: View {
    @StateObject private var postViewModel = PostViewModel()
    @StateObject private var petViewModel = PetViewModel()

    var body: some View {
        NavigationView {
            ScrollView {
                LazyVStack(spacing: 0) {
                    // Stories Section
                    StoriesRow(pets: petViewModel.pets)
                        .padding(.vertical, 12)

                    Divider()

                    // Feed Section
                    ForEach(postViewModel.posts) { post in
                        PostCard(post: post)
                            .padding(.vertical, 8)
                    }

                    if postViewModel.isLoading {
                        ProgressView()
                            .padding()
                    }
                }
            }
            .navigationTitle("PetConnect")
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button("登出") {
                        // Handle logout
                    }
                    .font(.subheadline)
                }
                ToolbarItem(placement: .navigationBarTrailing) {
                    HStack {
                        Button {
                            // Handle messages
                        } label: {
                            Image(systemName: "message.circle.fill")
                                .font(.title2)
                        }

                        Button {
                            // Handle notifications
                        } label: {
                            Image(systemName: "bell.circle.fill")
                                .font(.title2)
                        }
                    }
                }
            }
            .refreshable {
                postViewModel.loadAllPosts()
                petViewModel.loadAllPets()
            }
            .onAppear {
                postViewModel.loadMockData()
                petViewModel.loadMockData()
            }
        }
    }
}

// MARK: - Stories Row
struct StoriesRow: View {
    let pets: [Pet]

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 16) {
                ForEach(pets) { pet in
                    StoryCircle(pet: pet)
                }
            }
            .padding(.horizontal)
        }
    }
}

// MARK: - Story Circle
struct StoryCircle: View {
    let pet: Pet

    var body: some View {
        VStack(spacing: 4) {
            ZStack {
                Circle()
                    .fill(LinearGradient(
                        colors: [Theme.primaryColor, Theme.secondaryColor],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    ))
                    .frame(width: 68, height: 68)

                Circle()
                    .fill(Color.white)
                    .frame(width: 64, height: 64)

                if let avatar = pet.avatar, let url = URL(string: avatar) {
                    AsyncImage(url: url) { image in
                        image.resizable()
                            .aspectRatio(contentMode: .fill)
                    } placeholder: {
                        Image(systemName: "pawprint.fill")
                            .font(.title)
                            .foregroundColor(Theme.textSecondary)
                    }
                    .frame(width: 60, height: 60)
                    .clipShape(Circle())
                } else {
                    Image(systemName: "pawprint.fill")
                        .font(.title2)
                        .foregroundColor(Theme.primaryColor)
                }
            }

            Text(pet.petName)
                .font(.caption2)
                .foregroundColor(Theme.textPrimary)
                .lineLimit(1)
                .frame(width: 70)
        }
    }
}

// MARK: - Post Card
struct PostCard: View {
    let post: CommunityPost
    @State private var isLiked = false

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            // Header
            HStack {
                Circle()
                    .fill(Theme.secondaryColor.opacity(0.3))
                    .frame(width: 40, height: 40)
                    .overlay(
                        Image(systemName: "person.circle.fill")
                            .foregroundColor(Theme.secondaryColor)
                    )

                VStack(alignment: .leading, spacing: 2) {
                    Text("用户\(post.userId)")
                        .font(.subheadline)
                        .fontWeight(.semibold)

                    if let petId = post.petId {
                        Text("宠物 \(petId)")
                            .font(.caption)
                            .foregroundColor(Theme.textSecondary)
                    }
                }

                Spacer()

                Button {
                    // Handle more options
                } label: {
                    Image(systemName: "ellipsis")
                        .foregroundColor(Theme.textPrimary)
                }
            }
            .padding(.horizontal)
            .padding(.top, 8)

            // Content
            VStack(alignment: .leading, spacing: 8) {
                Text(post.title)
                    .font(.headline)
                    .foregroundColor(Theme.textPrimary)

                Text(post.content)
                    .font(.body)
                    .foregroundColor(Theme.textPrimary)
                    .lineLimit(3)
            }
            .padding(.horizontal)

            // Images (if any)
            if let images = post.images, !images.isEmpty {
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 8) {
                        ForEach(images.components(separatedBy: ","), id: \.self) { imageURL in
                            if let url = URL(string: imageURL.trimmingCharacters(in: .whitespaces)) {
                                AsyncImage(url: url) { image in
                                    image.resizable()
                                        .aspectRatio(contentMode: .fill)
                                } placeholder: {
                                    Rectangle()
                                        .fill(Theme.backgroundColor)
                                }
                                .frame(width: 200, height: 200)
                                .cornerRadius(12)
                            }
                        }
                    }
                    .padding(.horizontal)
                }
            }

            // Tags (if any)
            if let tags = post.tags, !tags.isEmpty {
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 8) {
                        ForEach(tags.components(separatedBy: ","), id: \.self) { tag in
                            Text("#\(tag.trimmingCharacters(in: .whitespaces))")
                                .font(.caption)
                                .foregroundColor(Theme.primaryColor)
                                .padding(.horizontal, 12)
                                .padding(.vertical, 6)
                                .background(Theme.primaryColor.opacity(0.1))
                                .cornerRadius(12)
                        }
                    }
                    .padding(.horizontal)
                }
            }

            Divider()
                .padding(.horizontal)

            // Actions
            HStack(spacing: 24) {
                Button {
                    isLiked.toggle()
                    // Handle like
                } label: {
                    HStack(spacing: 4) {
                        Image(systemName: isLiked ? "heart.fill" : "heart")
                            .foregroundColor(isLiked ? Theme.accentColor : Theme.textPrimary)
                        Text("\(post.likeCount ?? 0)")
                            .font(.subheadline)
                            .foregroundColor(Theme.textPrimary)
                    }
                }

                Button {
                    // Handle comments
                } label: {
                    HStack(spacing: 4) {
                        Image(systemName: "bubble.right")
                            .foregroundColor(Theme.textPrimary)
                        Text("\(post.commentCount ?? 0)")
                            .font(.subheadline)
                            .foregroundColor(Theme.textPrimary)
                    }
                }

                Button {
                    // Handle share
                } label: {
                    Image(systemName: "arrow.turn.up.right")
                        .foregroundColor(Theme.textPrimary)
                }

                Spacer()
            }
            .padding(.horizontal)
            .padding(.bottom, 8)
        }
        .background(Color.white)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.05), radius: 5, x: 0, y: 2)
        .padding(.horizontal)
    }
}

#Preview {
    HomeView()
}
