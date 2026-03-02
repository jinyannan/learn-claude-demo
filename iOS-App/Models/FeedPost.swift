import Foundation

struct FeedPost: Codable, Identifiable {
    let id: Int
    
    // User info
    let userId: Int
    let userName: String
    let userAvatar: String
    
    // Pet info
    let petId: Int?
    let petName: String?
    let petAvatar: String?
    
    // Post content
    let title: String
    let content: String
    let images: [String]
    let tags: String?
    
    // Engagement stats
    var likeCount: Int
    var commentCount: Int
    var viewCount: Int
    
    // Current user interaction flags
    var isLiked: Bool
    var isFollowed: Bool
    
    // Timestamp
    let createTime: String?
}
