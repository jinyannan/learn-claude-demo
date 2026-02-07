import SwiftUI

struct Theme {
    // Primary Colors
    static let primaryColor = Color(hex: "FF8B8B")
    static let secondaryColor = Color(hex: "FFAC6E")
    static let accentColor = Color(hex: "FF6B6B")

    // Background Colors
    static let backgroundColor = Color(hex: "F8F9FA")
    static let cardBackground = Color.white

    // Text Colors
    static let textPrimary = Color(hex: "1A1A1A")
    static let textSecondary = Color(hex: "8E8E93")

    // Gradient
    static let accentGradient = LinearGradient(
        gradient: Gradient(colors: [primaryColor, secondaryColor]),
        startPoint: .topLeading,
        endPoint: .bottomTrailing
    )
}

struct BrandColors {
    static let background = Color.black
    static let cardBackground = Color.white.opacity(0.1)
    static let accent = LinearGradient(
        gradient: Gradient(colors: [Color(hex: "FFAC6E"), Color(hex: "FF8B8B")]),
        startPoint: .topLeading,
        endPoint: .bottomTrailing
    )
    static let textPrimary = Color.white
    static let textSecondary = Color.gray
}

extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let a, r, g, b: UInt64
        switch hex.count {
        case 3: // RGB (12-bit)
            (a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (1, 1, 1, 0)
        }

        self.init(
            .sRGB,
            red: Double(r) / 255,
            green: Double(g) / 255,
            blue:  Double(b) / 255,
            opacity: Double(a) / 255
        )
    }
}

struct GlassModifier: ViewModifier {
    func body(content: Content) -> some View {
        content
            .background(.ultraThinMaterial)
            .cornerRadius(20)
            .overlay(
                RoundedRectangle(cornerRadius: 20)
                    .stroke(Color.white.opacity(0.1), lineWidth: 0.5)
            )
    }
}

extension View {
    func glassStyle() -> some View {
        self.modifier(GlassModifier())
    }
}
