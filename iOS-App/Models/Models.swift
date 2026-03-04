import SwiftUI

struct PetProfile: Identifiable {
    let id = UUID()
    let name: String
    let imageName: String
    let ownerName: String?
}

struct OldFeedPost: Identifiable {
    let id = UUID()
    let author: String
    let authorAvatar: String
    let postImage: String
    let likes: Int
    let comments: Int
    let description: String
}

let mockStories = [
    PetProfile(name: "Buddy", imageName: "p1", ownerName: nil),
    PetProfile(name: "Luna", imageName: "p2", ownerName: nil),
    PetProfile(name: "Milo", imageName: "p3", ownerName: nil),
    PetProfile(name: "Bella", imageName: "p4", ownerName: nil),
    PetProfile(name: "Charlie", imageName: "p5", ownerName: nil)
]

let mockFeed = [
    OldFeedPost(author: "Viotta", authorAvatar: "u1", postImage: "cat_main", likes: 1240, comments: 45, description: "Enjoying the afternoon sun! 🐱"),
    OldFeedPost(author: "James", authorAvatar: "u2", postImage: "dog_run", likes: 890, comments: 12, description: "Beach day with Max! 🏖️")
]
