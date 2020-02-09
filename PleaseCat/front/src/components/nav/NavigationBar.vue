<template>
  <div id="navBar">
      <span id="greeting">{{ this.$store.getters.getLoginId }} 님 환영합니다.</span>      
  </div>
</template>

<script>

export default {
    name: "NavigationBar",
}
</script>

<style lang="scss" scoped>
#navBar{
  position:fixed;
  top: 0;
  left:0;
  width: 100%;
  height: 50px;
  background-color: pink;
  z-index:100;
  text-align: right;
}
</style>