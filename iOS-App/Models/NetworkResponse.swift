import Foundation

// MARK: - API Response Wrapper
struct APIResponse<T: Codable>: Codable {
    let code: Int
    let message: String
    let data: T?
}

// MARK: - API Error
enum APIError: Error, LocalizedError {
    case invalidURL
    case networkError(Error)
    case decodingError(Error)
    case serverError(code: Int, message: String)
    case unknownError

    var errorDescription: String? {
        switch self {
        case .invalidURL:
            return "无效的URL"
        case .networkError(let error):
            return "网络错误: \(error.localizedDescription)"
        case .decodingError(let error):
            return "数据解析错误: \(error.localizedDescription)"
        case .serverError(let code, let message):
            return "服务器错误 (\(code)): \(message)"
        case .unknownError:
            return "未知错误"
        }
    }
}

// MARK: - Request Methods
enum HTTPMethod: String {
    case GET
    case POST
    case PUT
    case DELETE
    case PATCH
}
