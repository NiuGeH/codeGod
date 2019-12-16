<template>
  <div class="login">
    <div class="bg">
      <!-- 登录 -->
      <div class="view-login"
           v-show="fix">
        <p class="view-login-top">
          <img src="/static/image/logo.png"
               alt="">
          <span>后台管理系统</span>
        </p>
        <img src="/static/image/login-top.png"
             alt="">
        <div class="view-login-box">
          <p class="view-login-box-p">
            <img src="/static/image/phone.png"
                 alt="">
          </p>
          <div class="view-login-box-right">
            <el-input v-model="uid"
                      placeholder="请输入账号"></el-input>
          </div>

        </div>
        <div class="view-login-box">
          <p class="view-login-box-p">
            <img src="/static/image/iconb.png"
                 alt="">
          </p>
          <div class="view-login-box-right">
            <el-input v-model="pwd"
                      :type="type"
                      placeholder="请输入密码"></el-input>
            <img src="/static/image/iconc.png"
                 alt=""
                 v-show="isshow"
                 @click="show()">
            <img src="/static/image/icond.png"
                 alt=""
                 v-show="!isshow"
                 @click="shownot()">
          </div>

        </div>
        <!-- 记住我 -->
        <div class="controls">
          <el-checkbox v-model="checked"
                       label>记住我</el-checkbox>
          <!-- <el-button type="text" @click="register">一键注册</el-button> -->
          <el-button type="text"
                     @click="change()">修改密码</el-button>
        </div>
        <p @click="login()"
           class="login-btn">登录</p>
      </div>
      <!-- 修改密码 -->
      <div class="view-login view-fix"
           v-show="!fix">
        <p class="view-login-top">
          <img src="/static/image/logo.png"
               alt="">
          <span>后台管理系统</span>
        </p>
        <img src="/static/image/loginb.png"
             alt="">
        <div class="view-login-box">
          <p class="view-login-box-p">
            <img src="/static/image/phone.png"
                 alt="">
          </p>
          <div class="view-login-box-right">
            <el-input v-model="olduid"
                      placeholder="请输入账号"></el-input>
          </div>

        </div>
        <div class="view-login-box">
          <p class="view-login-box-p">
            <img src="/static/image/icone.png"
                 alt="">
          </p>
          <div class="view-login-box-right">
            <el-input v-model="oldpwd"
                      :type="type"
                      placeholder="请输入原密码"></el-input>
            <img src="/static/image/iconc.png"
                 alt=""
                 v-show="isshow"
                 @click="show()">
            <img src="/static/image/icond.png"
                 alt=""
                 v-show="!isshow"
                 @click="shownot()">
          </div>
        </div>
        <div class="view-login-box">
          <p class="view-login-box-p">
            <img src="/static/image/iconb.png"
                 alt="">
          </p>
          <div class="view-login-box-right">
            <el-input v-model="newpwd"
                      :type="type"
                      placeholder="请输入新密码"></el-input>
            <img src="/static/image/iconc.png"
                 alt=""
                 v-show="isshow"
                 @click="show()">
            <img src="/static/image/icond.png"
                 alt=""
                 v-show="!isshow"
                 @click="shownot()">
          </div>
        </div>
        <p @click="sure()"
           class="login-btn">确定修改</p>
      </div>
    </div>
  </div>
</template>
<script>
import { mapMutations, mapState } from "vuex";

export default {
  data () {
    return {
      fix: true,
      uid: "",
      pwd: "",
      olduid: "",
      oldpwd: "",
      newpwd: "",
      checked: true,
      hasCookie: false,
      isshow: true,
      type: "password",
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
  },
  methods: {
    ...mapMutations(["saveToken", "saveUserInfo", "toggleThemeStore"]),
    change () {
      this.fix = false;
    },
    sure () {
      console.log('111')
      if (this.olduid && this.oldpwd && this.newpwd) {
        //let data = { id: '2', newPwd: '111', password: '123' };
        let data = { id: '1', newPwd: this.newpwd, password: this.oldpwd };
        let post = this.Encrypt(JSON.stringify(data));
        console.log(JSON.stringify(data), post);
        this.api.modifyPwd(post, res => {
          console.log(res)
          if (res.code == 200) {
            this.fix = true;
            let a = this.Decrypt(res.data);
            console.log(a)
            this.$message.success("修改成功");
          } else {
            alert(res.message);
          }
        });
      } else {
        this.$message.warning("账号和密码不能为空");
      }
    },
    show () {
      this.isshow = false;
      this.type = 'text'
    },
    shownot () {
      this.isshow = true;
      this.type = 'password'
    },
    /**
     * [signIn 登录]
     * @return {[type]} [description]
     */
    login () {
      if (this.uid && this.pwd) {
        let data = { username: this.uid, password: this.pwd };
        let post = this.Encrypt(JSON.stringify(data));
        //document.cookie = 'JSESSIONID' + "=" + 1;
        let v = $cookies.keys();
        //alert(document.cookie)
        console.log(post,v);
        this.api.login(post, res => {
          // 删除所有cookie
          //this.foreach();
          console.log(res)
          if (res.code == 200) {
            let a = this.Decrypt(res.data);
            console.log(a)
            this.$message.success("登录成功");
            // 保存 token
            //this.saveToken("admintoken");
            //this.saveUserInfo({ uid: "admin", pwd: "admin", theme: "default-theme" });
            // 跳转到首页
            //this.$router.push({ path: this.$route.query.redirect || "/view/Home" });
          } else {
            this.$message.warning(res.message);
          }
        });
      } else {
        this.$message.warning("账号和密码不能为空");
      }
    },
    // js 遍历所有Cookie
    foreach () {
      var strCookie = document.cookie;
      var arrCookie = strCookie.split("; "); // 将多cookie切割为多个名/值对
      for (var i = 0; i < arrCookie.length; i++) { // 遍历cookie数组，处理每个cookie对
        var arr = arrCookie[i].split("=");
        if (arr.length > 0)
          this.DelCookie(arr[0]);
      }
    },
    GetCooki (offset) {
      var endstr = document.cookie.indexOf(";", offset);
      if (endstr == -1)
        endstr = document.cookie.length;
      return decodeURIComponent(document.cookie.substring(offset, endstr));
    },

    DelCookie (name) {
      var exp = new Date();
      exp.setTime(exp.getTime() - 1);
      var cval = this.GetCookie(name);
      document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
    },

    GetCookie (name) {
      var arg = name + "=";
      var alen = arg.length;
      var clen = document.cookie.length;
      var i = 0;
      while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg)
          return this.GetCooki(j);
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0) break;
      }
      return null;
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
<style lang="scss" scoped>
.login {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
}

.bg {
  flex: 1;
  background: url(/static/image/login.png) no-repeat;
  background-size: 100% 100%;
}

.view-login {
  bottom: 0;
  left: 0;
  margin: auto;
  position: absolute;
  top: 0;
  right: 0;
  width: 5.7rem;
  height: 4.5rem;
  background: rgba(255, 255, 255, 0.7);
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  &-top {
    width: 100%;
    height: 1rem;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 0.2rem;
    font-weight: 600;
    letter-spacing: 2px;
    color: #2a638c;
    img {
      width: 2rem;
      height: 0.514rem;
      margin-right: 0.1rem;
    }
    span {
      margin-left: 0.1rem;
    }
  }
  &-box {
    box-sizing: content-box;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: start;
    -ms-flex-pack: start;
    justify-content: flex-start;
    width: 4.6rem;
    height: 0.56rem;
    margin: 0 auto;
    border: 1px solid #949494;
    margin-top: 0.15rem;
    border-radius: 0.05rem;
    overflow: hidden;
    &-p {
      border-right: 1px solid #949494;
      justify-content: center;
      display: -webkit-box;
      display: -ms-flexbox;
      display: flex;
      width: 0.7rem;
      height: 100%;
      -webkit-box-align: center;
      -ms-flex-align: center;
      align-items: center;
      img {
        width: 0.38rem;
        height: 0.38rem;
      }
    }
    &-right {
      width: 3.9rem;
      display: flex;
      align-items: center;
      img {
        width: 0.38rem;
        height: auto;
        margin: 0 0.1rem;
        cursor: pointer;
      }
    }
  }

  .controls {
    width: 4.6rem;
    margin: 0 auto;
    align-items: center;
    display: flex;
    margin-top: 0.05rem;

    > label {
      flex: 1;
    }
  }
  .login-btn {
    background: #2384bc;
    color: #fff;
    width: 4.6rem;
    margin: 0.15rem auto 0;
    font-size: 0.24rem;
    text-align: center;
    height: 0.6rem;
    line-height: 0.6rem;
    border-radius: 0.05rem;
    cursor: pointer;
  }
}
.view-fix {
  height: 4.8rem;
  .login-btn {
    margin-top: 0.25rem;
  }
}
</style>