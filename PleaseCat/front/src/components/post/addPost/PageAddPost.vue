<template>
  <div class="addPost">
    <div class="title-selectPhoto">NEW POST</div>

    <div class="upload-wrap">
      <div class="selectPhoto">
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
      </div>

      <div class="writingText">
        <p>
          <textarea
            class="textField"
            v-model="post_content"
            wrap="hard"
            placeholder=" 문구 입력..."
          ></textarea>
        </p>
      </div>
    </div>

    <div class="btn-wrap">test:{{userGps}}
      <div class="modal selectCat">
        <button
          id="show-modal-cat"
          @click=" showModalSelectCat = true"
        >#고양이 태그</button>

        <modal
          v-if="showModalSelectCat"
          @close="showModalSelectCat = false"
        >
          <h3 slot="header">이 중에 고양이가 있나요?</h3>
          <div
            slot="footer"
            @click="showModalSelectCat = false"
          >
            <button
              v-for="nc in nearCats"
              @click="tagCat(`${nc.no}`, `${nc.name}`)"
              :key=nc.no
            >{{ nc.name }}</button>
          </div>
        </modal>
      </div>

      <br>

      <div class="modal selectLoc">
        <button
          id="show-modal-loc"
          @click="showModalRegLocation = true, regLocation"
        >위치 추가</button>

        <modal
          v-if="showModalRegLocation"
          @close="showModalRegLocation = false"
        >
          <h3 slot="header">위치</h3>
          <div
            slot="footer"
            @click="showModalSelectCat = false"
          >

            <!-- 지도 component 추가 -->
          </div>
        </modal>
      </div>

    </div>

    <div class="submit-wrap">
      <p>
        <input
          class="btn-upload"
          v-on:click="submit"
          type="submit"
          value="저장~!"
        />
      </p>
    </div>
  </div>
</template>


<script>
import PostingApi from "@/apis/PostingApi";
import axios from "axios";
import Modal from "@/components/post/addPost/Modal.vue";
import EXIF from "../../../../node_modules/exif-js/exif";

export default {
  components: { Modal: Modal },
  data() {
    return {
      showModalSelectCat: false,
      showModalRegLocation: false,

      cat_no: "",
      user_no: "2", // user 받아와서 바꿔야함
      post_image: "",
      post_time: "",
      post_content: "",
      post_location: "",
      postImage: "",
      nearCats: [],
      photoGps: [],
      userGps: [],
      gpsX: "",
      gpsY: ""
    };
  },
  created() {
    this.server = this.$store.state.server;
    // this.getUserLoc();
  },

  methods: {
    toDecimal(gpsInfo) {
      return (
        gpsInfo[0].numerator +
        gpsInfo[1].numerator / (60 * gpsInfo[1].denominator) +
        gpsInfo[2].numerator / (3600 * gpsInfo[2].denominator)
      );
    },
    setGps() {
      // 사진의 메타데이터에 gps 정보가 있는 경우.
      if(this.photoGps) {
        console.log("사진에 gps 정보 있음")
        this.gps = this.photoGps
      }
      // 사진 메타데이터에 gps정보가 없는 경우, 사용자의 현재위치를 gps에 저장한다.
      else if (!this.photoGps.latitude || !this.photoGps.longitude) {
        console.log("사진에 gps 정보 없음...")
        this.gps = this.userGps;
      }
      // 사용자의 현재위치가 없는 경우,  default값 설정
      else if (!this.userGps.latitude || this.userGps.latitude) {
        console.log("사용자의 현재위치 없음..")
        this.gps = {
          latitude: 37.558245,
          longitude: 126.998207
        }
      }
    },
    getUserLoc() {
      // 사용자의 현재 위치를 가져오는 함수
      if (navigator.geolocation) {
        var self = this;
        navigator.geolocation.getCurrentPosition(

          function(position) {
            self.userGps = {
              latitude: position.coords.latitude,
              longitude: position.coords.longitude
            };
            alert("위도 : " + position.coords.latitude + ", 경도: " + position.coords.longitude);
          },

          function(err) {
            console.log("error");
            if (err.code == 1) { alert("Error: Access is denied!"); } 
            else if (err.code == 2) { alert("Error: Position is unavailable!"); }
          },
          { timeout: 30000, enableHighAccuracy: true, maximumAge: 75000 }

        );
      } else {
        alert("이 브라우저는 Geolocation을 지원하지 않음.");
      }
    },
    fileSelect() {
      //사진 선택 시 canvas에서 사진 미리보기
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

      // 사진 메타데이터 중 gps정보 가져오기

      // clear photoGps list
      this.photoGps = [];

      if (this.$refs.postImage.files[0]) {
        var self = this;
        // console.log(self)

        EXIF.getData(this.$refs.postImage.files[0], function() {
          // console.log('image info', this )
          if (this.exifdata.GPSLatitude && this.exifdata.GPSLongitude) {
            /*
            console.log(
              '위도 N', 
              this.exifdata.GPSLatitude[0].numerator, this.exifdata.GPSLatitude[1].numerator, this.exifdata.GPSLatitude[2].numerator 
            )
            console.log(
              '경도 E', 
              this.exifdata.GPSLongitude[0].numerator, this.exifdata.GPSLongitude[1].numerator, this.exifdata.GPSLongitude[2].numerator
            )
            */

            // photoGps 정보(시, 분, 초)를 소수로 바꿔 저장
            var lat = EXIF.getTag(this, "GPSLatitude");
            var long = EXIF.getTag(this, "GPSLongitude");
            console.log(lat, long);
            var dec_lat = self.toDecimal(lat);
            var dec_long = self.toDecimal(long);
            self.photoGps = {
              latitude: dec_lat,
              longitude: dec_long
            };
            console.log(dec_lat, dec_long);
          }

          // 지도 선택 시 기준이 될 gps좌표 설정
          // self.setGps();
          // 지도에서 위치 선택

          

          // gps 정보 이용해 근처 고양이 목록 불러오기
          axios
            .get(self.$store.getters.getServer + `/api/cat/searchAll`)
            .then(res => {
              if (res.data.state == "ok") {
                // clear nearCats list
                self.nearCats = [];
                for (var i = 0; i < res.data.data.length; i++) {
                  self.nearCats.push({
                    no: res.data.data[i].cat_no,
                    name: res.data.data[i].cat_name
                  });
                }
              } else {
                console.log("cat 정보 불러오기 실패");
              }
            })
            .catch(err => {
              console.log("서버 통신 실패");
              console.log(err);
            });
        });
      } else {
        console.log(`it's not a image`);
      }
    },
    regLocation() {
      // 지도에서 위치를 선택

      // 선택한 위치 값 받아오기
      // this.gpsX = this.gps.latitude
      // this.gpsY = this.gps.longtitude
      // console.log(this.post_location, this.gpsX, this.gpsY)

      
    },
    tagCat(no, name) {
      // 선택한 고양이 값 받아오기
      this.cat_no = no;
      // console.log("cat_no: " + no + ", cat_name: " + name + "  선택!!")
    },
    submit() {
      // postImage에 사진 등록
      // file 태그애 Vue 인스턴스로 접근하기 위해 $refs 속성을 사용함.
      this.postImage = this.$refs.postImage.files[0];

      // 사진이 없으면 게시글 등록 불가
      if (this.postImage == "") {
        return false;
      }

      // backend와 통신 할 때 넘겨줄 FormData 구성
      const fd = new FormData();
      fd.append("cat_no", this.cat_no);
      fd.append("user_no", this.user_no);
      fd.append(
        "post_time",
        Vue.prototype.moment(new Date()).format("YYYY-MM-DD HH:mm:ss")
      );
      fd.append("post_content", this.post_content);
      fd.append("post_location", this.post_location);
      fd.append("psot_x", this.gps.latitude);
      fd.append("psot_y", this.gps.longitude);
      fd.append("catImg", this.postImage);

      // FormData 확인할 때 key 이용.
      // for (let key of fd.entries()) {
      //   console.log(`${key}`);
      // }

      // axios 통신
      axios
        .post(this.$store.getters.getServer + `/api/post/insert`, fd, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(res => {
          console.log("200");
          if (res.data.state == "ok") {
            console.log("post 저장 성공");
            console.log(res);
          } else {
            console.log("post 등록 실패");
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
.addPost {
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
.textField {
  // resize: none;
  resize: vertical;
  height: 200px;
  width: 420px;
}
</style>