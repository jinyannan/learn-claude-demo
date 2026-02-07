import Foundation

// MARK: - Message API
class MessageAPI {
    private let client = APIClient.shared

    func getChatList(userId: Int64) async throws -> [Message] {
        return try await client.requestArray(endpoint: "/messages/chats?userId=\(userId)", responseType: [Message].self)
    }

    func getChatHistory(currentUserId: Int64, otherUserId: Int64) async throws -> [Message] {
        return try await client.requestArray(endpoint: "/messages/\(otherUserId)?currentUserId=\(currentUserId)", responseType: [Message].self)
    }

    func sendMessage(fromUserId: Int64, toUserId: Int64, content: String, msgType: String = "text") async throws -> Message {
        return try await client.request(
            endpoint: "/messages?fromUserId=\(fromUserId)&toUserId=\(toUserId)&content=\(content.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? content)&msgType=\(msgType)",
            method: .POST,
            responseType: Message.self
        )
    }

    func markAsRead(messageId: Int64) async throws {
        try await client.requestVoid(endpoint: "/messages/\(messageId)/read", method: .PUT)
    }

    func markChatAsRead(fromUserId: Int64, toUserId: Int64) async throws {
        try await client.requestVoid(endpoint: "/messages/chat/read?fromUserId=\(fromUserId)&toUserId=\(toUserId)", method: .PUT)
    }

    func getUnreadCount(userId: Int64) async throws -> Int64 {
        let result: String = try await client.request(
            endpoint: "/messages/unread?userId=\(userId)",
            responseType: String.self
        )
        return Int64(result) ?? 0
    }

    func deleteMessage(messageId: Int64) async throws {
        try await client.requestVoid(endpoint: "/messages/\(messageId)", method: .DELETE)
    }
}

// MARK: - Friend API
class FriendAPI {
    private let client = APIClient.shared

    func getFriendList(userId: Int64) async throws -> [FriendRelation] {
        return try await client.requestArray(endpoint: "/friends?userId=\(userId)", responseType: [FriendRelation].self)
    }

    func getRecommendFriends(userId: Int64) async throws -> [User] {
        return try await client.requestArray(endpoint: "/friends/recommend?userId=\(userId)", responseType: [User].self)
    }

    func sendFriendRequest(userId: Int64, friendId: Int64, remark: String? = nil) async throws -> FriendRelation {
        var endpoint = "/friends/request?userId=\(userId)&friendId=\(friendId)"
        if let remark = remark {
            endpoint += "&remark=\(remark.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? remark)"
        }
        return try await client.request(endpoint: endpoint, method: .POST, responseType: FriendRelation.self)
    }

    func acceptFriendRequest(userId: Int64, friendId: Int64) async throws -> FriendRelation {
        return try await client.request(
            endpoint: "/friends/\(friendId)/accept?userId=\(userId)",
            method: .PUT,
            responseType: FriendRelation.self
        )
    }

    func deleteFriend(userId: Int64, friendId: Int64) async throws {
        try await client.requestVoid(endpoint: "/friends/\(friendId)?userId=\(userId)", method: .DELETE)
    }

    func checkFriendship(userId: Int64, friendId: Int64) async throws -> Bool {
        let result: String = try await client.request(
            endpoint: "/friends/check?userId=\(userId)&friendId=\(friendId)",
            responseType: String.self
        )
        return result == "true"
    }

    func getNearbyUsers(userId: Int64, latitude: Double, longitude: Double, radius: Int = 10) async throws -> [User] {
        return try await client.requestArray(
            endpoint: "/friends/nearby?userId=\(userId)&latitude=\(latitude)&longitude=\(longitude)&radius=\(radius)",
            responseType: [User].self
        )
    }
}

// MARK: - Friend Relation Model
struct FriendRelation: Codable, Identifiable {
    let id: Int64?
    let userId: Int64
    let friendId: Int64
    var remark: String?
    var isBlacklist: Int?
    let createTime: String?

    enum CodingKeys: String, CodingKey {
        case id
        case userId = "user_id"
        case friendId = "friend_id"
        case remark
        case isBlacklist = "is_blacklist"
        case createTime = "create_time"
    }
}
