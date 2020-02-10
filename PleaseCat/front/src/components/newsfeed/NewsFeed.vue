<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
<script src="https://unpkg.com/vue"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.18/vue.min.js"></script>
<script src="https://unpkg.com/vue@2.4.2"></script>
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
      <div v-if="post.like === true" class="HR" id="likeDisabled">
        <button
          v-on:click="likeDisabled(`${post.post_no}`,`${post.newsFeedIndex}`)"
          class="btnSize"
        >
          <img :src="require('../../assets/images/icons/2019090300416_0.png')" class="HRSize" />
        </button>
      </div>
      <div v-if="post.like === false" class="HR" id="likeActivation">
        <button
          v-on:click="likeActivation(`${post.post_no}`,`${post.newsFeedIndex}`)"
          class="btnSize"
        >
          <img :src="require('../../assets/images/icons/untitled.png')" class="HRSize" />
        </button>
      </div>
      <div v-if="post.unlike === true" class="HR">
        <button
          v-on:click="unLikeDisabled(`${post.post_no}`,`${post.newsFeedIndex}`)"
          class="btnSize"
        >
          <img :src="require('../../assets/images/icons/125082_154162_3227.jpg')" class="HRSize" />
        </button>
      </div>
      <div v-if="post.unlike === false" class="HR">
        <button
          v-on:click="unLikeActivation(`${post.post_no}`,`${post.newsFeedIndex}`)"
          class="btnSize"
        >
          <img :src="require('../../assets/images/icons/unnamed.png')" class="HRSize" />
        </button>
      </div>
      <div id="app"></div>
      <br />
      <div class="like">좋아요</div>
      <div class="like">{{post.post_like}}개,</div>
      <div class="like">싫어요</div>
      <div class="like">{{post.post_unlike}}개</div>
      <div class="content">{{post.user_id}}</div>
      <div v-if="post.detail === 'Init'">
        <div class="content">{{post.post_content}}</div>
      </div>
      <div v-if="post.detail === 'true'">
        <div id="detailTrue">{{post.post_content}}</div>
        <button v-on:click="detailTrue(`${post.newsFeedIndex}`)">자세히보기</button>
      </div>
      <div v-if="post.detail === 'false'">
        <div id="detailFalse">{{post.post_content}}</div>
        <button v-on:click="detailFalse(`${post.newsFeedIndex}`)">간략히</button>
      </div>
      <!--  -->
      <!-- <div class="invest_list">
        <div class="invest_box invest_item1"></div>
        <div class="invest_box invest_item2"></div>
        <div class="invest_box invest_item3"></div>
      </div>

      <div class="loading_img txt_center">
        <img src="/img/invest_loading_img.gif" alt="로딩이미지" title="로딩이미지" />
      </div>

      <div class="load_btn_box txt_center">
        <a href="javascript:;" class="invest_plus">더 불러오기</a>
      </div> -->

      <!--  -->
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
  </body>
</div>
</template>


<script type="text/javascript">
  // var maxlength = 2;
	// var start_number = 0;

	// $(".invest_plus").on("click", function() {
	// 	start_number = start_number + 1;
	// 	if(start_number > maxlength) {
	// 		return false;
	// 	}
	// 	$(".loading_img").stop().fadeIn("fast");
	// 	$(".load_btn_box").fadeOut("fast");
	// 	$.ajax({
	// 		url: './ajax_invest_list.php', //주소
	// 		type: 'post', // get 또는 post 방식으로
	// 		data: {
	// 			// 보낼 데이터
	// 		},
	// 		success : function(data, status, xhr) { 
	// 			//성공시 동작
				
	// 			$(".invest_list").append(data);
	// 			$(".loading_img").stop().fadeOut("fast");
	// 			if(start_number >= maxlength) {
	// 				$(".load_btn_box").fadeOut("fast");
	// 				return false;
	// 			}
	// 			$(".load_btn_box").fadeIn("fast");
				
	// 		},

	// 		fail: function(error) {
	// 			// 실패 시 동작
	// 		},
	// 		always: function(response) {
	// 			// 성공하든 실패하든 항상 할 동작
	// 		}
	// 	});
  // });
  
  // <div class="invest_box invest_item1">
	// </div>

	// <div class="invest_box invest_item2">
	// </div>

	// <div class="invest_box invest_item3">
	// </div>
</script>


<script>
import axios from "axios";
import "../../assets/css/style.css";
import InfiniteLoading from "vue-infinite-loading";
import { mapActions, mapMutations, mapGetters } from "vuex";

const api = "/api/NewsFeed/searchAll/{follower_no}?follower_no=1";
const api2 = "/api/Likes/searchAllLikes?user_no=1";
const api3 = "/api/Unlikes/searchAllUnLikes?user_no=1";
const likeActivationApi = "/api/Likes/insert?post_no=";
const likeDisabledApi = "/api/Likes/delete?post_no=";
const unLikeActivationApi = "/api/Unlikes/insert?post_no=";
const unLikeDisabledApi = "/api/Unlikes/delete?post_no=";
// const api = "http://localhost:8080/api/NewsFeed/searchAll/{follower_no}?follower_no=1";
// const api2 = "http://localhost:8080/api/Likes/searchAllLikes?user_no=1";
// const api3 = "http://localhost:8080/api/Unlikes/searchAllUnLikes?user_no=1";
// const likeActivationApi = "http://localhost:8080/api/Likes/insert?post_no=";
// const likeDisabledApi = "http://localhost:8080/api/Likes/delete?post_no=";
// const unLikeActivationApi = "http://localhost:8080/api/Unlikes/insert?post_no=";
// const unLikeDisabledApi = "http://localhost:8080/api/Unlikes/delete?post_no=";

export default {
  created() {
    axios.get(this.url + api2, {}).then(({ data }) => {
      this.isLike = data.data;
    }),
    axios.get(this.url + api3, {}).then(({ data }) => {
      this.unLike = data.data;
      console.log(this.unLike);
    });
  },
  computed: {
    ...mapGetters({
        url: 'getServer',
    }),
  },
  components: {
    InfiniteLoading
  },
  methods: {
    handle_toggle: function() {
      this.is_show = !this.is_show; // #2, #3
    },
    detailTrue(newsFeedIndex) {
      this.posts[newsFeedIndex].detail = "false";
    },
    detailFalse(newsFeedIndex) {
      this.posts[newsFeedIndex].detail = "true";
    },
    likeActivation(post_no, newsFeedIndex) {
      axios.post(this.url + likeActivationApi + post_no + "&user_no=1");
      this.posts[newsFeedIndex].like = true;
      if (this.posts[newsFeedIndex].unlike) {
        this.unLikeDisabled(post_no, newsFeedIndex);
      }
      this.posts[newsFeedIndex].post_like++;
      this.updateLikes(this.posts[newsFeedIndex].post_like, post_no);
    },
    likeDisabled(post_no, newsFeedIndex) {
      axios.delete(this.url + likeDisabledApi + post_no + "&user_no=1");
      this.posts[newsFeedIndex].like = false;
      this.posts[newsFeedIndex].post_like--;
      this.updateLikes(this.posts[newsFeedIndex].post_like, post_no);
    },
    unLikeActivation(post_no, newsFeedIndex) {
      axios.post(this.url + unLikeActivationApi + post_no + "&user_no=1");
      this.posts[newsFeedIndex].unlike = true;
      if (this.posts[newsFeedIndex].like) {
        this.likeDisabled(post_no, newsFeedIndex);
      }
      this.posts[newsFeedIndex].post_unlike++;
      this.updateUnLikes(this.posts[newsFeedIndex].post_unlike, post_no);
    },
    unLikeDisabled(post_no, newsFeedIndex) {
      axios.delete(this.url + unLikeDisabledApi + post_no + "&user_no=1");
      this.posts[newsFeedIndex].unlike = false;
      this.posts[newsFeedIndex].post_unlike--;
      this.updateUnLikes(this.posts[newsFeedIndex].post_unlike, post_no);
    },
    updateLikes(postLike, postNo) {
      axios.put(this.url + '/api/post/updateLikes', {
        post_like: postLike,
        post_no: postNo
      });
    },
    updateUnLikes(postUnLike, postNo) {
      axios.put(this.url + '/api/post/updateUnLikes', {
        post_unlike: postUnLike,
        post_no: postNo
      });
    },

    infiniteHandler($state) {
      axios
        .get(this.url + api, {
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
            for (var i = 0; i < this.isLike.length; i++) {
              if (data.data[this.page].post_no == this.isLike[i].post_no) {
                data.data[this.page].like = true;
              }
            }
            for (var j = 0; j < this.unLike.length; j++) {
              if (data.data[this.page].post_no == this.unLike[j].post_no) {
                data.data[this.page].unlike = true;
              }
            }
            data.data[this.page].detail = "Init";
            if (data.data[this.page].post_content.length > 36)
              data.data[this.page].detail = "true";
            this.posts.push(data.data[this.page]);
            this.postNo = data.data[this.page].post_no;
            data.data[this.page].newsFeedIndex = this.page;
            this.page += 1;
            $state.loaded();
          } else {
            $state.complete();
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
.invest_list {
  font-size: 0;
  text-align: center;
}
.invest_box {
  display: inline-block;
  width: 31.333%;
  height: 50px;
  padding-bottom: 25%;
  margin-left: 3%;
  background-color: #f2849e;
  border-radius: 5px;
  margin-bottom: 3%;
}
.invest_box.invest_item1 {
  margin-left: 0;
}
.txt_center {
  text-align: center;
}
.invest_plus {
  display: inline-block;
  width: 180px;
  height: 48px;
  line-height: 46px;
  background-color: #f2849e;
  color: #fff;
  border-radius: 5px;
}
.loading_img {
  display: none;
}
.loading_img.on {
  display: block;
}

//
#detailTrue {
  font-size: 18px;
  display: inline-block;
  width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis; /* 여러 줄 자르기 추가 스타일 */
  white-space: normal;
  line-height: 1.2;
  // height: 3.6em;
  text-align: left;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
#detailFalse {
  font-size: 18px;
  display: inline-block;
  width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis; /* 여러 줄 자르기 추가 스타일 */
  white-space: normal;
  line-height: 1.2;
  // height: 3.6em;
  text-align: left;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-box-orient: vertical;
}
.content {
  font-size: 18px;
  display: inline-block;
  width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis; /* 여러 줄 자르기 추가 스타일 */
  white-space: normal;
  line-height: 1.2;
  // height: 3.6em;
  text-align: left;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
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