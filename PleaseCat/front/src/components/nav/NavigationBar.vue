<template>
  <div id="navBar">
      <span id="greeting" v-if="getIsLogin">{{ this.$store.getters.getLoginInfo.user_id }} 님 환영합니다.</span>
      <span>
            <button v-if="!getIsLogin"><router-link to="/login">로그인</router-link></button>
            <button v-if="getIsLogin" v-on:click="logout">로그아웃</button>
      </span>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapGetters } from "vuex";

export default {
    name: "NavigationBar",
    data: () => {
      return {

      }
    },
    
    computed: {
      ...mapGetters([
        'getIsLogin', 'getUserLoc'
      ]),
    },
    methods: {
      logout() {
        // console.log('로그아웃');
        this.$store.dispatch('logout');
      },
    }
}
</script>

<style lang="scss" scoped>
#navBar{
  position:fixed;
  top: 0;
  left:0;
  width: 100%;
  height: 50px;
  background: orange;
  z-index:100;
  text-align: right;
  #greeting {
    margin-right: 20px;
  }
}
</style>