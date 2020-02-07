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

    <p>고양이 사진 추가</p>
    <div class="canvas-wrap">
      <canvas id="uploadCanvas" :src="uploadImage"></canvas>
			<!-- <img class = "preview" :src="uplo"> -->
    </div>

    <p>
      <input
        v-on:change="fileSelect"
        ref="catProfileImage"
        type="file"
        name="photo"
        id="photo"
        required="required"
      />
    </p>

    <p>
      <input class="btn-upload" v-on:click="submit" type="submit" value="저장~!" />
    </p>
  </div>
</template>

<script>
import axios from 'axios'
import PostingApi from "@/apis/PostingApi";
export default {
  data() {
    return {
      cat_no: '', // cat 받아와서 바꿔야함
      cat_name: "야옹이", // user 받아와서 바꿔야함
      cat_image: "",
      age: "",
      sex: "",
      reg_date: "",
      meal_time: "",
      skin_disease: "",
      cat_location: "",
      cat_manager: "",
      neuter: "",
      cat_desc: "",
      hurt: "",
      hair_color: "",
      eye_color: "",
      catImg: ""
    };
  },
  created() {
		this.server = this.$store.state.server;
  },
  methods: {
    fileSelect() {
      console.log(this.$refs)
      this.catImg = this.$refs.catProfileImage.files[0]
			console.log(this.catImg)

			var input = event.target
			
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
        catImg
      } = this;

      let data = {
        cat_no,
        cat_name,
        cat_image,
        age,
        sex,
        cat_location,
        cat_desc,
        catImg
			};
			
			const fd = new FormData();
			// fd.append('cat_no', this.cat_no)
			fd.append('cat_name', this.cat_name)
			fd.append('cat_image', this.cat_image)
			// fd.append('age', this.age)
			fd.append('sex', this.sex)
			fd.append('cat_location', this.cat_location)
			fd.append('cat_desc', this.cat_desc)
			fd.append('catImg', this.catImg)

			for (let key of fd.entries()) {
        console.log(`${key}`)
      }
      console.log(fd)

			axios.post(this.$store.state.server + `/api/cat/insert`, 
				fd, {
        headers: {
          'Content-Type' : 'multipart/form-data'
        }
      }).then((res) => {
				console.log('SUCCESS')
				console.log(res)
      }).catch((err) => {
				console.log('FAILURE')
        console.log(err)
      })

      // PostingApi.requestAddCat(
      //   this.$store.state.server,
      //   fd,
      //   res => {
      //     if (res.status == 200) {
      //       if (res.data.state == "ok") {
      //         console.log(res.data.state);
      //         //성공
      //         // this.$router.push("/");
      //       } else {
      //         console.log(res);
      //       }
      //     }
      //   },
      //   error => {
      //     console.log("서버 에러 입니다");
      //   }
      // );
    }
  }
};
</script>

<style lang="scss" scoped>
</style>