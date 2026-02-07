import Foundation

// MARK: - Pet API
class PetAPI {
    private let client = APIClient.shared

    func getAllPets() async throws -> [Pet] {
        return try await client.requestArray(endpoint: "/pets", responseType: [Pet].self)
    }

    func getPet(id: Int64) async throws -> Pet {
        return try await client.request(endpoint: "/pets/\(id)", responseType: Pet.self)
    }

    func getPetsByUser(userId: Int64) async throws -> [Pet] {
        return try await client.requestArray(endpoint: "/pets/user/\(userId)", responseType: [Pet].self)
    }

    func createPet(_ pet: Pet) async throws -> Pet {
        return try await client.request(endpoint: "/pets", method: .POST, body: pet, responseType: Pet.self)
    }

    func updatePet(id: Int64, pet: Pet) async throws -> Pet {
        return try await client.request(endpoint: "/pets/\(id)", method: .PUT, body: pet, responseType: Pet.self)
    }

    func deletePet(id: Int64) async throws {
        try await client.requestVoid(endpoint: "/pets/\(id)", method: .DELETE)
    }
}

// MARK: - Create Pet Request
struct CreatePetRequest: Codable {
    let userId: Int64
    let petName: String
    let petType: String
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

    enum CodingKeys: String, CodingKey {
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
    }
}
