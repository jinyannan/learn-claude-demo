import Foundation

struct APIConfig {
    static let baseURL = "http://localhost:8080/api"
    static let timeout: TimeInterval = 30

    // For production, change this to your server URL
    #if DEBUG
    static let serverURL = "http://localhost:8080"
    #else
    static let serverURL = "https://your-production-server.com"
    #endif
}

struct AppConstants {
    static let nearbySearchRadius = 10.0 // km
    static let maxImageUploadSize = 10 * 1024 * 1024 // 10MB
}
