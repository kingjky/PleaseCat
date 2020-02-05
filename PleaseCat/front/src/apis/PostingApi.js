import axios from 'axios'

const PostingApi = {
    requestAddPost: (url, data, callback, errorCallback) => requestAddPost(url, data, callback, errorCallback)
    ,
}

const requestAddPost = (url, data, callback, errorCallback) => {
    axios.post(url + `/api/post/insert`, {
        cat_no: data.cat_no,
        user_no: data.user_no,
        // post_content: data.post_content,
        // post_location: data.post_location,
        post_image: data.post_image.name,
    })
        .then((res) => {
            callback(res);
        })
        .catch((err) => {
            errorCallback(err);
        });

}


export default PostingApi