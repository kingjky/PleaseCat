<template>
  <v-app>
    <v-content>
      <!-- <HelloWorld/> -->
      <NavigationBar/>
      <TabBar/>
      <router-view></router-view>
    </v-content>
  </v-app>
</template>
<script>
// import HelloWorld from './components/HelloWorld'
import NavigationBar from './components/nav/NavigationBar'
import TabBar from './components/tabbar/TabBar'

export default {
  name: 'App',
  components: {
    // HelloWorld
    NavigationBar,
    TabBar
  },
  data: () => ({
    //
  })
}
</script>
<style lang="scss" scoped>
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);

.text{
    font-family: "Noto Sans KR", sans-serif;
}
</style>