<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
<script src="https://unpkg.com/vue"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.18/vue.min.js"></script>
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
              <img :src="require(`../../assets/images/cat/${post.cat_image}`)" id="profile" />
            </button>
          </router-link>
        </div>
        <div class="top">{{post.cat_name}}</div>
        <div class="top" id="time">{{post.post_time}}</div>
      </div>
      <div id="content">
        <img :src="require(`../../assets/images/cat/${post.post_image}`)" id="img" />
      </div>
      <div v-if="post.like === 'true'" class="HR" id="likeDisabled">
        <button
          v-on:click="likeDisabled(`${post.post_no}`,`${post.newsFeedIndex}`)"
          class="btnSize"
        >
          <img :src="require('../../assets/images/icons/2019090300416_0.png')" class="HRSize" />
        </button>
      </div>
      <div v-if="post.like === 'false'" class="HR" id="likeActivation">
        <button
          v-on:click="likeActivation(`${post.post_no}`,`${post.newsFeedIndex}`)"
          class="btnSize"
        >
          <img :src="require('../../assets/images/icons/untitled.png')" class="HRSize" />
        </button>
      </div>
      <div v-if="post.unlike === 'true'" class="HR">
        <button
          v-on:click="unLikeDisabled(`${post.post_no}`,`${post.newsFeedIndex}`)"
          class="btnSize"
        >
          <img :src="require('../../assets/images/icons/125082_154162_3227.jpg')" class="HRSize" />
        </button>
      </div>
      <div v-if="post.unlike === 'false'" class="HR">
        <button
          v-on:click="unLikeActivation(`${post.post_no}`,`${post.newsFeedIndex}`)"
          class="btnSize"
        >
          <img :src="require('../../assets/images/icons/unnamed.png')" class="HRSize" />
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
  "http://localhost:8080/api/NewsFeed/searchAll/{follower_no}?follower_no=1";
const api2 = "http://localhost:8080/api/Likes/searchAllLikes?user_no=1";
const api3 = "http://localhost:8080/api/Unlikes/searchAllUnLikes?user_no=1";
const likeActivationApi = "http://localhost:8080/api/Likes/insert?post_no=";
const likeDisabledApi = "http://localhost:8080/api/Likes/delete?post_no=";
const unLikeActivationApi = "http://localhost:8080/api/Unlikes/insert?post_no=";
const unLikeDisabledApi = "http://localhost:8080/api/Unlikes/delete?post_no=";

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
    likeActivation(post_no, newsFeedIndex) {
      axios.post(likeActivationApi + post_no + "&user_no=1");
      this.posts[newsFeedIndex].like = "true";
      if (this.posts[newsFeedIndex].unlike == "true") {
        this.unLikeDisabled(post_no, newsFeedIndex);
      }
      this.posts[newsFeedIndex].post_like++;
      this.updateLikes(this.posts[newsFeedIndex].post_like, post_no);
    },
    likeDisabled(post_no, newsFeedIndex) {
      axios.delete(likeDisabledApi + post_no + "&user_no=1");
      this.posts[newsFeedIndex].like = "false";
      this.posts[newsFeedIndex].post_like--;
      this.updateLikes(this.posts[newsFeedIndex].post_like,post_no);
    },
    unLikeActivation(post_no, newsFeedIndex) {
      axios.post(unLikeActivationApi + post_no + "&user_no=1");
      this.posts[newsFeedIndex].unlike = "true";
      if (this.posts[newsFeedIndex].like == "true") {
        this.likeDisabled(post_no, newsFeedIndex);
      }
      this.posts[newsFeedIndex].post_unlike++;
      this.updateUnLikes(this.posts[newsFeedIndex].post_unlike,post_no);
    },
    unLikeDisabled(post_no, newsFeedIndex) {
      axios.delete(unLikeDisabledApi + post_no + "&user_no=1");
      this.posts[newsFeedIndex].unlike = "false";
      this.posts[newsFeedIndex].post_unlike--;
      this.updateUnLikes(this.posts[newsFeedIndex].post_unlike,post_no);
    },
    updateLikes(postLike, postNo) {
      axios.put(`http://localhost:8080/api/post/updateLikes`, {
        post_like: postLike,
        post_no: postNo
      });
    },
    updateUnLikes(postUnLike, postNo) {
      axios.put(`http://localhost:8080/api/post/updateUnLikes`, {
        post_unlike: postUnLike,
        post_no: postNo,
      });
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
            data.data[this.page].newsFeedIndex = this.page;
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
      post_no: 0,
      post_like: 0,
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