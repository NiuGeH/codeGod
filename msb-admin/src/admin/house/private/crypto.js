import Vue from 'vue';
import CryptoJS from 'crypto-js/crypto-js'
let crypto = {
  Encrypt: (content) => {
    var sKey = CryptoJS.enc.Utf8.parse('MI97JSJfjwIDAQAB');
    var sContent = CryptoJS.enc.Utf8.parse(content);
    var encrypted = CryptoJS.AES.encrypt(sContent, sKey, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
    return encrypted.toString();
  },
  Decrypt: (content) => {
    var sKey = CryptoJS.enc.Utf8.parse('MI97JSJfjwIDAQAB');
    var decrypt = CryptoJS.AES.decrypt(content, sKey, { mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7 });
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
  },
}

Vue.prototype.Encrypt = crypto.Encrypt;
Vue.prototype.Decrypt = crypto.Decrypt;