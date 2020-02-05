import axios from 'axios'
import qs from 'qs'

const UserApi = {
    requestLogin: (url, data, callback, errorCallback) => requestLogin(url, data, callback, errorCallback)
    , requestSignup: (url, data, callback, errorCallback) => requestSignup(url, data, callback, errorCallback)
}

const requestLogin = (url, data, callback, errorCallback) => {
    //백앤드와 로그인 통신하는 부분
    axios.get(url + `/api/user/login?user_email=${data.user_email}&user_pw=${data.user_pw}`)
        .then((res) => {
            callback(res);
        })
        .catch((err) => {
            errorCallback(err);
        });

}
const requestSignup = (url, data, callback, errorCallback) => {
    axios.post(url + `/api/user/insert`, {
            user_id: data.user_id,
            user_pw: data.user_pw,
            user_email: data.user_email,
        })
        .then((res) => {
            callback(res);
        })
        .catch((err) => {
            errorCallback(err);
        });

}


export default UserApi
