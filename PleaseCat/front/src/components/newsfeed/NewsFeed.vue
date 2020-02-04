<template>
<div>
  <!-- <header>
    <h1>뉴스피드</h1>
  </header>-->
  <body>
    <div id="repeat" class="text" v-for="post in posts" :key="post.post_no">
      <div id="top">
        <div class="top">
          <router-link v-bind:to="{name:'Home'}">
            <button id="profileButton">
              <img :src="require(`../../assets/images/${post.cat_image}`)" id="profile" />
            </button>
          </router-link>
        </div>
        <div class="top">{{post.cat_name}}</div>
        <div class="top" id="time">{{post.post_time}}</div>
      </div>
      <div id="content">
        <img :src="require(`../../assets/images/${post.cat_image}`)" id="img" />
      </div>
      <div v-if="post.like === 'true'" class="HR">
        <button v-on:click="like" class="btnSize">
          <img :src="require('../../assets/images/2019090300416_0.png')" class="HRSize" />
        </button>
      </div>
      <div v-if="post.like === 'false'" class="HR">
        <button v-on:click="like" class="btnSize">
          <img :src="require('../../assets/images/untitled.png')" class="HRSize" />
        </button>
      </div>
      <div v-if="post.unlike === 'true'" class="HR">
        <button v-on:click="unLike" class="btnSize">
          <img :src="require('../../assets/images/125082_154162_3227.jpg')" class="HRSize" />
        </button>
      </div>
      <div v-if="post.unlike === 'false'" class="HR">
        <button v-on:click="unLike" class="btnSize">
          <img :src="require('../../assets/images/unnamed.png')" class="HRSize" />
        </button>
      </div>
      <br />
      <div class="like">좋아요</div>
      <div class="like">{{post.post_like}}개,</div>
      <div class="like">싫어요</div>
      <div class="like">{{post.post_unlike}}개</div>
      <div class="content">{{post.user_id}}</div>
      <div class="content">{{post.post_content}}</div>
      <!-- <div>{{post.post_image}}</div> -->
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
  </body>
</div>
</template>/
<script>
import axios from "axios";
import "../../assets/css/style.css";
import InfiniteLoading from "vue-infinite-loading";

const api =
  "http://70.12.247.116:8080/api/Etc/searchAll/{follower_no}?follower_no=1";
const api2 = "http://localhost:8080/api/Likes/searchAllLikes?user_no=1";
const api3 = "http://localhost:8080/api/Unlikes/searchAllUnLikes?user_no=1";

// import Vue from 'vue'
export default {
  created() {
    axios.get(api2, {}).then(({ data }) => {
      this.isLike = data.data;
    }),
      axios.get(api3, {}).then(({ data }) => {
        this.unLike = data.data;
        console.log(this.unLike);
      });
  },
  components: {
    InfiniteLoading
  },
  methods: {
    like() {
      // id, pw가 DB에 존재하는지 확인
      let { user_email, user_pw } = this;
      let data = {
        user_email,
        user_pw
      };

      UserApi.requestLogin(
        data,
        res => {
          if (res.status == 200) {
            if (res.data.state == "ok") {
              //성공
              this.$router.push("/");
            } else {
              //실패
            }
          }
        },
        error => {
          //요청이 끝나면 버튼 활성화
          // console.log("리턴")
          console.log("서버 에러");
          this.isSubmit = true;
        }
      );
    },
    unLike2() {
      // id, pw가 DB에 존재하는지 확인
      let { user_email, user_pw } = this;
      let data = {
        user_email,
        user_pw
      };

      UserApi.requestLogin(
        data,
        res => {
          if (res.status == 200) {
            if (res.data.state == "ok") {
              //성공
              this.$router.push("/");
            } else {
              //실패
            }
          }
        },
        error => {
          //요청이 끝나면 버튼 활성화
          // console.log("리턴")
          console.log("서버 에러");
          this.isSubmit = true;
        }
      );
    },

    infiniteHandler($state) {
      axios
        .get(api, {
          params: {
            postNo: 0
          }
        })
        .then(({ data }) => {
          if (data.data.length > this.page) {
            if (data.data[this.page].post_time < 1) {
              data.data[this.page].post_time = "방금 전";
            } else if (data.data[this.page].post_time < 60) {
              data.data[this.page].post_time =
                data.data[this.page].post_time + "분 전";
            } else if (data.data[this.page].post_time < 1440) {
              data.data[this.page].post_time =
                Math.floor(data.data[this.page].post_time / 60) + "시간 전";
            } else if (data.data[this.page].post_time < 44640) {
              data.data[this.page].post_time =
                Math.floor(data.data[this.page].post_time / 1440) + "일 전";
            } else if (data.data[this.page].post_time < 525600) {
              data.data[this.page].post_time =
                Math.floor(data.data[this.page].post_time / 44640) + "개월 전";
            } else {
              data.data[this.page].post_time =
                Math.floor(data.data[this.page].post_time / 525600) + "년 전";
            }
            data.data[this.page].like = "false";
            data.data[this.page].unlike = "false";
            for (var i = 0; i < this.isLike.length; i++) {
              if (data.data[this.page].post_no == this.isLike[i].post_no) {
                data.data[this.page].like = "true";
              }
            }
            for (var j = 0; j < this.unLike.length; j++) {
              if (data.data[this.page].post_no == this.unLike[j].post_no) {
                data.data[this.page].unlike = "true";
              }
            }
            this.posts.push(data.data[this.page]);
            this.postNo = data.data[this.page].post_no;
            this.page += 1;
            $state.loaded();
          } else {
            state.complete();
          }
          console.log(this.posts);
        });
    }
  },
  data: () => {
    return {
      unLike: [],
      isLike: [],
      page: 0,
      posts: [],
      cat_profile: "",
      email: "",
      homeImg: "",
      location: "",
      plus: "",
      search: "",
      profile: ""
    };
  }
};
</script>
 
<style lang="scss" scoped>
.content {
  font-size: 3vw;
}
#HR {
  display: inline;
}
.HR {
  display: inline;
}
.HRSize {
  width: 100%;
}
.btnSize {
  width: 9%;
}
.like {
  font-size: 3vw;
  display: inline;
}
#content {
  height: 100%;
  font-size: 0;
  line-height: 0;
}
#img {
  width: 100%;
}
.top {
  font-size: 3vw;
  display: inline;
}
#time {
  height: 100%;
  float: bottom;
  float: right;
}
#repeat {
  background-color: white;
  color: black;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 10%;
}
#profile {
  width: 100%;

  border-radius: 100%;
}
#profileButton {
  width: 10%;
}
</style>