import SwiftUI

struct LoginView: View {
    @StateObject private var authService = AuthService.shared
    @State private var username = ""
    @State private var password = ""
    @State private var email = ""
    @State private var isRegistering = false
    @State private var isLoading = false
    @State private var errorMessage = ""
    
    var body: some View {
        ZStack {
            // Background Gradient
            Theme.accentGradient
                .ignoresSafeArea()
            
            // Decorative Spheres
            Circle()
                .fill(Color.white.opacity(0.1))
                .frame(width: 300, height: 300)
                .offset(x: -150, y: -250)
            
            Circle()
                .fill(Color.white.opacity(0.05))
                .frame(width: 200, height: 200)
                .offset(x: 150, y: 300)
            
            VStack(spacing: 30) {
                // Logo/Header
                VStack(spacing: 8) {
                    Image(systemName: "pawprint.circle.fill")
                        .font(.system(size: 80))
                        .foregroundColor(.white)
                        .shadow(radius: 10)
                    
                    Text("PetConnect")
                        .font(.system(size: 34, weight: .bold, design: .rounded))
                        .foregroundColor(.white)
                }
                .padding(.top, 50)
                
                // Login Form
                VStack(spacing: 20) {
                    Text(isRegistering ? "创建账号" : "欢迎回来")
                        .font(.title2)
                        .fontWeight(.semibold)
                        .foregroundColor(.white)
                    
                    VStack(spacing: 15) {
                        CustomTextField(icon: "person.fill", placeholder: "用户名", text: $username)
                        
                        if isRegistering {
                            CustomTextField(icon: "envelope.fill", placeholder: "邮箱", text: $email)
                        }
                        
                        CustomSecureField(icon: "lock.fill", placeholder: "密码", text: $password)
                    }
                    
                    if !errorMessage.isEmpty {
                        Text(errorMessage)
                            .font(.caption)
                            .foregroundColor(.red)
                            .padding(.top, 5)
                            .transition(.opacity)
                    }
                    
                    Button {
                        handleAuth()
                    } label: {
                        if isLoading {
                            ProgressView()
                                .progressViewStyle(CircularProgressViewStyle(tint: .white))
                        } else {
                            Text(isRegistering ? "注册" : "登录")
                                .fontWeight(.bold)
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.white)
                                .foregroundColor(Theme.primaryColor)
                                .cornerRadius(15)
                                .shadow(radius: 5)
                        }
                    }
                    .disabled(isLoading || username.isEmpty || password.isEmpty)
                }
                .padding(30)
                .background(.ultraThinMaterial)
                .cornerRadius(30)
                .overlay(
                    RoundedRectangle(cornerRadius: 30)
                        .stroke(Color.white.opacity(0.2), lineWidth: 1)
                )
                .padding(.horizontal, 20)
                
                // Toggle Button
                Button {
                    withAnimation {
                        isRegistering.toggle()
                        errorMessage = ""
                    }
                } label: {
                    Text(isRegistering ? "已有账号？点击登录" : "没有账号？点击注册")
                        .font(.footnote)
                        .foregroundColor(.white.opacity(0.8))
                }
                
                Spacer()
            }
        }
    }
    
    private func handleAuth() {
        isLoading = true
        errorMessage = ""
        
        Task {
            do {
                if isRegistering {
                    try await authService.register(username: username, password: password, email: email)
                } else {
                    try await authService.login(username: username, password: password)
                }
            } catch {
                errorMessage = "操作失败: \(error.localizedDescription)"
            }
            isLoading = false
        }
    }
}

struct CustomTextField: View {
    let icon: String
    let placeholder: String
    @Binding var text: String
    
    var body: some View {
        HStack {
            Image(systemName: icon)
                .foregroundColor(.white.opacity(0.7))
                .frame(width: 20)
            
            TextField("", text: $text, prompt: Text(placeholder).foregroundColor(.white.opacity(0.5)))
                .foregroundColor(.white)
        }
        .padding()
        .background(Color.white.opacity(0.1))
        .cornerRadius(12)
    }
}

struct CustomSecureField: View {
    let icon: String
    let placeholder: String
    @Binding var text: String
    
    var body: some View {
        HStack {
            Image(systemName: icon)
                .foregroundColor(.white.opacity(0.7))
                .frame(width: 20)
            
            SecureField("", text: $text, prompt: Text(placeholder).foregroundColor(.white.opacity(0.5)))
                .foregroundColor(.white)
        }
        .padding()
        .background(Color.white.opacity(0.1))
        .cornerRadius(12)
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
