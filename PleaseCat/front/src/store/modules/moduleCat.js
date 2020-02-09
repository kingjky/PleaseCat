// 고양이 정보와 관련된 Vuex Store Module
import axios from 'axios'

export default {
    namespaced: true,
    state: {
        catList: '',
        selectedCat: '',
    },
    // mutations : 동기 처리 logic
    mutations: { // (state, rootState?)
        /*
            state.catList을 업데이트
            호출 위치 : storeCat/getCatList
        */
        changeCatList(state, payload, rootState) {
            console.log('Now I am in changeCatList in mutations in storeCat');
            console.log('And this is payload from getCatList in actions')
            console.log(payload)
            state.catList = payload;
            console.log('Finally End getCatList')
        },
        changeSelectedCat(state, payload, rootState) {
            console.log('This is selectedCat from catList in state');
            console.log(state.catList[payload.no]);
            state.selectedCat = state.catList[payload.no - 1];
        },
    },
    // actions : 비동기 처리 logic
    actions: { // ({ dispatch, commit, getters, rootGetters }) : context. 생략
        /*
            DB에서 모든 고양이 정보를 읽어옴 -> mutation.changeCatList
            호출 위치 : App.vue
        */
        getCatList({ state, dispatch, commit, getters, rootGetters }) {
            console.log('I am in getCatList in actions in storeCat');
            console.log('This is getServer in rootGetters');
            console.log(rootGetters.getServer);
            axios
                .get(`${rootGetters.getServer}/api/cat/searchAll`)
                .then(res => {
                    // handle success
                    console.log('This is catList now');
                    console.log(state.catList);

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
        /*
            Vuex에 가진 catList 중 선택한 번호(data.cat_no)의 고양이를 selectedCat에 저장
            호출 위치 : PageCatProfile.vue
        */
        getSelectedCat({ state, dispatch, commit, getters, rootGetters }, data) {
            console.log('This is getSelectedCat in actions in storeCat');
            console.log('Selected Number is');
            console.log(data.cat_no);
            // if (!state.catList) {
            //     dispatch('getCatList');
            // }
            commit('changeSelectedCat', { 'no': data.cat_no });
        },
    },
    getters: { // (state, getters, rootState, rootGetters)
        catList: state => {
            return state.catList
        },
        selectedCat: state => {
            return state.selectedCat
        },
    }
};