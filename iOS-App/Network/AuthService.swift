import Foundation

struct AuthResponse: Codable {
    let code: Int
    let message: String
    let data: User?
}

class AuthService: ObservableObject {
    @Published var currentUser: User?
    @Published var isAuthenticated = false
    
    private let baseURL = APIConfig.baseURL
    
    static let shared = AuthService()
    
    private init() {
        // Load user from storage if needed
    }
    
    func login(username: String, password: String) async throws {
        guard let url = URL(string: "\(baseURL)/auth/login") else {
            throw APIError.invalidURL
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let body = ["username": username, "password": password]
        request.httpBody = try JSONSerialization.data(withJSONObject: body)
        
        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 200 else {
            throw APIError.unknownError
        }
        
        let decodedResponse = try JSONDecoder().decode(AuthResponse.self, from: data)
        if decodedResponse.code == 200, let user = decodedResponse.data {
            DispatchQueue.main.async {
                self.currentUser = user
                self.isAuthenticated = true
            }
        } else {
            throw APIError.serverError(code: decodedResponse.code, message: decodedResponse.message)
        }
    }
    
    func register(username: String, password: String, email: String) async throws {
        guard let url = URL(string: "\(baseURL)/auth/register") else {
            throw APIError.invalidURL
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let body = ["username": username, "password": password, "email": email]
        request.httpBody = try JSONSerialization.data(withJSONObject: body)
        
        let (data, _) = try await URLSession.shared.data(for: request)
        let decodedResponse = try JSONDecoder().decode(AuthResponse.self, from: data)
        
        if decodedResponse.code == 200, let user = decodedResponse.data {
            DispatchQueue.main.async {
                self.currentUser = user
                self.isAuthenticated = true
            }
        } else {
            throw APIError.serverError(code: decodedResponse.code, message: decodedResponse.message)
        }
    }
    
    func logout() {
        DispatchQueue.main.async {
            self.currentUser = nil
            self.isAuthenticated = false
        }
    }
}
