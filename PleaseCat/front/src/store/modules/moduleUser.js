// 유저 정보와 관련된 Vuex Store Module
import axios from 'axios'

export default {
    namespaced: true,
    modules:{
        storeRank: {
            namespaced: true,
            state: {
                rankList: null,
            },
            getters: {
                rankList: (state) => {
                    return state.rankList;
                },
            },
            mutations: {
                changeRankList: (state, payload) => {
                    state.rankList = payload.data;
                },
            },
            actions: {
                getRankList({ dispatch, commit, getters, rootGetters }, data){
                    axios
                    .post(`${rootGetters.getServer}/api/Ranking/searchRankingCat?cat_no=${data.cat_no}`)
                    // .get(`${rootGetters.getServer}/api/cat/searchCat?Cat_no=${data.cat_no}`)
                    .then(res => {
                        // handle success
                        commit('changeRankList', res.data);
                    })
                    .catch(err => {
                        // handle error
                    })
                    .then(() => {
                        // always executed
                    });
                }
            },
        },
    },
    state: {
        userList: '',
        selectedUser: null,
    },
    mutations: { // (state, payload, rootState?)
        changeUserList(state, payload, rootState) {
            state.userList = payload;
        },
        changeSelectedUser(state, payload, rootState) {
            state.selectedUser = payload;
        },
        clearSelectedUser(state, payload) {
            state.selectedUser = null;
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
        },
        getSelectedUser({ dispatch, commit, getters, rootGetters },data){
            axios
                .get(`${rootGetters.getServer}/api/user/searchUserNo?user_no=${data}`)
                .then(res => {
                    // handle success
                    var userInfo = res.data.data;
                    commit('changeSelectedUser', userInfo);
                })
                .catch(err => {
                    // handle error
                })
                .then(() => {
                    // always executed
                    // console.log(vm.man);
                });
        },
    },
    getters: { // (state, getters, rootState, rootGetters)
        userList: state => {
            return state.userList;
        },
        selectedUser: state => {
            return state.selectedUser;
        },
    }
};