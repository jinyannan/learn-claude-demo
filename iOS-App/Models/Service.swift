import Foundation

// MARK: - Service Publish Model
struct ServicePublish: Codable, Identifiable {
    let id: Int64?
    let userId: Int64
    let serviceTypeId: Int64
    var title: String
    var description: String?
    var addressId: Int64?
    var serviceRadius: Int?
    var price: Double?
    var priceUnit: String?
    var availableTime: String?
    var petTypes: String?
    var status: Int?
    var viewCount: Int?
    let createTime: String?
    let updateTime: String?

    enum CodingKeys: String, CodingKey {
        case id
        case userId = "user_id"
        case serviceTypeId = "service_type_id"
        case title
        case description
        case addressId = "address_id"
        case serviceRadius = "service_radius"
        case price
        case priceUnit = "price_unit"
        case availableTime = "available_time"
        case petTypes = "pet_types"
        case status
        case viewCount = "view_count"
        case createTime = "create_time"
        case updateTime = "update_time"
    }
}

// MARK: - Service Order Model
struct ServiceOrder: Codable, Identifiable {
    let id: Int64?
    let orderNo: String?
    let serviceId: Int64
    let providerId: Int64
    let customerId: Int64
    let petId: Int64
    var serviceTime: String?
    var serviceAddress: String?
    var price: Double?
    var status: String?
    var remark: String?
    let createTime: String?
    let updateTime: String?

    enum CodingKeys: String, CodingKey {
        case id
        case orderNo = "order_no"
        case serviceId = "service_id"
        case providerId = "provider_id"
        case customerId = "customer_id"
        case petId = "pet_id"
        case serviceTime = "service_time"
        case serviceAddress = "service_address"
        case price
        case status
        case remark
        case createTime = "create_time"
        case updateTime = "update_time"
    }
}

// MARK: - Service Type Enum
enum ServiceType: String, CaseIterable {
    case boarding = "寄养"
    case grooming = "美容"
    case walking = "遛狗"
    case training = "训练"
    case sitting = "宠物看护"

    var id: Int64 {
        switch self {
        case .boarding: return 1
        case .grooming: return 2
        case .walking: return 3
        case .training: return 4
        case .sitting: return 5
        }
    }
}

// MARK: - Mock Data
extension ServicePublish {
    static let mockData: [ServicePublish] = [
        ServicePublish(
            id: 1,
            userId: 200,
            serviceTypeId: 1,
            title: "专业宠物寄养服务",
            description: "家里有院子，环境宽敞，可以提供专业的宠物寄养服务。",
            addressId: 1,
            serviceRadius: 5,
            price: 80.0,
            priceUnit: "元/天",
            availableTime: "周末全天",
            petTypes: "狗,猫",
            status: 0,
            viewCount: 156,
            createTime: "2024-01-10T09:00:00",
            updateTime: "2024-01-10T09:00:00"
        ),
        ServicePublish(
            id: 2,
            userId: 201,
            serviceTypeId: 2,
            title: "专业宠物美容",
            description: "10年美容经验，擅长各种造型。",
            addressId: 2,
            serviceRadius: 10,
            price: 120.0,
            priceUnit: "元/次",
            availableTime: "周一至周五 9:00-18:00",
            petTypes: "狗",
            status: 0,
            viewCount: 234,
            createTime: "2024-01-12T10:00:00",
            updateTime: "2024-01-12T10:00:00"
        )
    ]
}

extension ServiceOrder {
    static let mockData: [ServiceOrder] = [
        ServiceOrder(
            id: 1,
            orderNo: "ORD1234567890",
            serviceId: 1,
            providerId: 200,
            customerId: 100,
            petId: 1,
            serviceTime: "2024-02-01T10:00:00",
            serviceAddress: "朝阳区xxx",
            price: 160.0,
            status: "pending",
            remark: "需要特别照顾",
            createTime: "2024-01-20T15:30:00",
            updateTime: "2024-01-20T15:30:00"
        )
    ]
}
