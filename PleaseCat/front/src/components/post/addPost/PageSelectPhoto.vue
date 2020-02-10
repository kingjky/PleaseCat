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
      <canvas id="previewCanvas"></canvas>
    </div>

    <p>
      <input
        v-on:change="fileSelect"
        ref="postImage"
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
import axios from "axios";
// import VueMomentJs from "vue-momentjs"

// Vue.use(VueMomentJs, moment)
export default {
  data() {
    return {
      cat_no: "1", // cat 받아와서 바꿔야함
      user_no: "2", // user 받아와서 바꿔야함
      post_image: "",
      post_time: "",
      post_content: "",
      post_location: "",
      postImage: "",
    };
  },
  created() {

    this.server = this.$store.state.server;

  },
  methods: {

    fileSelect() {
      // file 태그애 Vue 인스턴스로 접근하기 위해 $refs 속성을 사용해야한답니다.
      // console.log(this.$refs);
      this.postImage = this.$refs.postImage.files[0];
      console.log(this.postImage);

      var canvas = document.getElementById("previewCanvas");
      var ctx = canvas.getContext("2d");

      var reader = new FileReader();
      reader.onload = function(event) {
        var img = new Image();

        img.onload = function() {
          var MAX_WIDTH = 420;
          var MAX_HEIGHT = 420;
          var width = img.width;
          var height = img.height;

          // 이미지 리사이징
          if (width > height) {
            if (width > MAX_WIDTH) {
              height *= MAX_WIDTH / width;
              width = MAX_WIDTH;
            }
          } else {
            if (height > MAX_HEIGHT) {
              width *= MAX_HEIGHT / height;
              height = MAX_HEIGHT;
            }
          }
          canvas.width = width;
          canvas.height = height;
          ctx.drawImage(img, 0, 0, width, height);

        };

        img.src = event.target.result;

      };

      reader.readAsDataURL(event.target.files[0]);
    },

    submit() {
      const fd = new FormData();
      console.log()
      fd.append("cat_no", 1) // cat 받아와서 바꿔야함
      fd.append("user_no", 2) // user 받아와서 바꿔야함
      fd.append("post_image", this.post_image.name)
      fd.append("post_time", Vue.prototype.moment(new Date()).format('YYYY-MM-DD hh:mm:ss'))
      fd.append('post_content', this.post_content)
      fd.append('post_location', this.post_location)
      fd.append("catImg", this.postImage)
      // console.log(fd)
      for (let key of fd.entries()) {
        console.log(`${key}`)
      }


      axios
        .post(this.$store.getters.getServer + `//api/post/insert`, fd, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(res => {
          console.log("200");
          if (res.data.state == "ok") {
            console.log("cat 저장 성공");
            console.log(res);
          } else {
            console.log("cat 등록 실패");
          }
        })
        .catch(err => {
          console.log("FAILURE");
          console.log(err);
        });

      let { cat_no, user_no, post_image } = this;
      let data = {
        cat_no,
        user_no,
        // post_content,
        // post_location,
        post_image,
        url: this.$store.getters.getServer
      };
      if (this.post_image == null) {
        return false;
      }

    }
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
  // padding-bottom: 100%;
}
#uploadCanvas {
  position: absolute;
  width: 100%;
  height: 100%;
}
</style>