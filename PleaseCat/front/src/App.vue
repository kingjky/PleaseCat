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
import { mapActions, mapMutations, mapGetters } from "vuex";

export default {
  name: 'App',
  data: () => ({
    //
  }),
  created() {
    this.getCatList();
  },
  components: {
    // HelloWorld
    NavigationBar,
    TabBar
  },
  methods: {
      ...mapActions('storeCat',[
          'getCatList',
      ]),
  },
}
</script>
<style lang="scss" scoped>
@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);

* {
    font-family: "Noto Sans KR", sans-serif;
}
</style>