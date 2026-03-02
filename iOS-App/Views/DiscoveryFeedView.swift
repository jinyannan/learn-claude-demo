import SwiftUI

struct DiscoveryFeedView: View {
    @StateObject private var viewModel = FeedViewModel()
    
    // Aesthetic Tokens
    private let primaryBrandColor = Color(red: 1.0, green: 0.45, blue: 0.25)
    private let backgroundColor = Color(red: 0.98, green: 0.98, blue: 0.98) // Off-white
    
    var body: some View {
        ZStack(alignment: .bottomTrailing) {
            
            // Base layer: Background Color
            backgroundColor
                .ignoresSafeArea()
            
            ScrollView(.vertical, showsIndicators: false) {
                // Top Padding to clear navigation area
                Spacer().frame(height: 120)
                
                LazyVStack(spacing: 8) {
                    ForEach(viewModel.posts) { post in
                        PostCardView(post: post) {
                            Task {
                                await viewModel.toggleLike(for: post)
                            }
                        }
                        .onAppear {
                            // Infinite Scroll trigger point (2 items before end)
                            if post.id == viewModel.posts.last?.id {
                                Task {
                                    await viewModel.loadNextPage()
                                }
                            }
                        }
                    }
                    
                    // Loading indicator for pagination
                    if viewModel.isLoading {
                        ProgressView("Fetching more pets...")
                            .padding(.vertical, 32)
                    }
                }
                // Bottom padding for clear FAB
                Spacer().frame(height: 120)
            }
            .refreshable {
                await viewModel.fetchInitialFeed()
            }
            
            // Layer 2: Glassmorphism Nav Bar
            VStack {
                HStack(alignment: .center) {
                    Text("Paws & Pals")
                        .font(.system(size: 28, weight: .heavy, design: .rounded))
                        .foregroundColor(.primary)
                    
                    Spacer()
                    
                    Button(action: {}) {
                        Image(systemName: "magnifyingglass")
                            .font(.system(size: 20, weight: .bold))
                            .foregroundColor(.primary)
                    }
                    .padding(.trailing, 16)
                    
                    AsyncImage(url: URL(string: "https://picsum.photos/100/100?random=self")) { image in
                        image.resizable().scaledToFill()
                    } placeholder: {
                        Circle().fill(Color.gray.opacity(0.3))
                    }
                    .frame(width: 40, height: 40)
                    .clipShape(Circle())
                    .overlay(Circle().stroke(Color.white, lineWidth: 2))
                    .shadow(color: .black.opacity(0.1), radius: 4, x: 0, y: 2)
                }
                .padding(.horizontal, 24)
                .padding(.vertical, 16)
                .background(.ultraThinMaterial)
                
                Spacer()
            }
            .ignoresSafeArea(edges: .top)
            
            // Layer 3: Floating Action Button (FAB)
            Button(action: {
                // Open Mock Publish Screen
            }) {
                Image(systemName: "pawprint.fill")
                    .font(.system(size: 28))
                    .foregroundColor(.white)
                    .frame(width: 64, height: 64)
                    .background(
                        LinearGradient(gradient: Gradient(colors: [primaryBrandColor, primaryBrandColor.opacity(0.8)]), startPoint: .topLeading, endPoint: .bottomTrailing)
                    )
                    .clipShape(Circle())
                    .shadow(color: primaryBrandColor.opacity(0.4), radius: 12, x: 0, y: 8)
                    .overlay(
                        Circle().stroke(Color.white.opacity(0.3), lineWidth: 1)
                    )
                    .padding(.trailing, 24)
                    .padding(.bottom, 24)
            }
        }
        .task {
            // Initiate fetch on entry
            // In a real app auth determines this trigger, here we assume logged in.
            if viewModel.posts.isEmpty {
                await viewModel.fetchInitialFeed()
            }
        }
    }
}
