import Foundation

@MainActor
class PetViewModel: ObservableObject {
    private let api = PetAPI()

    @Published var pets: [Pet] = []
    @Published var myPets: [Pet] = []
    @Published var currentPet: Pet?
    @Published var isLoading = false
    @Published var errorMessage: String?

    func loadAllPets() {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.pets = try await api.getAllPets()
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func loadMyPets(userId: Int64) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.myPets = try await api.getPetsByUser(userId: userId)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func loadPet(id: Int64) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.currentPet = try await api.getPet(id: id)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func createPet(_ request: CreatePetRequest) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                let newPet = try await api.createPet(
                    Pet(
                        id: nil,
                        userId: request.userId,
                        petName: request.petName,
                        petType: request.petType,
                        breed: request.breed,
                        gender: request.gender,
                        birthDate: request.birthDate,
                        weight: request.weight,
                        avatar: request.avatar,
                        personality: request.personality,
                        healthStatus: request.healthStatus,
                        sterilized: request.sterilized,
                        vaccinated: request.vaccinated,
                        description: request.description,
                        status: 0,
                        createTime: nil,
                        updateTime: nil
                    )
                )
                self.myPets.append(newPet)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func updatePet(id: Int64, pet: Pet) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                let updatedPet = try await api.updatePet(id: id, pet)
                if let index = myPets.firstIndex(where: { $0.id == id }) {
                    myPets[index] = updatedPet
                }
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func deletePet(id: Int64) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                try await api.deletePet(id: id)
                self.myPets.removeAll { $0.id == id }
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    // Load mock data for testing
    func loadMockData() {
        self.pets = Pet.mockData
        self.myPets = Array(Pet.mockData.prefix(1))
    }
}
