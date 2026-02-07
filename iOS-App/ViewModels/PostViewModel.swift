import Foundation

@MainActor
class PostViewModel: ObservableObject {
    private let api = PostAPI()

    @Published var posts: [CommunityPost] = []
    @Published var currentPost: CommunityPost?
    @Published var comments: [PostComment] = []
    @Published var isLoading = false
    @Published var errorMessage: String?

    func loadAllPosts(page: Int = 0, size: Int = 20) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.posts = try await api.getAllPosts(page: page, size: size)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
                // Fallback to mock data
                self.posts = CommunityPost.mockData
            }
        }
    }

    func loadPost(id: Int64) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.currentPost = try await api.getPost(id: id)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func loadPostsByUser(userId: Int64) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.posts = try await api.getPostsByUser(userId: userId)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func loadPostsByType(postType: String) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.posts = try await api.getPostsByType(postType: postType)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func createPost(_ request: CreatePostRequest) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                let newPost = try await api.createPost(request)
                self.posts.insert(newPost, at: 0)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func likePost(postId: Int64, userId: Int64) {
        Task {
            do {
                try await api.likePost(postId: postId, userId: userId)
                if let index = posts.firstIndex(where: { $0.id == postId }) {
                    var post = posts[index]
                    post.likeCount = (post.likeCount ?? 0) + 1
                    posts[index] = post
                }
            } catch {
                self.errorMessage = error.localizedDescription
            }
        }
    }

    func loadComments(postId: Int64) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.comments = try await api.getComments(postId: postId)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func addComment(postId: Int64, _ request: CreateCommentRequest) {
        Task {
            do {
                let newComment = try await api.addComment(postId: postId, request)
                self.comments.append(newComment)
            } catch {
                self.errorMessage = error.localizedDescription
            }
        }
    }

    func deletePost(id: Int64) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                try await api.deletePost(id: id)
                self.posts.removeAll { $0.id == id }
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    // Load mock data for testing
    func loadMockData() {
        self.posts = CommunityPost.mockData
    }
}
