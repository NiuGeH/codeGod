import VueI18n from 'vue-i18n'
import en from '../js/locale/lang/en'
import zhCN from '../js/locale/lang/zh-CN'
import ja from '../js/locale/lang/ja'
import elZhCN from 'element-ui/lib/locale/lang/zh-CN'
import elEn from 'element-ui/lib/locale/lang/en'
import elJa from 'element-ui/lib/locale/lang/ja'

import Vue from 'vue'

Vue.use(VueI18n)

let lang = localStorage.getItem('lang') ? localStorage.getItem('lang') : 'zh'

// 国际化options
const options = {
  locale: lang,
  messages: {
    'en': {
      ...en,
      ...elEn,
      name: 'En',
      text: '英文'
    },
    'zh': {
      ...zhCN,
      ...elZhCN,
      name: '中',
      text: '中文'
    },
    'ja': {
      ...ja,
      ...elJa,
      name: 'あ',
      text: '日文'
    }
  }
}

// 国际化
const i18n = new VueI18n(options)

export default {
  options,
  i18n
}
