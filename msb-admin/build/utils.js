'use strict'
const glob = require('glob')
const path = require('path')
const buildConfig = require('../build/config')

// build tool set

module.exports = {
  getMultiFiles: function (globPath) {
    // get multi pages's entry and template
    let entries = {}

    glob.sync(globPath).forEach(function (entry) {
      let pathList = entry.split('/')
      let baseConfig = require('.' + entry)
      pathList.splice(-1)
      let pathname = pathList.slice(-1)[0]
      let chunks
      if (process.env.NODE_ENV === 'production') {
        chunks = ['chunk-vendors', 'chunk-common', 'vendor-' + pathname, pathname]
      } else {
        chunks = ['chunk-vendors', 'chunk-common', 'vendor-' + pathname, 'mock', pathname]
      }
      entries[pathname] = Object.assign({
        entry: pathList.join('/') + '/' + pathname + '.js',
        template: 'public/' + pathname + '.html',
        filename: pathname + '/' + pathname + '.html',
        chunks: chunks
      }, baseConfig)
    })
    return entries
  },
  getMultiEntries: function () {
    let self = this
    let pages = self.getMultiFiles('./src/' + buildConfig.moduleName + '/**/page.config.json')
    let chunks = Object.keys(pages)
    let chunksPlugin = {}
    let devRewriteUrl = []
    chunks.forEach(function (item, index) {
      let newName = 'vendor-' + item
      chunksPlugin[item] = {
        name: newName,
        test: (module) => {
          switch (item) {
            case 'manage':
              return /codemirror|mavon-editor|quill/.test(module.context)
            case 'portal':
              return /echarts/.test(module.context)
          }
        },
        chunks: 'initial',
        minChunks: 1,
        priority: index
      }
      devRewriteUrl.push(
        { from: new RegExp('^/' + item + '/?.*'), to: path.posix.join('/', item + '/' + item + '.html') }
      )
    })
    return {
      pages: pages,
      plugins: chunksPlugin,
      devRewriteUrl: devRewriteUrl
    }
  }
}
