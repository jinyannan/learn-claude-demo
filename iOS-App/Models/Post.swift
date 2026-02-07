import Foundation

// MARK: - Community Post Model
struct CommunityPost: Codable, Identifiable {
    let id: Int64?
    let userId: Int64
    let petId: Int64?
    var title: String
    var content: String
    var images: String?
    var postType: String?
    var tags: String?
    var location: String?
    var likeCount: Int?
    var commentCount: Int?
    var viewCount: Int?
    var isTop: Int?
    var status: Int?
    let createTime: String?
    let updateTime: String?

    enum CodingKeys: String, CodingKey {
        case id
        case userId = "user_id"
        case petId = "pet_id"
        case title
        case content
        case images
        case postType = "post_type"
        case tags
        case location
        case likeCount = "like_count"
        case commentCount = "comment_count"
        case viewCount = "view_count"
        case isTop = "is_top"
        case status
        case createTime = "create_time"
        case updateTime = "update_time"
    }
}

// MARK: - Post Comment Model
struct PostComment: Codable, Identifiable {
    let id: Int64?
    let postId: Int64
    let userId: Int64
    var content: String
    var parentId: Int64?
    var replyToUserId: Int64?
    var likeCount: Int?
    var status: Int?
    let createTime: String?

    enum CodingKeys: String, CodingKey {
        case id
        case postId = "post_id"
        case userId = "user_id"
        case content
        case parentId = "parent_id"
        case replyToUserId = "reply_to_user_id"
        case likeCount = "like_count"
        case status
        case createTime = "create_time"
    }
}

// MARK: - Mock Data
extension CommunityPost {
    static let mockData: [CommunityPost] = [
        CommunityPost(
            id: 1,
            userId: 100,
            petId: 1,
            title: "Buddy的公园日记",
            content: "今天带Buddy去公园玩，它开心得不得了！看到了很多其他狗狗，交到了新朋友。",
            images: nil,
            postType: "daily",
            tags: "金毛,公园",
            location: nil,
            likeCount: 42,
            commentCount: 8,
            viewCount: 256,
            isTop: 0,
            status: 0,
            createTime: "2024-01-15T10:30:00",
            updateTime: "2024-01-15T10:30:00"
        ),
        CommunityPost(
            id: 2,
            userId: 101,
            petId: 2,
            title: "Luna的日常",
            content: "Luna今天特别粘人，一直在我腿边蹭来蹭去。🐱",
            images: nil,
            postType: "daily",
            tags: "布偶猫",
            location: nil,
            likeCount: 89,
            commentCount: 15,
            viewCount: 432,
            isTop: 0,
            status: 0,
            createTime: "2024-01-16T14:20:00",
            updateTime: "2024-01-16T14:20:00"
        )
    ]
}
