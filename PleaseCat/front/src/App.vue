<template>
  <v-app>
    <v-content>
      <!-- <HelloWorld/> -->
      <NavigationBar/>
      <router-view></router-view>
      <TabBar/>
    </v-content>
  </v-app>
</template>
<script>
// import HelloWorld from './components/HelloWorld'
import NavigationBar from '@/components/nav/NavigationBar'
import TabBar from '@/components/tabbar/TabBar'
import { mapActions, mapMutations, mapGetters } from "vuex";

export default {
  name: 'App',
  data: () => ({
    //
  }),
  created() {
    this.getCatList();
    // this.$store.dispatch('storeCat/getCatList');
    this.getUserList();
    this.getPostList();
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
      ...mapActions('storeUser',[
          'getUserList',
      ]),
      ...mapActions('storePost',[
          'getPostList',
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