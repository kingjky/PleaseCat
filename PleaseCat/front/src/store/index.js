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
        server: 'http://70.12.246.120:8080',
        token: '',
        loginInfo: '',
        isLogin: false,
    },
    getters: {
        getServer: state => { return state.server },
        getToken: state => { return state.token },
        getLoginInfo: state => { return state.loginInfo },
        getIsLogin: state => { return state.isLogin },
    },
    mutations: {
        changeToken(state, payload) {
            state.token = payload.data;
        },
        changeLoginId(state, payload, rootState) {
            state.loginInfo = payload;
            state.isLogin = true;
        },
        changeLogout(state) {
            state.isLogin = false;
            state.loginInfo = '';
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
                    router.push("/");
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
                    console.log(response.data);
                    var obj = eval("("+response.data.data+")");
                    console.log(obj);
                    if(response.data.state === 'ok'){
                        commit('changeLoginId', obj);
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