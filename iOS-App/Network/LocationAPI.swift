import Foundation

// MARK: - Location API
class LocationAPI {
    private let client = APIClient.shared

    func findNearbyPets(latitude: Double, longitude: Double, radius: Int = 10, userId: Int64? = nil, petType: String? = nil) async throws -> [Pet] {
        var request = NearbySearchRequestAPI(latitude: latitude, longitude: longitude, radius: radius, userId: userId, petType: petType, serviceTypeId: nil)
        return try await client.requestArray(endpoint: "/nearby/pets", method: .POST, body: request, responseType: [Pet].self)
    }

    func findNearbyUsers(latitude: Double, longitude: Double, radius: Int = 10, userId: Int64? = nil) async throws -> [User] {
        var request = NearbySearchRequestAPI(latitude: latitude, longitude: longitude, radius: radius, userId: userId, petType: nil, serviceTypeId: nil)
        return try await client.requestArray(endpoint: "/nearby/users", method: .POST, body: request, responseType: [User].self)
    }

    func findNearbyServices(latitude: Double, longitude: Double, radius: Int = 10, serviceTypeId: Int64? = nil) async throws -> [ServicePublish] {
        var request = NearbySearchRequestAPI(latitude: latitude, longitude: longitude, radius: radius, userId: nil, petType: nil, serviceTypeId: serviceTypeId)
        return try await client.requestArray(endpoint: "/nearby/services", method: .POST, body: request, responseType: [ServicePublish].self)
    }

    func calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double) async throws -> Double {
        let result: String = try await client.request(
            endpoint: "/nearby/distance?lat1=\(lat1)&lon1=\(lon1)&lat2=\(lat2)&lon2=\(lon2)",
            responseType: String.self
        )
        return Double(result) ?? 0.0
    }
}

// MARK: - Nearby Search Request (for API calls)
struct NearbySearchRequestAPI: Codable {
    let latitude: Double
    let longitude: Double
    let radius: Int
    let userId: Int64?
    let petType: String?
    let serviceTypeId: Int64?
}

// MARK: - Upload API
class UploadAPI {
    private let client = APIClient.shared

    func uploadAvatar(imageData: Data, fileName: String = "avatar.jpg") async throws -> [String: Any] {
        return try await client.uploadImage(endpoint: "/upload/avatar", imageData: imageData, fileName: fileName)
    }

    func uploadImage(imageData: Data, fileName: String = "image.jpg") async throws -> [String: Any] {
        return try await client.uploadImage(endpoint: "/upload/image", imageData: imageData, fileName: fileName)
    }

    func uploadImages(imageDataArray: [Data]) async throws -> [String] {
        // For multiple images, you'd need to implement batch upload on server side
        // For now, uploading one by one
        var urls: [String] = []
        for imageData in imageDataArray {
            let result = try await uploadImage(imageData: imageData)
            if let url = result["url"] as? String {
                urls.append(url)
            }
        }
        return urls
    }
}
