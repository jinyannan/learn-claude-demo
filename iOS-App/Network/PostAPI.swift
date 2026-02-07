import Foundation

// MARK: - Post API
class PostAPI {
    private let client = APIClient.shared

    func getAllPosts(page: Int = 0, size: Int = 20) async throws -> [CommunityPost] {
        return try await client.requestArray(endpoint: "/posts?page=\(page)&size=\(size)", responseType: [CommunityPost].self)
    }

    func getPost(id: Int64) async throws -> CommunityPost {
        return try await client.request(endpoint: "/posts/\(id)", responseType: CommunityPost.self)
    }

    func getPostsByUser(userId: Int64) async throws -> [CommunityPost] {
        return try await client.requestArray(endpoint: "/posts/user/\(userId)", responseType: [CommunityPost].self)
    }

    func getPostsByType(postType: String) async throws -> [CommunityPost] {
        return try await client.requestArray(endpoint: "/posts/type/\(postType)", responseType: [CommunityPost].self)
    }

    func createPost(_ request: CreatePostRequest) async throws -> CommunityPost {
        return try await client.request(endpoint: "/posts", method: .POST, body: request, responseType: CommunityPost.self)
    }

    func updatePost(id: Int64, _ request: CreatePostRequest) async throws -> CommunityPost {
        return try await client.request(endpoint: "/posts/\(id)", method: .PUT, body: request, responseType: CommunityPost.self)
    }

    func deletePost(id: Int64) async throws {
        try await client.requestVoid(endpoint: "/posts/\(id)", method: .DELETE)
    }

    func likePost(postId: Int64, userId: Int64) async throws {
        try await client.requestVoid(endpoint: "/posts/\(postId)/like?userId=\(userId)", method: .POST)
    }

    func getComments(postId: Int64) async throws -> [PostComment] {
        return try await client.requestArray(endpoint: "/posts/\(postId)/comments", responseType: [PostComment].self)
    }

    func addComment(postId: Int64, _ request: CreateCommentRequest) async throws -> PostComment {
        return try await client.request(endpoint: "/posts/\(postId)/comments", method: .POST, body: request, responseType: PostComment.self)
    }

    func deleteComment(commentId: Int64) async throws {
        try await client.requestVoid(endpoint: "/posts/comments/\(commentId)", method: .DELETE)
    }
}

// MARK: - Create Post Request
struct CreatePostRequest: Codable {
    let userId: Int64
    var petId: Int64?
    let title: String
    let content: String
    var images: String?
    var postType: String?
    var tags: String?
    var location: String?

    enum CodingKeys: String, CodingKey {
        case userId = "user_id"
        case petId = "pet_id"
        case title
        case content
        case images
        case postType = "post_type"
        case tags
        case location
    }
}

// MARK: - Create Comment Request
struct CreateCommentRequest: Codable {
    let postId: Int64
    let userId: Int64
    let content: String
    var parentId: Int64?
    var replyToUserId: Int64?

    enum CodingKeys: String, CodingKey {
        case postId = "post_id"
        case userId = "user_id"
        case content
        case parentId = "parent_id"
        case replyToUserId = "reply_to_user_id"
    }
}
