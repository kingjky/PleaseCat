import Vue from 'vue'
import Vuex from 'vuex'
// import moduleCat from './modules/moduleCat.js'

Vue.use(Vuex)

export const moduleCat = {
  namespaced: true,
  state: {
      cat: {
      name: 'asd',
      age: '2', 
      }
  },
  mutations: {

  },
  actions: {

  },
  getters: {

  }
}

export default new Vuex.Store({
  modules: {
    moduleCat,
  },
  state: {
    // selectedCatNo: '0',
    server: 'http://70.12.247.116:8080',
    token: '',
  },
  getters: {
    // selectedCatNo: state => { return state.selectedCatNo },
    getServer: state=> {return state.server},
  },
  mutations: {
    // selectCat(state, payload) {
    //   state.selectedCatNo = payload.n;
    // }
  },
  actions: {
    // selectCat({ commit }, payload) {
    //   commit('selectCat',{
    //     n: payload.n,
    //   })
    // }
  },
  modules: {
  }
})
