import Foundation

// MARK: - Pet Model
struct Pet: Codable, Identifiable {
    let id: Int64?
    let userId: Int64
    var petName: String
    var petType: String
    var breed: String?
    var gender: Int?
    var birthDate: String?
    var weight: Double?
    var avatar: String?
    var personality: String?
    var healthStatus: String?
    var sterilized: Int?
    var vaccinated: Int?
    var description: String?
    var status: Int?
    let createTime: String?
    let updateTime: String?

    enum CodingKeys: String, CodingKey {
        case id
        case userId = "user_id"
        case petName = "pet_name"
        case petType = "pet_type"
        case breed
        case gender
        case birthDate = "birth_date"
        case weight
        case avatar
        case personality
        case healthStatus = "health_status"
        case sterilized
        case vaccinated
        case description
        case status
        case createTime = "create_time"
        case updateTime = "update_time"
    }
}

// MARK: - Mock Data
extension Pet {
    static let mockData: [Pet] = [
        Pet(
            id: 1,
            userId: 100,
            petName: "Buddy",
            petType: "狗",
            breed: "金毛",
            gender: 1,
            birthDate: "2020-05-15",
            weight: 30.5,
            avatar: nil,
            personality: "活泼友好",
            healthStatus: "健康",
            sterilized: 1,
            vaccinated: 1,
            description: "非常喜欢玩球",
            status: 0,
            createTime: "2024-01-01T10:00:00",
            updateTime: "2024-01-01T10:00:00"
        ),
        Pet(
            id: 2,
            userId: 101,
            petName: "Luna",
            petType: "猫",
            breed: "布偶",
            gender: 2,
            birthDate: "2021-03-20",
            weight: 4.2,
            avatar: nil,
            personality: "安静温柔",
            healthStatus: "健康",
            sterilized: 1,
            vaccinated: 1,
            description: "喜欢晒太阳",
            status: 0,
            createTime: "2024-01-02T10:00:00",
            updateTime: "2024-01-02T10:00:00"
        )
    ]
}
