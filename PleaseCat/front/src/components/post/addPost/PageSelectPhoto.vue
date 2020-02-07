<template>
  <div class="selectPhoto">
    <div class="title-selectPhoto">NEW POST</div>
    <!--
    <div class="preview">
      <div class="uploadPhoto">
        <div class="post-btn"></div>
      </div>
    </div>
    !-->

    <p>ORIGINAL 포스트 이미지 추가</p>
    <div class="canvas-wrap">
      <canvas id="uploadCanvas"></canvas>
    </div>

    <p>
      <input
        v-on:change="fileSelect"
        ref="post_image"
        type="file"
        name="photo"
        id="uploadPhoto"
        required="required"
      />
    </p>

    <p>
      <input class="btn-upload" v-on:click="submit" type="submit" value="저장~!" />
    </p>
  </div>
</template>


<script>
import PostingApi from "@/apis/PostingApi";
import axios from 'axios'
export default {
  data() {
    return {
      cat_no: "1",  // cat 받아와서 바꿔야함
      user_no: "2", // user 받아와서 바꿔야함
      post_image: "",
      post_time: "",
      post_content: "",
      post_location: "",
      // uploadImageFile: "",
    };
  },
  created() {
        this.server = this.$store.state.server;
    },
  methods: {

    fileSelect() {
      // file 태그애 Vue 인스턴스로 접근하기 위해 $refs 속성을 사용해야한답니다.
      // console.log(this.$refs);
      this.post_image = this.$refs.post_image.files[0];
      console.log(this.post_image);
    },
    
    submit() {
      var multer = require('multer');

      var storage = multer.diskStorage({
        destination: function(req, file, cb) {
          // cb,
        }
      })
      

      const formData = new FormData();
      formData.append('cat_no', 1) // cat 받아와서 바꿔야함
      formData.append('user_no', 2) // user 받아와서 바꿔야함
      // formData.append('post_content', this.post_content)
      // formData.append('post_location', this.post_location)
      formData.append('post_image', this.post_image.name)
      // console.log(formData)
      // for (let key of formData.entries()) {
      //   console.log(`${key}`)
      // }
      // console.log(formData)

      // axios로 multipart/form-data POST 요청 
      

      // axios.post(this.$store.state.server + `/SelectPhoto`, formData, {
      //   headers: {
      //     'Content-Type' : 'multipart/form-data'
      //   }
      // }).then((res) => {
      //   console.log(res)
      //   console.log()
      // }).catch((err) => {
      //   console.log(err)
      // })

      let { cat_no, user_no, post_image } = this;
      let data = {
        cat_no,
        user_no,
        // post_content,
        // post_location,
        post_image,
        url: this.$store.getters.getServer,
      };
      if(this.post_image==null) {
        return false;
      }
      
      
      PostingApi.requestAddPost(
        data,
        res => {
          if (res.status == 200) {
            if (res.data.state == "ok") {
              console.log(res.data.state)
              //성공
              // this.$router.push("/");
            } else {
              console.log(res)
            }
          }
        },
        error => {
          console.log("서버 에러 입니다");
        }
      )
      


    },
  }
};
</script>

<style lang="scss" scoped>
.selectPhoto {
  width: 600px;
  margin: 0 auto;
  margin-top: 10px;
  margin-bottom: 60px;
  // padding-top: 100px;
  padding-top: 10px;
  padding-bottom: 125px;
}
.canvas-wrap {
  position: relative;
  width: 60%;
}
.canvas-wrap:after {
  content: "";
  display: block;
  padding-bottom: 100%;
}
#uploadCanvas {
  position: absolute;
  width: 100%;
  height: 100%;
}
</style>