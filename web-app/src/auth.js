import { apiClient } from './api.js';

class Auth {
    constructor() {
        this.user = JSON.parse(localStorage.getItem('user')) || null;
        this.isAuthenticated = !!this.user;
    }

    async login(username, password) {
        try {
            const result = await apiClient.post('/auth/login', { username, password });
            if (result.code === 200) {
                this.user = result.data;
                this.isAuthenticated = true;
                localStorage.setItem('user', JSON.stringify(this.user));
                return { success: true };
            }
            return { success: false, message: result.message };
        } catch (e) {
            return { success: false, message: '服务器连接失败' };
        }
    }

    async register(username, password, email) {
        try {
            const result = await apiClient.post('/auth/register', { username, password, email });
            if (result.code === 200) {
                this.user = result.data;
                this.isAuthenticated = true;
                localStorage.setItem('user', JSON.stringify(this.user));
                return { success: true };
            }
            return { success: false, message: result.message };
        } catch (e) {
            return { success: false, message: '注册失败' };
        }
    }

    logout() {
        this.user = null;
        this.isAuthenticated = false;
        localStorage.removeItem('user');
    }
}

export const auth = new Auth();
