<template>
  <div class="login">
    <div class="nav">头部</div>
    <div class="bg">
      <div class="view-login">
        <h1>KUB</h1>
        <el-input v-model="uid"
                  placeholder="账户"></el-input>
        <el-input v-model="pwd"
                  type="password"
                  placeholder="密码"></el-input>
        <div class="controls">
          <el-checkbox v-model="checked"
                       label>记住我</el-checkbox>
          <el-button type="text"
                     @click="register">一键注册</el-button>
          <el-button type="text"
                     @click="login">登录</el-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapMutations, mapState } from "vuex";

export default {
  data () {
    return {
      uid: "",
      pwd: "",
      checked: false,
      hasCookie: false
    };
  },
  computed: {
    ...mapState(["token"])
  },
  mounted () {
    // 如果登录了就跳到首页
    if (this.token) {
      const r = JSON.parse(localStorage.getItem('router'));
      this.$router.push({ path: r.url });
    }

    this.remenberMe();
    this.getpassword ();
  },
  methods: {
    ...mapMutations(["saveToken", "saveUserInfo", "toggleThemeStore"]),
    //加密测试
    getpassword () {
      this.api.getpassword({}, res => {
        console.log(res)
        if (res.code == 200) {
          console.log(res.data)
          let a = this.Decrypt(res.data);
          console.log(a)
        } else {
          //alert(res.message);
        }
      });
    },
    /**
     * [register 注册账号]
     * @return {[type]} [description]
     */
    register () {
      if (this.uid && this.pwd) {
        // this.http.post(`/user/reg`, { uid: this.uid, pwd: this.$md5(this.pwd) })
        //     .then(res => {
        //         res.data.code == 200 ? this.login() : this.$message.error(res.data.msg);
        //     });
      } else {
        this.$message.warning("用户名和密码不能为空");
      }
    },

    /**
     * [signIn 登录]
     * @return {[type]} [description]
     */
    login () {
      // this.$axios.get('https://api.coindesk.com/v1/bpi/currentprice.json').then(response => {
      //         console.log(response);
      //     }).catch(function (error) {
      //       console.log(error);
      //     })
      //
      //
      // ==========================
      // this.http.post("/user/login", { uid: this.uid, pwd: this.hasCookie ? this.pwd : this.$md5(this.pwd) }).then(res => {

      //     const result = res.data,
      //         redirect = this.$route.query.redirect;

      //     result.code == 200 ? (

      //         //保存 cookie
      //         this.setCookie(result.data[0]),

      //         this.$message.success(result.msg),

      //         // 保存 token
      //         this.saveToken(result.data[0].pwd),

      //         this.saveUserInfo(result.data[0]),

      //         // 跳转到首页
      //         this.$router.push({ path: redirect || '/module/Home' })

      //     ) : this.$message.error(result.msg);
      // });

      //保存 cookie
      this.setCookie({ uid: "admin", pwd: "admin", theme: "default-theme" });

      this.$message.success("登录成功");

      // 保存 token
      this.saveToken("admintoken");

      this.saveUserInfo({ uid: "admin", pwd: "admin", theme: "default-theme" });

      // 跳转到首页
      this.$router.push({ path: this.$route.query.redirect || "/view/Home" });
    },

    /**
     * [setCookie 记住我]
     * @param {[type]} user [description]
     */
    setCookie (u) {
      this.checked ?
        (document.cookie = `user=${u.uid}/${u.pwd}/${u.theme};max-age=${30 *
          24 *
          60 *
          60}`) :
        (document.cookie = `user=${u.uid}/${u.pwd}/${u.theme};max-age=0`);
    },

    /**
     * [remenberMe 记住我 ]
     * @return {Boolean} [description]
     */
    remenberMe () {
      let user = document.cookie.match(`(^| )user=([^;]*)(;|$)`);

      if (user) {
        user = user[2].split("/");

        this.uid = user[0];
        this.pwd = user[1]; // md5 后端自己匹配
        this.toggleThemeStore(user[2]); // 设置主题
        this.checked = true;
        this.hasCookie = true;
      }
    }
  }
};
</script>
<style scoped lang="scss">
.login {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
}

.bg {
  flex: 1;
  background: #000;
}

.view-login {
  bottom: 0;
  left: 40%;
  margin: auto;
  position: absolute;
  top: 30%;
  right: 0;
  width: 5rem;

  h1 {
    margin-bottom: 0.2rem;
  }

  .el-input {
    margin-bottom: 0.15rem;
  }

  .controls {
    align-items: center;
    display: flex;

    > label {
      flex: 1;
    }
  }
}
</style>