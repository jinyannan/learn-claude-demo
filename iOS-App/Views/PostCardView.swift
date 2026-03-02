import SwiftUI

struct PostCardView: View {
    let post: FeedPost
    let onLike: () -> Void
    
    // Aesthetic Tokens
    private let cardCornerRadius: CGFloat = 24
    private let primaryBrandColor = Color(red: 1.0, green: 0.45, blue: 0.25) // Vibrant Coral/Orange
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            
            // 1. Header: User Info
            HStack(spacing: 12) {
                AsyncImage(url: URL(string: post.userAvatar)) { image in
                    image.resizable().scaledToFill()
                } placeholder: {
                    Circle().fill(Color.gray.opacity(0.2))
                }
                .frame(width: 44, height: 44)
                .clipShape(Circle())
                
                VStack(alignment: .leading, spacing: 4) {
                    Text(post.userName)
                        .font(.system(size: 16, weight: .bold, design: .rounded))
                        .foregroundColor(.primary)
                    
                    if let time = post.createTime {
                        Text(time.prefix(10)) // Simple format for now
                            .font(.system(size: 12, weight: .medium, design: .rounded))
                            .foregroundColor(.secondary)
                    }
                }
                
                Spacer()
                
                // Action menu (three dots)
                Button(action: {}) {
                    Image(systemName: "ellipsis")
                        .foregroundColor(.gray)
                        .padding(8)
                }
            }
            .padding([.horizontal, .top], 16)
            
            // 2. Main Content
            Text(post.content)
                .font(.system(size: 15, weight: .regular, design: .default))
                .foregroundColor(.primary.opacity(0.9))
                .lineSpacing(4)
                .lineLimit(4)
                .padding(.horizontal, 16)
            
            // 3. Images Gallery (Display first image as Hero for now)
            if let firstImage = post.images.first, !firstImage.isEmpty {
                AsyncImage(url: URL(string: firstImage)) { image in
                    image.resizable().scaledToFill()
                } placeholder: {
                    Rectangle()
                        .fill(Color.gray.opacity(0.1))
                        .overlay(ProgressView())
                }
                .frame(height: 220)
                .frame(maxWidth: .infinity)
                .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
                .padding(.horizontal, 16)
            }
            
            // 4. Action Bar
            HStack(spacing: 24) {
                // Like Button
                Button(action: onLike) {
                    HStack(spacing: 6) {
                        Image(systemName: post.isLiked ? "pawprint.fill" : "pawprint")
                            .font(.system(size: 18))
                            .foregroundColor(post.isLiked ? primaryBrandColor : .gray)
                            // Pop animation layer would go here
                        Text("\(post.likeCount)")
                            .font(.system(size: 14, weight: .bold, design: .rounded))
                            .foregroundColor(post.isLiked ? primaryBrandColor : .gray)
                    }
                    .padding(.vertical, 8)
                    .padding(.horizontal, 12)
                    .background(
                        RoundedRectangle(cornerRadius: 20)
                            .fill(post.isLiked ? primaryBrandColor.opacity(0.1) : Color.clear)
                    )
                }
                
                // Comment Button
                Button(action: {}) {
                    HStack(spacing: 6) {
                        Image(systemName: "bubble.right")
                            .font(.system(size: 16))
                            .foregroundColor(.gray)
                        Text("\(post.commentCount)")
                            .font(.system(size: 14, weight: .semibold, design: .rounded))
                            .foregroundColor(.gray)
                    }
                }
                
                Spacer()
                
                // Share
                Button(action: {}) {
                    Image(systemName: "square.and.arrow.up")
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundColor(.gray)
                }
                .padding(.trailing, 16)
            }
            .padding(.leading, 4)
            .padding(.bottom, 16)
            
        }
        .background(Color(.systemBackground))
        .clipShape(RoundedRectangle(cornerRadius: cardCornerRadius, style: .continuous))
        .shadow(color: Color.black.opacity(0.04), radius: 16, x: 0, y: 8) // Premium soft shadow
        .padding(.horizontal, 16)
        .padding(.vertical, 8)
    }
}
