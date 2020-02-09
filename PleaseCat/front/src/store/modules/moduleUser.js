// 유저 정보와 관련된 Vuex Store Module
import axios from 'axios'

export default {
    namespaced: true,
    state: {
        userList: '',
    },
    mutations: { // (state, payload, rootState?)
        changeUserList(state, payload, rootState) {
            state.userList = payload;
        }
    },
    actions: { // ({ dispatch, commit, getters, rootGetters }, data, ...) 'context.'는 생략
        getUserList({ dispatch, commit, getters, rootGetters }) {
            axios
                .get(`${rootGetters.getServer}/api/user/searchAll`)
                .then(res => {
                    // handle success
                    commit('changeUserList', res.data.data);
                })
                .catch(err => {
                    // handle error
                })
                .then(() => {
                    // always executed
                    // console.log(vm.man);
                });
        }
    },
    getters: { // (state, getters, rootState, rootGetters)
        userList: state => {
            return state.userList;
        },
    }
};