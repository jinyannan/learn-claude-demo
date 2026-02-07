const isDev = true;

const config = {
    // 开发环境使用本地 IP (建议换成你自己的局域网 IP，方便真机测试)
    // 生产环境换成阿里云域名
    baseURL: isDev ? 'http://localhost:8080/api' : 'https://api.yourdomain.com/api',
    uploadURL: isDev ? 'http://localhost:8080/api/upload' : 'https://api.yourdomain.com/api/upload'
};

module.exports = config;
