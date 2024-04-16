// store/index.js
import { createStore } from 'vuex';

export default createStore({
  state: {
    isDarkMode: false,
    isLoading: false
  },
  mutations: {
    toggleDarkMode(state) {
      state.isDarkMode = !state.isDarkMode;
    },
    setLoading(state, isLoading) {
      state.isLoading = isLoading;
    }
  }
});