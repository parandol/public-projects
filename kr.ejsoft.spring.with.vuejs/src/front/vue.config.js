module.exports = {
    devServer: {
        proxy: {
            '/data': {
                target: 'http://localhost:8080',
                changeOrigin: true,
//                pathRewrite: {
//                    '^/data': ''
//                }
            }
        }
    }
}