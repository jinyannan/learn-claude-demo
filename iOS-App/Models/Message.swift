import Foundation

// MARK: - Message Model
struct Message: Codable, Identifiable {
    let id: Int64?
    let fromUserId: Int64
    let toUserId: Int64
    var content: String
    var msgType: String?
    var isRead: Int?
    var readTime: String?
    var status: Int?
    let createTime: String?

    enum CodingKeys: String, CodingKey {
        case id
        case fromUserId = "from_user_id"
        case toUserId = "to_user_id"
        case content
        case msgType = "msg_type"
        case isRead = "is_read"
        case readTime = "read_time"
        case status
        case createTime = "create_time"
    }
}

// MARK: - User Model (Simplified)
struct User: Codable, Identifiable {
    let id: Int64?
    var username: String?
    var nickname: String?
    var avatar: String?
    var email: String?
    var phone: String?
    var status: Int?
    let createTime: String?
    let updateTime: String?

    enum CodingKeys: String, CodingKey {
        case id
        case username
        case nickname
        case avatar
        case email
        case phone
        case status
        case createTime = "create_time"
        case updateTime = "update_time"
    }
}

// MARK: - Mock Data
extension Message {
    static let mockData: [Message] = [
        Message(
            id: 1,
            fromUserId: 100,
            toUserId: 101,
            content: "你好，你的宠物好可爱！",
            msgType: "text",
            isRead: 1,
            readTime: nil,
            status: 0,
            createTime: "2024-01-20T14:30:00"
        ),
        Message(
            id: 2,
            fromUserId: 101,
            toUserId: 100,
            content: "谢谢！你的也很可爱！",
            msgType: "text",
            isRead: 0,
            readTime: nil,
            status: 0,
            createTime: "2024-01-20T14:35:00"
        )
    ]
}

extension User {
    static let mockCurrentUser = User(
        id: 100,
        username: "petlover",
        nickname: "萌宠达人",
        avatar: nil,
        email: "pet@example.com",
        phone: "13800138000",
        status: 0,
        createTime: "2024-01-01T00:00:00",
        updateTime: "2024-01-01T00:00:00"
    )
}
