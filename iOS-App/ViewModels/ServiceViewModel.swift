import Foundation

@MainActor
class ServiceViewModel: ObservableObject {
    private let api = ServiceAPI()

    @Published var services: [ServicePublish] = []
    @Published var currentService: ServicePublish?
    @Published var orders: [ServiceOrder] = []
    @Published var myOrders: [ServiceOrder] = []
    @Published var isLoading = false
    @Published var errorMessage: String?

    func loadAllServices() {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.services = try await api.getAllServices()
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
                // Fallback to mock data
                self.services = ServicePublish.mockData
            }
        }
    }

    func loadService(id: Int64) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.currentService = try await api.getService(id: id)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func loadServicesByType(typeId: Int64) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.services = try await api.getServicesByType(typeId: typeId)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func createService(_ service: ServicePublish) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                let newService = try await api.createService(service)
                self.services.insert(newService, at: 0)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func createOrder(serviceId: Int64, _ request: CreateServiceOrderRequest) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                let newOrder = try await api.createOrder(serviceId: serviceId, request)
                self.myOrders.append(newOrder)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    func loadMyOrders(customerId: Int64) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                self.myOrders = try await api.getOrdersByCustomer(customerId: customerId)
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
                // Fallback to mock data
                self.myOrders = ServiceOrder.mockData
            }
        }
    }

    func updateOrderStatus(id: Int64, status: String) {
        isLoading = true
        errorMessage = nil

        Task {
            do {
                let updatedOrder = try await api.updateOrderStatus(id: id, status: status)
                if let index = myOrders.firstIndex(where: { $0.id == id }) {
                    myOrders[index] = updatedOrder
                }
                self.isLoading = false
            } catch {
                self.errorMessage = error.localizedDescription
                self.isLoading = false
            }
        }
    }

    // Load mock data for testing
    func loadMockData() {
        self.services = ServicePublish.mockData
        self.myOrders = ServiceOrder.mockData
    }
}
