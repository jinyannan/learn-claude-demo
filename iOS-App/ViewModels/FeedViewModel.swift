import SwiftUI

@MainActor
class FeedViewModel: ObservableObject {
    @Published var posts: [FeedPost] = []
    @Published var isLoading = false
    @Published var errorMessage: String?
    
    // Pagination
    private var currentPage = 0
    private let pageSize = 10
    var hasMorePages = true
    
    private let service = FeedService.shared
    
    func fetchInitialFeed() async {
        currentPage = 0
        posts.removeAll()
        await loadFeed(isRefresh: true)
    }
    
    func loadNextPage() async {
        guard !isLoading, hasMorePages else { return }
        currentPage += 1
        await loadFeed(isRefresh: false)
    }
    
    private func loadFeed(isRefresh: Bool) async {
        if isRefresh { isLoading = true }
        errorMessage = nil
        
        do {
            let fetchedPosts = try await service.fetchFeed(page: currentPage, size: pageSize)
            
            if isRefresh {
                self.posts = fetchedPosts
            } else {
                self.posts.append(contentsOf: fetchedPosts)
            }
            
            // Assuming empty fetch means we hit the end
            hasMorePages = !fetchedPosts.isEmpty
            
        } catch {
            errorMessage = "Failed to load feed: \(error.localizedDescription)"
        }
        if isRefresh { isLoading = false }
    }
    
    func toggleLike(for post: FeedPost) async {
        guard let index = posts.firstIndex(where: { $0.id == post.id }) else { return }
        
        // Optimistic UI update
        let isLiking = !posts[index].isLiked
        posts[index].isLiked = isLiking
        posts[index].likeCount += isLiking ? 1 : -1
        
        // Perform network action
        do {
            try await service.toggleLike(postId: post.id)
            // It succeeded, we do nothing to the UI since we optimistic-updated
        } catch {
            // Rollback optimistic update
            posts[index].isLiked = !isLiking
            posts[index].likeCount -= isLiking ? 1 : -1
            print("Like toggle failed: \(error)")
        }
    }
}
