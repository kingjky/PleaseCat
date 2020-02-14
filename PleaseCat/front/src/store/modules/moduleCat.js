// 고양이 정보와 관련된 Vuex Store Module
import axios from 'axios'
import CatApi from "@/apis/CatApi"

export default {
    namespaced: true,
    state: {
        catList: [],
        selectedCat: null,
        nearCatList: [],
    },
    // mutations : 동기 처리 logic
    mutations: { // (state, rootState?)
        /*
            state.catList을 업데이트
            호출 위치 : storeCat/getCatList
        */
        changeCatList(state, payload, rootState) {
            state.catList = payload;
            // console.log('Finally End getCatList')
        },
        changeSelectedCat(state, payload, rootState) {
            state.selectedCat = payload;
        },
        changeNearCatList(state, payload, rootState) {
            state.nearCatList = payload;
        },
        clearSelectedCat(state, payload) {
            state.selectedCat = null;
        }
    },
    // actions : 비동기 처리 logic
    actions: { // ({ dispatch, commit, getters, rootGetters }, data) : context. 생략
        /*
            DB에서 모든 고양이 정보를 읽어옴 -> storeCat/changeCatList 호출
            호출 위치 : App.vue
        */
        getCatList({ state, dispatch, commit, getters, rootGetters }) {
            axios
                .get(`${rootGetters.getServer}/api/cat/searchAll`)
                .then(res => {
                    // handle success
                    commit('changeCatList', res.data.data);
                })
                .catch(err => {
                    // handle error
                })
                .then(() => {
                    // always executed
                });
        },
        getSelectedCat({ dispatch, commit, getters, rootGetters },data){
            axios
                .get(`${rootGetters.getServer}/api/cat/searchCat?Cat_no=${data}`)
                .then(res => {
                    // handle success
                    var catInfo = res.data.data;
                    commit('changeSelectedCat', catInfo);
                })
                .catch(err => {
                    // handle error
                })
                .then(() => {
                    // always executed
                    // console.log(vm.man);
                });
        },
        getNearCatList({ dispatch, commit, getters, rootGetters },data){
            axios
                .get(`${rootGetters.getServer}/api/cat/searchCat?Cat_no=${data}`)
                .then(res => {
                    // handle success
                    var catInfo = res.data.data;
                    commit('changeSelectedCat', catInfo);
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
        catList: state => {
            return state.catList
        },
        selectedCat: state => {
            return state.selectedCat;
        },
    }
};