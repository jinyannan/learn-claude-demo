import SwiftUI

struct FeedView: View {
    @StateObject private var viewModel = PostViewModel()
    @State private var selectedPostType: String? = nil

    private let postTypes = [
        (type: "daily", title: "日常", icon: "calendar"),
        (type: "qa", title: "问答", icon: "questionmark.circle"),
        (type: "lost", title: "走失", icon: "magnifyingglass"),
        (type: "found", title: "捡到", icon: "exclamationmark.triangle")
    ]

    var body: some View {
        NavigationView {
            VStack(spacing: 0) {
                // Post Type Filter
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 12) {
                        ForEach(postTypes, id: \.type) { postType in
                            PostTypeChip(
                                title: postType.title,
                                icon: postType.icon,
                                isSelected: selectedPostType == postType.type
                            ) {
                                withAnimation {
                                    selectedPostType = postType.type
                                    viewModel.loadPostsByType(postType: postType.type)
                                }
                            }
                        }
                    }
                    .padding(.horizontal)
                    .padding(.vertical, 12)
                }
                .background(Color.white)

                Divider()

                // Posts List
                Group {
                    if viewModel.posts.isEmpty {
                        emptyPostsView
                    } else {
                        postsList
                    }
                }

                if viewModel.isLoading {
                    ProgressView()
                        .padding()
                }
            }
            .navigationTitle("社区动态")
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        // Create new post
                    } label: {
                        Image(systemName: "plus.circle.fill")
                            .font(.title2)
                            .foregroundColor(Theme.primaryColor)
                    }
                }
            }
            .refreshable {
                if let type = selectedPostType {
                    viewModel.loadPostsByType(postType: type)
                } else {
                    viewModel.loadAllPosts()
                }
            }
            .onAppear {
                viewModel.loadMockData()
            }
        }
    }

    private var emptyPostsView: some View {
        VStack(spacing: 16) {
            Image(systemName: "newspaper")
                .font(.system(size: 60))
                .foregroundColor(Theme.textSecondary)

            Text("还没有动态")
                .font(.headline)
                .foregroundColor(Theme.textPrimary)

            Text("发布第一条动态吧！")
                .font(.subheadline)
                .foregroundColor(Theme.textSecondary)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }

    private var postsList: some View {
        ScrollView {
            LazyVStack(spacing: 16) {
                ForEach(viewModel.posts) { post in
                    FeedPostCard(post: post, viewModel: viewModel)
                }
            }
            .padding()
        }
    }
}

// MARK: - Post Type Chip
struct PostTypeChip: View {
    let title: String
    let icon: String
    let isSelected: Bool
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            HStack(spacing: 6) {
                Image(systemName: icon)
                Text(title)
            }
            .font(.subheadline)
            .fontWeight(isSelected ? .semibold : .regular)
            .foregroundColor(isSelected ? .white : Theme.textPrimary)
            .padding(.horizontal, 16)
            .padding(.vertical, 10)
            .background(isSelected ? Theme.primaryColor : Color.gray.opacity(0.2))
            .cornerRadius(20)
        }
    }
}

// MARK: - Feed Post Card
struct FeedPostCard: View {
    let post: CommunityPost
    let viewModel: PostViewModel
    @State private var isLiked = false
    @State private var showingComments = false

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            // Header
            HStack {
                Circle()
                    .fill(Theme.secondaryColor.opacity(0.3))
                    .frame(width: 40, height: 40)
                    .overlay(
                        Image(systemName: "person.circle.fill")
                            .foregroundColor(Theme.secondaryColor)
                    )

                VStack(alignment: .leading, spacing: 2) {
                    Text("用户\(post.userId)")
                        .font(.subheadline)
                        .fontWeight(.semibold)
                        .foregroundColor(Theme.textPrimary)

                    Text(formatDate(post.createTime))
                        .font(.caption)
                        .foregroundColor(Theme.textSecondary)
                }

                Spacer()

                if let postType = post.postType {
                    Text(postTypeTitle(postType))
                        .font(.caption)
                        .foregroundColor(.white)
                        .padding(.horizontal, 10)
                        .padding(.vertical, 4)
                        .background(postTypeColor(postType))
                        .cornerRadius(8)
                }
            }

            // Title & Content
            VStack(alignment: .leading, spacing: 8) {
                Text(post.title)
                    .font(.headline)
                    .foregroundColor(Theme.textPrimary)

                Text(post.content)
                    .font(.body)
                    .foregroundColor(Theme.textPrimary)
                    .lineLimit(isLiked == true ? nil : 3)
            }

            // Images
            if let images = post.images, !images.isEmpty {
                let imageUrls = images.components(separatedBy: ",")
                GeometryReader { geometry in
                    TabView {
                        ForEach(imageUrls.indices, id: \.self) { index in
                            let urlString = imageUrls[index].trimmingCharacters(in: .whitespaces)
                            if let url = URL(string: urlString) {
                                AsyncImage(url: url) { phase in
                                    switch phase {
                                    case .success(let image):
                                        image
                                            .resizable()
                                            .aspectRatio(contentMode: .fill)
                                    case .failure(_):
                                        Rectangle()
                                            .fill(Theme.backgroundColor)
                                            .overlay(
                                                Image(systemName: "photo")
                                                    .foregroundColor(Theme.textSecondary)
                                            )
                                    case .empty:
                                        ProgressView()
                                    @unknown default:
                                        EmptyView()
                                    }
                                }
                            } else {
                                Rectangle()
                                    .fill(Theme.backgroundColor)
                            }
                        }
                    }
                    .frame(height: 250)
                    .cornerRadius(12)
                    .tabViewStyle(.page(indexDisplayMode: imageUrls.count > 1 ? .always : .never))
                }
                .frame(height: 250)
            }

            // Tags
            if let tags = post.tags, !tags.isEmpty {
                FlowLayout(spacing: 8) {
                    ForEach(tags.components(separatedBy: ","), id: \.self) { tag in
                        Text("#\(tag.trimmingCharacters(in: .whitespaces))")
                            .font(.caption)
                            .foregroundColor(Theme.primaryColor)
                            .padding(.horizontal, 10)
                            .padding(.vertical, 4)
                            .background(Theme.primaryColor.opacity(0.15))
                            .cornerRadius(8)
                    }
                }
            }

            Divider()

            // Actions
            HStack(spacing: 30) {
                // Like
                Button {
                    withAnimation(.spring()) {
                        isLiked.toggle()
                        if let postId = post.id {
                            viewModel.likePost(postId: postId, userId: 100)
                        }
                    }
                } label: {
                    HStack(spacing: 6) {
                        Image(systemName: isLiked ? "heart.fill" : "heart")
                            .foregroundColor(isLiked ? Theme.accentColor : Theme.textPrimary)
                            .font(.title3)

                        Text("\(post.likeCount ?? 0)")
                            .font(.subheadline)
                            .foregroundColor(Theme.textPrimary)
                    }
                }

                // Comment
                Button {
                    showingComments = true
                } label: {
                    HStack(spacing: 6) {
                        Image(systemName: "bubble.right")
                            .foregroundColor(Theme.textPrimary)
                            .font(.title3)

                        Text("\(post.commentCount ?? 0)")
                            .font(.subheadline)
                            .foregroundColor(Theme.textPrimary)
                    }
                }

                // Share
                Button {
                    // Handle share
                } label: {
                    Image(systemName: "arrow.turn.up.right")
                        .foregroundColor(Theme.textPrimary)
                        .font(.title3)
                }

                Spacer()
            }
        }
        .padding()
        .background(Color.white)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.05), radius: 5, x: 0, y: 2)
        .sheet(isPresented: $showingComments) {
            CommentsView(postId: post.id ?? 0, viewModel: viewModel)
        }
    }

    private func postTypeTitle(_ type: String) -> String {
        switch type {
        case "daily": return "日常"
        case "qa": return "问答"
        case "lost": return "走失"
        case "found": return "捡到"
        default: return type
        }
    }

    private func postTypeColor(_ type: String) -> Color {
        switch type {
        case "daily": return Theme.primaryColor
        case "qa": return Theme.secondaryColor
        case "lost": return Color.red
        case "found": return Color.green
        default: return Theme.primaryColor
        }
    }

    private func formatDate(_ dateString: String?) -> String {
        guard let dateString = dateString else { return "" }
        // Simple date formatting - in production, use DateFormatter
        return dateString.prefix(16).description
    }
}

// MARK: - Comments View
struct CommentsView: View {
    let postId: Int64
    let viewModel: PostViewModel
    @Environment(\.dismiss) var dismiss
    @State private var commentText = ""

    var body: some View {
        NavigationView {
            VStack {
                // Comments List
                ScrollView {
                    LazyVStack(spacing: 16) {
                        ForEach(viewModel.comments) { comment in
                            CommentRow(comment: comment)
                        }
                    }
                    .padding()
                }

                Divider()

                // Comment Input
                HStack(spacing: 12) {
                    TextField("写评论...", text: $commentText)
                        .textFieldStyle(.roundedBorder)

                    Button("发送") {
                        if !commentText.isEmpty {
                            let request = CreateCommentRequest(
                                postId: postId,
                                userId: 100,
                                content: commentText,
                                parentId: nil,
                                replyToUserId: nil
                            )
                            viewModel.addComment(postId: postId, request)
                            commentText = ""
                        }
                    }
                    .buttonStyle(.borderedProminent)
                    .disabled(commentText.isEmpty)
                }
                .padding()
            }
            .navigationTitle("评论")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button("关闭") {
                        dismiss()
                    }
                }
            }
            .onAppear {
                viewModel.loadComments(postId: postId)
            }
        }
    }
}

// MARK: - Comment Row
struct CommentRow: View {
    let comment: PostComment

    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            Circle()
                .fill(Theme.secondaryColor.opacity(0.3))
                .frame(width: 32, height: 32)
                .overlay(
                    Image(systemName: "person.circle.fill")
                        .font(.title3)
                        .foregroundColor(Theme.secondaryColor)
                )

            VStack(alignment: .leading, spacing: 4) {
                Text("用户\(comment.userId)")
                    .font(.subheadline)
                    .fontWeight(.semibold)
                    .foregroundColor(Theme.textPrimary)

                Text(comment.content)
                    .font(.body)
                    .foregroundColor(Theme.textPrimary)

                Text(formatDate(comment.createTime))
                    .font(.caption)
                    .foregroundColor(Theme.textSecondary)
            }
        }
    }

    private func formatDate(_ dateString: String?) -> String {
        guard let dateString = dateString else { return "" }
        return dateString.prefix(16).description
    }
}

// MARK: - Flow Layout for Tags
struct FlowLayout: Layout {
    var spacing: CGFloat = 8

    func sizeThatFits(proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) -> CGSize {
        let result = FlowResult(
            in: proposal.replacingUnspecifiedDimensions().width,
            subviews: subviews,
            spacing: spacing
        )
        return result.size
    }

    func placeSubviews(in bounds: CGRect, proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) {
        let result = FlowResult(
            in: bounds.width,
            subviews: subviews,
            spacing: spacing
        )
        for (index, subview) in subviews.enumerated() {
            subview.place(at: CGPoint(x: bounds.minX + result.positions[index].x,
                                     y: bounds.minY + result.positions[index].y),
                         proposal: .unspecified)
        }
    }

    struct FlowResult {
        var size: CGSize = .zero
        var positions: [CGPoint] = []

        init(in maxWidth: CGFloat, subviews: Subviews, spacing: CGFloat) {
            var currentX: CGFloat = 0
            var currentY: CGFloat = 0
            var lineHeight: CGFloat = 0

            for subview in subviews {
                let size = subview.sizeThatFits(.unspecified)

                if currentX + size.width > maxWidth && currentX > 0 {
                    currentX = 0
                    currentY += lineHeight + spacing
                    lineHeight = 0
                }

                positions.append(CGPoint(x: currentX, y: currentY))
                currentX += size.width + spacing
                lineHeight = max(lineHeight, size.height)
            }

            self.size = CGSize(width: maxWidth, height: currentY + lineHeight)
        }
    }
}

#Preview {
    FeedView()
}
