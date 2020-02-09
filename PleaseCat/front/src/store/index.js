import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import moduleCat from './modules/moduleCat'
import moduleUser from './modules/moduleUser'
import modulePost from './modules/modulePost'
import router from '@/router/index'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        storeCat: moduleCat,
        storeUser: moduleUser,
        storePost: modulePost,
    },
    state: {
        server: 'http://192.168.219.100:8080',
        token: '',
        loginId: '',
    },
    getters: {
        getServer: state => { return state.server },
        getToken: state => { return state.token },
        getLoginId: state => { return state.loginId },
    },
    mutations: {
        changeToken(state, payload, rootState) {
            state.token = payload.data;
        },
        changeLoginId(state, payload, rootState) {
            state.loginId = payload.data;
        }
    },
    actions: {
        postLogin({ state, dispatch, commit, getters, rootGetters }, data) {
            axios
                .post(`${getters.getServer}/api/user/login`, data)
                .then(res => {
                    // console.log(res.data);
                    commit('changeToken', res.data);
                    // handle success
                    // 토큰을 헤더에 포함시켜서 유저 정보를 요청
                    let config = {
                        headers: {
                            "token": getters.getToken,
                        }
                    }
                    axios
                        .get(`${getters.getServer}/api/user/checkToken`, config)
                        .then(response => {
                            console.log(response);
                            commit('changeLoginId', response.data);
                        })
                        .catch(error => {
                            console.error(error);
                        })

                    // router.push("/");
                })
                .catch(err => {
                    // handle error
                    console.error(err);
                })
                .then(() => {
                    // always executed
                });
        },
        postSignUp({ state, dispatch, commit, getters, rootGetters }, data) {
            axios
                .post(`${getters.getServer}/api/user/insert`, data)
                .then((res) => {
                    console.log(res);
                })
                .catch((err) => {
                    console.error(err);
                });
        },
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