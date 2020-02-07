import axios from 'axios'

const PostingApi = {
    requestAddPost: (url, data, callback, errorCallback) => requestAddPost(url, data, callback, errorCallback)
    ,requestAddCat: (url, data, callback, errorCallback) => requestAddCat(url, data, callback, errorCallback)
}

const requestAddCat = (url, data, callback, errorCallback) => {
    axios.post(server + `/api/post/insert`, {
        catImg: data.catPostImage,
        cat_no: data.cat_no,
        user_no: data.user_no,
    })
        .then((res) => {
            callback(res);
        })
        .catch((err) => {
            errorCallback(err);
        });

}


export default PostingApi
