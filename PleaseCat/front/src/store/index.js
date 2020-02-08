import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
// import moduleCat from './modules/moduleCat.js'
import moduleUser from './modules/moduleUser.js'
import modulePost from './modules/modulePost.js'

Vue.use(Vuex)

const moduleCat = {
    namespaced: true,
    state: {
        cats: {
            name: 'asd',
            age: '2',
        }
    },
    mutations: { // (state, rootState?)
        changeCatList(state, payload, rootState) {
            console.log('Now I am in changeCatList in mutations in storeCat');
            console.log('And this is payload from getCatList in actions')
            console.log(payload)
            state.cats = payload;
        },
    },
    actions: { // ({ dispatch, commit, getters, rootGetters }) : context. 생략
        getCatList({ state, dispatch, commit, getters, rootGetters }) {
            console.log('I am in getCatList in actions in storeCat');
            console.log('This is getServer in rootGetters');
            console.log(rootGetters.getServer);
            axios
                .get(`${rootGetters.getServer}/api/cat/searchAll`)
                .then(res => {
                    // handle success
                    console.log('This is catlist now');
                    console.log(state.cats);

                    console.log('And This is res.data.data now');
                    console.log(res.data.data);
                    commit('changeCatList', res.data.data);
                })
                .catch(err => {
                    // handle error
                })
                .then(() => {
                    // always executed
                });
        },
    },
    getters: { // (state, getters, rootState, rootGetters)
        catList: state => {
            return state.cats
        }
    }
}

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