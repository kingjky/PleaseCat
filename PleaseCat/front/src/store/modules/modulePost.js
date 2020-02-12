// 게시물 정보와 관련된 Vuex Store Module
import axios from 'axios'

export default {
    namespaced: true,
    state: {
        postList: null,
    },
    mutations: { // (state, rootState?)
        changePostList(state, payload, rootState) {
            state.postList = payload;
        }
    },
    actions: { // ({ dispatch, commit, getters, rootGetters }) : context. 생략
        getPostList({ dispatch, commit, getters, rootGetters }) {
            axios
                .get(`${rootGetters.getServer}/api/post/searchAll`)
                .then(res => {
                    // handle success
                    commit('changePostList', res.data.data);
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
        postList: state => {
            return state.postList;
        },
    }
};