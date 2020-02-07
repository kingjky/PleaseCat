import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    server: 'http://70.12.247.116:8080',
    token: '',
    selectedCatNo: '',
  },
  getters: {
    selectedCatNo: state => { return state.selectedCatNo },
    getServer: state=> {return state.server},
  },
  mutations: {
    selectCat(state, payload) {
      state.selectedCatNo = payload.n;
    }
  },
  actions: {
    selectCat({ commit }, payload) {
      commit('selectCat',{
        n: payload.n,
      })
    }
  },
  modules: {
  }
})
