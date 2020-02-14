// 게시물 정보와 관련된 Vuex Store Module
import axios from 'axios'

export default {
    namespaced: true,
    state: {
        postList: [],
        myPosts: null,
        userPosts: null,
        catPosts: null,
    },
    mutations: { // (state, rootState?)
        changePostList(state, payload, rootState) {
            state.postList = payload;
        },
        changeMyPosts(state, payload, rootState) {
            state.myPosts = payload;
        },
        changeUserPosts(state, payload, rootState) {
            state.userPosts = payload;
        },
        changeCatPosts(state, payload, rootState) {
            state.catPosts = payload;
        },
        clearUserPosts(state, payload, rootState) {
            state.userPosts = null;
        },
        clearCatPosts(state, payload, rootState) {
            state.catPosts = null;
        },
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
        },
        getMyPosts({ dispatch, commit, getters, rootGetters }, data) {
            axios
                .get(`${rootGetters.getServer}/api/post/searchPostUser?User_no=${data}`)
                .then(res => {
                    // handle success
                    commit('changeMyPosts', res.data.data);
                })
                .catch(err => {
                    // handle error
                })
                .then(() => {
                    // always executed
                    // console.log(vm.man);
                });
        },
        getUserPosts({ dispatch, commit, getters, rootGetters }, data) {
            console.log('getUserPosts');
            axios
                .get(`${rootGetters.getServer}/api/post/searchPostUser?User_no=${data}`)
                .then(res => {
                    // handle success
                    commit('changeUserPosts', res.data.data);
                })
                .catch(err => {
                    // handle error
                })
                .then(() => {
                    // always executed
                    // console.log(vm.man);
                });
        },
        async getCatPosts({ dispatch, commit, getters, rootGetters }, data) {
            console.log('getCatPosts');
            axios
                .get(`${rootGetters.getServer}/api/post/searchPostCat?Cat_no=${data}`)
                .then(res => {
                    // handle success
                    commit('changeCatPosts', res.data.data);
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
        postList: state => {
            return state.postList;
        },
        myPosts: state => {
            return state.myPosts;
        },
        userPosts: state => {
            return state.userPosts;
        },
        catPosts: state => {
            return state.catPosts;
        },
    }
};