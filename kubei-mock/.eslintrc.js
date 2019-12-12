module.exports = {
    env: {
        browser: true,
        es6: true
    },
    extends: [
        'eslint:recommended',
        'plugin:vue/essential'
    ],
    globals: {
        Atomics: 'readonly',
        SharedArrayBuffer: 'readonly',
        dict: true,
        AMap: true,
        BMap: true,
        apiConfig: true
    },
    parserOptions: {
        ecmaVersion: 2018,
        sourceType: 'module'
    },
    plugins: [
        'vue'
    ],
    rules: {
    }
};
