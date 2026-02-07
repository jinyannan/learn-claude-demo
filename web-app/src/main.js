import './style.css'
import { apiClient } from './api.js'
import { components } from './components.js'
import { auth } from './auth.js'

const mainContent = document.querySelector('#main-content');
const navItems = document.querySelectorAll('.nav-item');
const authOverlay = document.querySelector('#auth-overlay');
const authTitle = document.querySelector('#auth-title');
const authUsername = document.querySelector('#auth-username');
const authPassword = document.querySelector('#auth-password');
const authEmail = document.querySelector('#auth-email');
const registerFields = document.querySelector('#register-fields');
const authSubmit = document.querySelector('#auth-submit');
const authToggle = document.querySelector('#auth-toggle');
const authError = document.querySelector('#auth-error');

let isRegisterMode = false;

const pages = {
  home: async () => {
    mainContent.innerHTML = '<h2 style="margin: 20px; color: var(--text-primary);">我的宠物</h2>';
    try {
      const pets = await apiClient.get('/pets');
      if (pets && pets.length > 0) {
        pets.forEach(pet => {
          mainContent.appendChild(components.petCard(pet));
        });
      } else {
        mainContent.appendChild(components.emptyState('你还没有添加宠物哦'));
      }
    } catch (e) {
      mainContent.appendChild(components.emptyState('无法连接到服务器，请确保后端已启动'));
    }
  },

  discover: async () => {
    mainContent.innerHTML = '<h2 style="margin: 20px; color: var(--text-primary);">发现宠物</h2>';
    pages.home();
  },

  community: async () => {
    mainContent.innerHTML = '<h2 style="margin: 20px; color: var(--text-primary);">社区动态</h2>';
    try {
      const posts = await apiClient.get('/posts');
      if (posts && posts.length > 0) {
        posts.forEach(post => {
          mainContent.appendChild(components.postCard(post));
        });
      } else {
        mainContent.appendChild(components.emptyState('快来发表第一条动态吧'));
      }
    } catch (e) {
      mainContent.appendChild(components.emptyState('社区正在维护中...'));
    }
  },

  profile: () => {
    if (!auth.isAuthenticated) {
      showAuth();
      return;
    }

    mainContent.innerHTML = `
      <div style="text-align: center; padding: 40px;">
        <div style="width: 100px; height: 100px; border-radius: 50%; background: var(--accent-gradient); margin: 0 auto 20px;"></div>
        <h2 style="color: var(--text-primary);">${auth.user.username}</h2>
        <p style="color: var(--text-secondary);">欢迎回来！</p>
        
        <div class="glass-effect" style="margin-top: 40px; border-radius: 20px; text-align: left;">
          <div style="padding: 16px; border-bottom: 1px solid rgba(0,0,0,0.05);">设置</div>
          <div style="padding: 16px; border-bottom: 1px solid rgba(0,0,0,0.05);">关于 PetConnect</div>
          <div id="logout-btn" style="padding: 16px; color: #FF4D4D; cursor: pointer;">退出登录</div>
        </div>
      </div>
    `;

    document.querySelector('#logout-btn').addEventListener('click', () => {
      auth.logout();
      navigate('home');
    });
  }
};

function showAuth() {
  authOverlay.classList.remove('hidden');
}

function hideAuth() {
  authOverlay.classList.add('hidden');
}

async function navigate(pageId) {
  navItems.forEach(item => {
    item.classList.toggle('active', item.dataset.page === pageId);
  });

  if (pages[pageId]) {
    await pages[pageId]();
  }
}

// Auth Events
authToggle.addEventListener('click', () => {
  isRegisterMode = !isRegisterMode;
  authTitle.textContent = isRegisterMode ? '加入 PetConnect' : '欢迎回来';
  authSubmit.textContent = isRegisterMode ? '注册' : '登录';
  authToggle.textContent = isRegisterMode ? '已有账号？点击登录' : '没有账号？点击注册';
  registerFields.classList.toggle('hidden', !isRegisterMode);
  authError.classList.add('hidden');
});

authSubmit.addEventListener('click', async () => {
  authError.classList.add('hidden');
  const username = authUsername.value;
  const password = authPassword.value;
  const email = authEmail.value;

  if (!username || !password) {
    authError.textContent = '请输入用户名和密码';
    authError.classList.remove('hidden');
    return;
  }

  authSubmit.disabled = true;
  authSubmit.textContent = '处理中...';

  let result;
  if (isRegisterMode) {
    result = await auth.register(username, password, email);
  } else {
    result = await auth.login(username, password);
  }

  if (result.success) {
    hideAuth();
    authUsername.value = '';
    authPassword.value = '';
    authEmail.value = '';
    navigate('profile');
  } else {
    authError.textContent = result.message;
    authError.classList.remove('hidden');
  }

  authSubmit.disabled = false;
  authSubmit.textContent = isRegisterMode ? '注册' : '登录';
});

// Event Listeners for Nav
navItems.forEach(item => {
  item.addEventListener('click', (e) => {
    e.preventDefault();
    navigate(item.dataset.page);
  });
});

// Initial load
navigate('home');
