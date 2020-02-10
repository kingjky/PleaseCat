import axios from 'axios'
import store from '@/store/index';

export default {
    axiosGetCatList: (data, callback, errcallback) => {
        console.log(store.getters.getServer);
        axios
            .get(`${ store.getters.getServer }/api/cat/searchAll`)
            .then(res => {
                // handle success
                callback();
            })
            .catch(err => {
                // handle error
                errcallback();
            })
            .then(() => {
                // always executed
            });
    },

}