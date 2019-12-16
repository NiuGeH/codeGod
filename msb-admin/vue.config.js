const utils = require('./build/utils')
const webpack = require('webpack')
const CompressionWebpackPlugin = require('compression-webpack-plugin')
const SpriteSmithPlugin = require('webpack-spritesmith')
const UglifyJSPlugin = require('uglifyjs-webpack-plugin')
const path = require('path')
const buildConfig = require('./build/config')

const { pages, plugins, devRewriteUrl } = utils.getMultiEntries()

module.exports = {
    // 输出文件目录
    outputDir: 'frosui',
    assetsDir: 'static',
    publicPath: process.env.NODE_ENV === 'production' ? '../' : '',
    pages: pages,
    parallel: false,

    // 配置merge
    configureWebpack: process.env.NODE_ENV === 'production' ? {
        optimization: {
            minimizer: [
                new UglifyJSPlugin({
                    uglifyOptions: {
                        compress: {
                            drop_console: true
                        }
                    }
                })
            ],
            splitChunks: {
                chunks: 'all',
                cacheGroups: Object.assign({
                    common: {
                        name: 'chunk-common',
                        priority: 1,
                        chunks: 'all',
                        minChunks: 2
                    }
                }, plugins)
            }
        },
        performance: {
            hints: false,
            maxEntrypointSize: 5000000,
            maxAssetSize: 3000000
        },
        plugins: [
            new CompressionWebpackPlugin({
                algorithm: 'gzip',
                test: new RegExp('\\.(js|css|html)$'),
                threshold: 10240,
                minRatio: 0.8
            }),
            new SpriteSmithPlugin({
                src: {
                    cwd: path.resolve(__dirname, './src/assets/image/icon/'),
                    glob: '**/*.png'
                },
                target: {
                    image: path.resolve(__dirname, './src/assets/image/sprite/sprite.png'),
                    css: path.resolve(__dirname, './src/assets/css/sprite.css')
                },
                // 样式文件中调用雪碧图地址写法
                apiOptions: {
                    cssImageRef: '../image/sprite/sprite.png'
                },
                spritesmithOptions: {
                    algorithm: 'left-right',
                    padding: 10
                }
            }),
            new webpack.ProvidePlugin({
                dict: [path.resolve(path.join(__dirname, 'src/assets/js/dict.js')), 'default']
            })
        ],
        externals: {
            apiConfig: 'window.apiConfig',
            AMap: 'window.AMap',
            BMap: 'window.BMap'
        }
    } : {
        plugins: [
            new SpriteSmithPlugin({
                src: {
                    cwd: path.resolve(__dirname, './src/assets/image/icon/'),
                    glob: '**/*.png'
                },
                target: {
                    image: path.resolve(__dirname, './src/assets/image/sprite/sprite.png'),
                    css: path.resolve(__dirname, './src/assets/css/sprite.css')
                },
                // 样式文件中调用雪碧图地址写法
                apiOptions: {
                    cssImageRef: '../image/sprite/sprite.png'
                },
                spritesmithOptions: {
                    algorithm: 'left-right',
                    padding: 10
                }
            }),
            new webpack.ProvidePlugin({
                dict: [path.resolve(path.join(__dirname, 'src/assets/js/dict.js')), 'default']
            })
        ],
        externals: {
            apiConfig: 'window.apiConfig',
            AMap: 'window.AMap'
        },
    },
    chainWebpack: config => {
        if (process.env.ANALYZE) {
            config
                .plugin('webpack-bundle-analyzer')
                .use(require('webpack-bundle-analyzer')
                    .BundleAnalyzerPlugin)
                .end()
        }
        config
            .plugin('copy')
            .tap(options => {
                options[0][0].ignore.push('index.html')
                return options
            })
        config.module.rule('md')
            .test(/\.md/)
            .use('vue-loader')
            .loader('vue-loader')
            .end()
            .use('vue-markdown-loader')
            .loader('vue-markdown-loader/lib/markdown-compiler')
            .options({
                raw: true
            })
        config.resolve.alias
            .set('vue$', 'vue/dist/vue.esm.js')
            .set('@', path.resolve('src'))
            .set('@api', path.resolve('src/assets/js/api'))
            .set('@js', path.resolve('src/assets/js'))
            .set('@store', path.resolve('src/store'))
            .set('@mock', path.resolve('src/mock'))
            .set('@css', path.resolve('src/assets/css'))
            .set('@components', path.resolve('src/components'))
    },
    devServer: {
        port: buildConfig.port,
        proxy: {
            '/api': {
                //target: 'http://117.78.37.58:8000/codeGod/',
                target: 'http://192.168.3.10:8000/codeGod/',
                ws: true,
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            },
            // '/admin-uat': {
            //     target: 'http://172.28.31.129:8980',
            //     ws: true,
            //     changeOrigin: true
            // },
            // '/mock/31/admin-uat': {
            //     target: 'http://172.28.29.205:3000',
            //     ws: true,
            //     changeOrigin: true
            // },
            // '/visprd': {
            //     target: 'http://sc.y2t.com',
            //     ws: true,
            //     changeOrigin: true
            // },
            // '/frosapis': {
            //     target: 'http://172.30.25.66:8070',
            //     ws: true,
            //     changeOrigin: true,
            //     pathRewrite: {
            //         '^/frosapis': ''
            //     }
            // },
            // '/api': {
            //     target: 'http://172.30.254.67:8082',
            //     ws: true,
            //     changeOrigin: true,
            //     pathRewrite: {
            //         '^/api': ''
            //     }
            // },
            // '/check': {
            //     target: 'http://10.231.138.127:8082',
            //     ws: true,
            //     changeOrigin: true,
            //     pathRewrite: {
            //         '^/check': ''
            //     }
            // },
            // 文件上传
            // '/upLoad': {
            //     target: 'http://172.30.254.89:8080',
            //     ws: true,
            //     changeOrigin: true,
            //     pathRewrite: {
            //         '^/upLoad': ''
            //     }
            // },
            // '/validatorApi': {
            //     target: 'http://172.30.254.67:8082',
            //     ws: true,
            //     changeOrigin: true,
            //     pathRewrite: {
            //         '^/validatorApi': ''
            //     }
            // }
        },
        historyApiFallback: {
            rewrites: devRewriteUrl
        },
        allowedHosts: [buildConfig.host]
    }
}