<template>
  <div class="addCat">
    <div class="title-selectPhoto">NEW POST</div>
    
    

    <p>고양이 이미지 추가</p>
    <div class="canvas-wrap">
      <canvas id="previewCanvas" :src="uploadImage"></canvas>
      <!-- <img class = "preview" :src="uploadImage"> -->
    </div>

    <p>
      <input
        v-on:change="fileSelect"
        ref="catProfileImage"
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
import axios from "axios";
import Modal from "@/components/post/modal/Modal.vue";
export default {
  components: {
    Modal: Modal,
  },
  data() {
    return {
      cat_manager:'',
      reg_date: '',

      catImg: '',
      cat_name: '',
      cat_location: '', 
      age: '',
      sex: '',
      cat_desc: '',

      skin_disease: '',
      neuter:'',
      hurt: '',

      hair_color: '',
      eye_color: '',

      // uploadImage: '',
    };
  },
  created() {
    this.server = this.$store.state.server;
  },
  methods: {
    fileSelect: function(event) {
      console.log(this.$refs);
      this.catImg = this.$refs.catProfileImage.files[0];
      console.log(this.catImg);

      /*
			var input = event.target
			if(input.files && input.files[0]) {
				var reader = new FileReader()
				reader.onload = (e) => {
					this.uploadImage = e.target.result
				}
				reader.readAsDataURL(input.files[0])
			}
			*/

      var canvas = document.getElementById("previewCanvas");
      var ctx = canvas.getContext("2d");

      var reader = new FileReader();
      reader.onload = function(event) {
        var img = new Image();

        img.onload = function() {
          canvas.width = 300;
          canvas.height = 300;
          ctx.drawImage(img, 0,0, 300, 300)
          
        };

        img.src = event.target.result;

      };

      reader.readAsDataURL(event.target.files[0]);
      
    },
    submit() {
      let {
        cat_no,
        cat_name,
        cat_image,
        age,
        sex,
        cat_location,
        cat_desc,
        catImg,
        resizedImage
      } = this;

      let data = {
        cat_no,
        cat_name,
        cat_image,
        age,
        sex,
        cat_location,
        cat_desc,
        catImg,
        resizedImage
      };

      const fd = new FormData();
      // fd.append('cat_no', this.cat_no)
      fd.append("cat_name", this.cat_name);
      fd.append("cat_image", this.cat_image);
      // fd.append('age', this.age)
      fd.append("sex", this.sex);
      fd.append("cat_location", this.cat_location);
      fd.append("cat_desc", this.cat_desc);
      fd.append("catImg", this.catImg);

      for (let key of fd.entries()) {
        console.log(`${key}`);
      }
      console.log(fd);

      axios
        .post(this.$store.getters.getServer + `/api/cat/insert`, fd, {
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
  width: 70%;
  height: 360px;
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