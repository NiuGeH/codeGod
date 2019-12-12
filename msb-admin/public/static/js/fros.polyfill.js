// 用于处理一些浏览器兼容问题
(function () {
  // 对于ie9一下的浏览器进行提示
  // 获取设备信息
  function getDeviceInfo (query) {
    var userAgent = navigator.userAgent
    if (query === 'browser') {
      var isOpera = userAgent.indexOf('Opera') > -1
      var isIE = userAgent.indexOf('compatible') > -1 && userAgent.indexOf('MSIE') > -1 && !isOpera
      var isFF = userAgent.indexOf('Firefox') > -1
      var isSafari = userAgent.indexOf('Safari') > -1
      var isChrome = userAgent.indexOf('Chrome') > -1
      var isEdge = userAgent.indexOf('Edge') > -1
      if (isIE) {
        var reIE = new RegExp('MSIE (\\d+\\.\\d+);')
        reIE.test(userAgent)
        var fIEVersion = parseFloat(RegExp['$1'])
        return 'IE' + fIEVersion
      } else if (isFF) {
        return 'Firefox'
      } else if (isOpera) {
        return 'Opera'
      } else if (isChrome) {
        return 'Chrome'
      } else if (isEdge) {
        return 'Edge'
      } else if (isSafari) {
        return 'Safari'
      } else {
        return 'Unknown'
      }
    } else if (query === 'device') {
      var isAndroid = userAgent.indexOf('Android') > -1
      var isIphone = userAgent.indexOf('iPhone') > -1
      var isIpad = userAgent.indexOf('iPad') > -1
      if (isAndroid) {
        return 'Android'
      } else if (isIphone) {
        return 'Iphone'
      } else if (isIpad) {
        return 'Ipad'
      } else {
        return 'Unknown'
      }
    } else if (query === 'platform') {
      if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
        return 'MOBILE'
      } else {
        return 'PC'
      }
    }
  }
  // 获取浏览器版本
  var browserVersion = getDeviceInfo('browser')
  if (/IE/g.test(browserVersion)) {
    // 获取IE版本，ie10以下的进行弹窗提示，因为ie9不支持flex布局，所以这里不考虑兼容ie9，如果有需要兼容ie9，请使用非flex布局，然后再调整这里的版本控制
    var versionNum = parseInt(browserVersion.replace('IE', ''))
    if (versionNum < 10) {
      // 隐藏布局
      var mainCtx = document.getElementsByTagName('body')[0]
      mainCtx.style.display = 'none'
      // 版本弹窗提示
      var alertDomStr = '<div style="width: 100%;height: 100%;position: fixed;background: rgba(0,0,0,0.4);top: 0;left: 0;z-index: 9999;">' +
        '<div style="padding: 32px;width: 500px;height: 150px;background: #f5f5f5;position: absolute;top: 50%;margin-top: -107px;left: 50%;margin-left: -282px;">' +
        '<div style="color: #000;">Hi！您当前的浏览器版本过低，可能存在安全风险，建议升级浏览器！</div>' +
        '<div style="color: #b49114;font-size: 12px;margin-top: 6px;">若您使用的操作系统版本较低，如xp，升级系统或使用360浏览器</div>' +
        '<div style="vertical-align: middle;margin-top: 12px;">' +
        '<a target="_blank" href="https://www.google.cn/intl/zh-CN/chrome/" style="margin-right: 10px;display: inline-block;height: 32px;line-height: 32px;padding: 0 16px;border: 1px solid #2b579a;color: #2b579a;text-decoration: none;">谷歌 Chrome</a>' +
        '<a target="_blank" href="https://www.mozilla.org/zh-CN/firefox/new/" style="margin-right: 10px;display: inline-block;height: 32px;line-height: 32px;padding: 0 16px;border: 1px solid #2b579a;color: #2b579a;text-decoration: none;">火狐 Firefox</a>' +
        '<a target="_blank" href="http://browser.360.cn/" style="display: inline-block;height: 32px;line-height: 32px;padding: 0 16px;border: 1px solid #2b579a;color: #2b579a;text-decoration: none;">360安全浏览器</a>' +
        '</div>' +
        '</div>' +
        '</div>'
      var temParent = document.createElement('div')
      temParent.innerHTML = alertDomStr
      var alertDoms = temParent.childNodes
      document.getElementsByTagName('html')[0].appendChild(alertDoms[0])
    }
  } else {
    // TODO 未来根据实际情况对不同浏览器进行判断限制
  }
  // 兼容window.btoa 和 window.atob
  var chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/='
  window.btoa = function (input) {
    var str = String(input)
    for (
      // initialize result and counter
      var block, charCode, idx = 0, map = chars, output = '';
      // if the next str index does not exist:
      // change the mapping table to "="
      // check if d has no fractional digits
      str.charAt(idx | 0) || (map = '=', idx % 1);
      // "8 - idx % 1 * 8" generates the sequence 2, 4, 6, 8
      output += map.charAt(63 & block >> 8 - idx % 1 * 8)
    ) {
      charCode = str.charCodeAt(idx += 3 / 4)
      if (charCode > 0xFF) {
        throw new Error("'btoa' failed: The string to be encoded contains characters outside of the Latin1 range.")
      }
      block = block << 8 | charCode
    }
    return output
  }
  window.atob = function (input) {
    var str = (String(input)).replace(/[=]+$/, '')
    // #31: ExtendScript bad parse of /=
    if (str.length % 4 === 1) {
      throw new Error("'atob' failed: The string to be decoded is not correctly encoded.")
    }
    for (
      // initialize result and counters
      var bc = 0, bs, buffer, idx = 0, output = '';
      // get next character
      buffer = str.charAt(idx++); // eslint-disable-line no-cond-assign
      // character found in table? initialize bit storage and add its ascii value;
      ~buffer && (bs = bc % 4 ? bs * 64 + buffer : buffer,
      // and if not first of each 4 characters,
      // convert the first 8 bits to one ascii character
      bc++ % 4) ? output += String.fromCharCode(255 & bs >> (-2 * bc & 6)) : 0
    ) {
      // try to find character in table (0-63, not found => -1)
      buffer = chars.indexOf(buffer)
    }
    return output
  }
})()
