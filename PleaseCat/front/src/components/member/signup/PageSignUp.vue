<template>
  <div class="signup text">
    <div class="title-signup">
      Sign Up
    </div>
    <div class="input-wrap">
      <!-- 입력받을 정보: name, email, id, pw, image, desc -->
      <div class="input-row">
        <label for="id">ID</label>
        <input type="text" id="id" v-model="user_id" placeholder="아이디를 입력하세요" />
      </div>
      <div class="input-row">
        <label for="password">PW</label>
        <input type="password" id="password" v-model="user_pw" placeholder="비밀번호를 입력하세요" />
      </div>
      <div class="input-row">
        <label for="email">Email</label>
        <input type="email" id="email" v-model="user_email" placeholder="이메일을 입력하세요" />
      </div>
      <div class="input-row">
        <label for="name">Name</label>
        <input type="name" id="name" v-model="user_name" placeholder="이름을 입력하세요" />
      </div>
    </div>

    <div class="btn-wrap">
      <button class="btn-signup" v-on:click="signup">Create Account</button>
    </div>
  </div>
</template>

<script>
import UserApi from "../../../apis/UserApi";
export default {
  data() {
    return {
      user_id: "",
      user_pw: "",
      user_email: "",
      user_name: "",

      error: {
        user_id: false,
        user_pw: false,
        user_email: false,
        user_name: false
      }
    };
  },

  methods: {
    signup() {
      let { user_id, user_pw, user_email, user_name } = this;
      let data = {
        user_id,
        user_pw,
        user_email,
        user_name
      };

      UserApi.requestSignup(
        data,
        res => {
          if (res.status == 200) {
            if ((res.data.state = "ok")) {
              this.$router.push("/");
            } else {
              // 실패
            }
          }
        },
        error => {
          console.log("서버 에러");
        }
      );
    }
  }
};
</script>

<style lang="scss" scoped>
.signup {
  width: 350px;
  margin: 0 auto;
  margin-top: 10px;
  margin-bottom: 60px;
  // padding-top: 100px;
  padding-top: 10px;
  padding-bottom: 125px;
}
.signup .title-signup{
  margin-top: 60px;
  margin-bottom: 40px;
  font-weight: bold;
  font-size: 42px;
}
.signup label {
  display: inline-block;
  max-width: 100%;
  margin-bottom: 5px;
  font-weight: 700;
}
.signup input {
  width: 82%;
  float: right;
  background: 0 0; //? 이게 무슨 속성일까!
  border: 1px solid rgb(250, 206, 231);
  border-radius: 6px;
  box-sizing: border-box;
  color: rgb(248, 44, 163);
  font-size: 14px;
  padding: 7px 8px 7px;
}
.input-wrap .input-row {
  margin-bottom: 25px;
}

.btn-wrap {
  padding-top: 5px;
  margin-bottom: 25px;
  color: rgb(248, 51, 160);
}
.btn-wrap .btn-signup {
  background: rgb(248, 51, 160);
  border-color: rgb(248, 51, 160);
  color: #fff;
  border-radius: 6px;
  border-style: solid;
  border-width: 1px;
  font-size: 14px;
  font-weight: 600;
  line-height: 28px;
  outline: none;
  width: 40%;
  padding: 0%;
  float: right;
  cursor: pointer;
}
</style>