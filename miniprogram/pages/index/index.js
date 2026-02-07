const config = require('../../utils/config')

Page({
    data: {
        pets: [],
        loading: true
    },

    onLoad() {
        this.fetchPets()
    },

    fetchPets() {
        const that = this
        wx.request({
            url: `${config.baseURL}/pets`,
            method: 'GET',
            success(res) {
                if (res.data.code === 200) {
                    that.setData({
                        pets: res.data.data,
                        loading: false
                    })
                }
            },
            fail() {
                wx.showToast({
                    title: '连接后台失败',
                    icon: 'error'
                })
            }
        })
    }
})
