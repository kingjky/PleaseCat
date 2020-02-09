import Vue from 'vue'
import Vuex from 'vuex'
// import axios from 'axios'
import moduleCat from './modules/moduleCat'
import moduleUser from './modules/moduleUser'
import modulePost from './modules/modulePost'

Vue.use(Vuex)

// const moduleCat = 

export default new Vuex.Store({
    modules: {
        storeCat: moduleCat,
        storeUser: moduleUser,
        storePost: modulePost,
    },
    state: {
        // selectedCatNo: '0',
        server: 'http://192.168.219.100:8080',
        token: '',
    },
    getters: {
        // selectedCatNo: state => { return state.selectedCatNo },
        getServer: state => { return state.server },
    },
    mutations: {
        // selectCat(state, payload) {
        //   state.selectedCatNo = payload.n;
        // }
    },
    actions: {
        // selectCat({ commit }, payload) {
        //   commit('selectCat',{
        //     n: payload.n,
        //   })
        // }
    },
})


/*
  Vue 에서 Module store 접근 시
  import { createNamespacedHelpers } from 'vuex'

  const { mapState, mapActions } = createNamespacedHelpers('some/nested/module')

  export default {
    computed: {
      // `some/nested/module`에서 찾음
      ...mapState({
        a: state => state.a,
        b: state => state.b
      })
    },
    methods: {
      // `some/nested/module`에서 찾음
      ...mapActions([
        'foo',
        'bar'
      ])
    }
  }
*/