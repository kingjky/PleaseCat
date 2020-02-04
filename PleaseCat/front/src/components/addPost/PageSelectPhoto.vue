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

    <p>포스트 이미지 추가</p>
    <div class="canvas-wrap">
      <canvas id="uploadCanvas"></canvas>
    </div>
    
    <p>
      <input
        v-on:change="fileSelect()"
        ref="post_image"
        type="file"
        name="photo"
        id="photo"
        required="required"
      />
    </p>

    <p>
      <input class="btn-upload" v-on:click="submit" type="submit" value="저장~!"/>
    </p>
  </div>
</template>


<script>
export default {
  data() {
    return {
      user_no: "",
      cat_no: "",
      post_image: "",
      post_time: "",
      post_content: "",
      post_location: "",
      uploadImageFile: "",
    };
  },

  methods: {

    fileSelect() {
      // file 태그애 Vue 인스턴스로 접근하기 위해 $refs 속성을 사용해야한답니다.
      console.log(this.$refs);
      this.post_image = this.$refs.post_image.files[0];
      console.log(this.post_image);
    },
    submit() {
      
      if(this.post_image!=null) {
        return false;
      }
      const fd = new FormData();
      fd.append('user_no', 1) // user 받아와서 바꿔야함
      // fd.append('post_content', this.post_content)
      // fd.append('post_location', this.post_location)
      // fd.append('post_image', this.post_image)
      console.log("Aa")
      console.log(fd)
    },
    handleImage(e) {
      var fr = new FileReader();
      fr.onload = function(event) {
        var img = new Image();
        img.onload = function() {
          uploadCanvas.width = 300;
          uploadCanvas.height = 300;
          ctx.drawImage(img, 0, 0, 300, 300);
        };
        img.src = event.target.result;
      };
      fr.readAsDataURL(e.target.files[0]);
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
  padding-bottom: 100%;
}
#uploadCanvas {
  position: absolute;
  width: 100%;
  height: 100%;
}


</style>