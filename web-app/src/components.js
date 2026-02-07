export const components = {
    petCard(pet) {
        const card = document.createElement('div');
        card.className = 'pet-card glass-effect fade-in';
        card.innerHTML = `
      <img src="${pet.avatarUrl || 'https://via.placeholder.com/80'}" class="pet-image" alt="${pet.name}">
      <div class="pet-info">
        <div class="pet-name">${pet.name}</div>
        <div class="pet-breed">${pet.breed}</div>
      </div>
    `;
        return card;
    },

    postCard(post) {
        const card = document.createElement('div');
        card.className = 'glass-effect fade-in';
        card.style.margin = '16px';
        card.style.padding = '16px';
        card.style.borderRadius = '20px';
        card.innerHTML = `
      <div style="display: flex; align-items: center; margin-bottom: 12px;">
        <div style="width: 40px; height: 40px; border-radius: 50%; background: var(--accent-gradient); margin-right: 12px;"></div>
        <div>
          <div style="font-weight: 700;">用户 ${post.userId}</div>
          <div style="font-size: 0.8rem; color: var(--text-secondary);">刚刚</div>
        </div>
      </div>
      <div style="margin-bottom: 12px;">${post.content}</div>
      ${post.images ? `<img src="${post.images}" style="width: 100%; border-radius: 12px; height: 200px; object-fit: cover;">` : ''}
    `;
        return card;
    },

    emptyState(message) {
        const div = document.createElement('div');
        div.style.textAlign = 'center';
        div.style.padding = '40px';
        div.style.color = 'var(--text-secondary)';
        div.innerHTML = `<p>${message || '暂无数据'}</p>`;
        return div;
    }
};
