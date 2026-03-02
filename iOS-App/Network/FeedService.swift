import Foundation

class FeedService {
    static let shared = FeedService()
    
    // Replace with actual backend IP when running on device
    private let baseURL = "http://localhost:8080/api/v1/feed"
    
    func fetchFeed(page: Int, size: Int) async throws -> [FeedPost] {
        guard let url = URL(string: "\(baseURL)?page=\(page)&size=\(size)") else {
            throw URLError(.badURL)
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        // Use a mock user ID for now to simulate logged-in state fetching engagement
        request.addValue("1", forHTTPHeaderField: "X-User-Id")
        request.addValue("application/json", forHTTPHeaderField: "Accept")
        
        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard let httpRes = response as? HTTPURLResponse, (200...299).contains(httpRes.statusCode) else {
            throw URLError(.badServerResponse)
        }
        
        // Need to set up decoder perfectly for dates if needed, omitting custom formatter for demo ease
        let decoder = JSONDecoder()
        
        return try decoder.decode([FeedPost].self, from: data)
    }
    
    func toggleLike(postId: Int) async throws {
        guard let url = URL(string: "\(baseURL)/\(postId)/like") else {
            throw URLError(.badURL)
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("1", forHTTPHeaderField: "X-User-Id")
        
        let (_, response) = try await URLSession.shared.data(for: request)
        
        guard let httpRes = response as? HTTPURLResponse, (200...299).contains(httpRes.statusCode) else {
            throw URLError(.badServerResponse)
        }
    }
}
