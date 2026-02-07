import Foundation

// MARK: - Service API
class ServiceAPI {
    private let client = APIClient.shared

    func getAllServices() async throws -> [ServicePublish] {
        return try await client.requestArray(endpoint: "/services", responseType: [ServicePublish].self)
    }

    func getService(id: Int64) async throws -> ServicePublish {
        return try await client.request(endpoint: "/services/\(id)", responseType: ServicePublish.self)
    }

    func getServicesByUser(userId: Int64) async throws -> [ServicePublish] {
        return try await client.requestArray(endpoint: "/services/user/\(userId)", responseType: [ServicePublish].self)
    }

    func getServicesByType(typeId: Int64) async throws -> [ServicePublish] {
        return try await client.requestArray(endpoint: "/services/type/\(typeId)", responseType: [ServicePublish].self)
    }

    func createService(_ service: ServicePublish) async throws -> ServicePublish {
        return try await client.request(endpoint: "/services", method: .POST, body: service, responseType: ServicePublish.self)
    }

    func updateService(id: Int64, _ service: ServicePublish) async throws -> ServicePublish {
        return try await client.request(endpoint: "/services/\(id)", method: .PUT, body: service, responseType: ServicePublish.self)
    }

    func deleteService(id: Int64) async throws {
        try await client.requestVoid(endpoint: "/services/\(id)", method: .DELETE)
    }

    func createOrder(serviceId: Int64, _ request: CreateServiceOrderRequest) async throws -> ServiceOrder {
        return try await client.request(endpoint: "/services/\(serviceId)/order", method: .POST, body: request, responseType: ServiceOrder.self)
    }

    // MARK: - Order APIs
    func getAllOrders() async throws -> [ServiceOrder] {
        return try await client.requestArray(endpoint: "/orders", responseType: [ServiceOrder].self)
    }

    func getOrder(id: Int64) async throws -> ServiceOrder {
        return try await client.request(endpoint: "/orders/\(id)", responseType: ServiceOrder.self)
    }

    func getOrdersByCustomer(customerId: Int64) async throws -> [ServiceOrder] {
        return try await client.requestArray(endpoint: "/orders/customer/\(customerId)", responseType: [ServiceOrder].self)
    }

    func getOrdersByProvider(providerId: Int64) async throws -> [ServiceOrder] {
        return try await client.requestArray(endpoint: "/orders/provider/\(providerId)", responseType: [ServiceOrder].self)
    }

    func getOrdersByStatus(status: String) async throws -> [ServiceOrder] {
        return try await client.requestArray(endpoint: "/orders/status/\(status)", responseType: [ServiceOrder].self)
    }

    func updateOrderStatus(id: Int64, status: String) async throws -> ServiceOrder {
        // For query params in PUT request
        return try await client.request(endpoint: "/orders/\(id)/status?status=\(status)", method: .PUT, responseType: ServiceOrder.self)
    }

    func deleteOrder(id: Int64) async throws {
        try await client.requestVoid(endpoint: "/orders/\(id)", method: .DELETE)
    }
}

// MARK: - Create Service Order Request
struct CreateServiceOrderRequest: Codable {
    let serviceId: Int64
    let providerId: Int64
    let customerId: Int64
    let petId: Int64
    var serviceTime: String?
    var serviceAddress: String?
    var price: Double?
    var remark: String?

    enum CodingKeys: String, CodingKey {
        case serviceId = "service_id"
        case providerId = "provider_id"
        case customerId = "customer_id"
        case petId = "pet_id"
        case serviceTime = "service_time"
        case serviceAddress = "service_address"
        case price
        case remark
    }
}
