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
        server: 'http://localhost:8080',
        token: '',
        loginId: '',
        isLogin: false,
    },
    getters: {
        getServer: state => { return state.server },
        getToken: state => { return state.token },
        getLoginId: state => { return state.loginId },
        getIsLogin: state => { return state.isLogin },
    },
    mutations: {
        changeToken(state, payload) {
            state.token = payload.data;
        },
        changeLoginId(state, payload, rootState) {
            state.loginId = payload.data;
            state.isLogin = true;
        },
        changeLogout(state) {
            state.isLogin = false;
            state.loginId = '';
            state.token = '';
            localStorage.removeItem('savedToken');
        }
    },
    actions: {
        logout({ state, dispatch, commit, getters, rootGetters }){
            commit('changeLogout');
        },
        postLogin({ state, dispatch, commit, getters, rootGetters }, data) {
            axios
                .post(`${getters.getServer}/api/user/login`, data)
                .then(res => {
                    // handle success
                    // console.log(res.data);
                    let token = res.data.data;
                    localStorage.setItem('savedToken', token);
                    dispatch('checkToken');
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
        checkToken({ state, dispatch, commit, getters, rootGetters }) {
            // 토큰을 헤더에 포함시켜서 유저 정보를 요청
            let token = localStorage.getItem('savedToken');
            
            let config = {
                headers: {
                    "token": token,
                }
            }
            axios
                .get(`${getters.getServer}/api/user/checkToken`, config)
                .then(response => {
                    console.log(response);
                    console.log(response.data.data);
                    if(response.data.state === 'ok'){
                        commit('changeLoginId', response.data);
                        router.push("/");
                    } else {
                        dispatch('logout');
                    }
                })
                .catch(error => {
                    console.error(error);
                })
        }
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