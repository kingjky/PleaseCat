<template>
<div id="myProfile">
    <div class="emptySpace">-Navigation Bar-</div>
    <div id="profileView" v-if="(getLoginInfo != null)">
        <div id="leftPart">
            <img id="userPhoto" :src='require(`@/assets/images/man/${ getLoginInfo.user_no }.jpg`)' alt="catProfile">
        </div>
        <section id="rightPart">
            <div id="name"><h1 id="catName" class="text">{{ getLoginInfo.user_id }}</h1></div>
            <div id="buttons">
                <span id="followButton" class="btn text">
                    <button id="show-modal-loc" @click="showModalFollow = true">
                        팔로우
                    </button>
                    <ModalComponent v-if="showModalFollow" @close="showModalFollow = false">
                        <h3 slot="head">
                            팔로우 목록
                        </h3>
                        <h3 slot="body">
                            본문
                            본문
                            본문
                            본문
                            본문
                            본문
                        </h3>
                        <div slot="foot">
                            <button @click="$emit('close')">Close</button>
                        </div>
                    </ModalComponent>
                </span>
                <span id="detailButton" class="btn text">
                    <router-link :to="`/catDetail/${no}`"><button>상세 정보</button></router-link>
                </span>
            </div>
        </section>
    </div>
    <div id="summaryView" class="text" v-if="(getLoginInfo != null)">
        <span class="summary">게시물<br>{{ getLoginInfo.count_posts }}</span>
        <span class="summary">팔로우<br>{{ getLoginInfo.count_followers }}</span>
        <span class="summary">좋아요<br>{{ getLoginInfo.count_likes }}</span>
    </div>
    <div id="photoView" v-if="(myPosts != null)">
        <div id="photoList">
            <span v-for="(post, idx) in myPosts" :key="idx">
                <router-link :to="{name:''}">
                    <span class="photo" :style="{'background-image' : `url(${require(`@/assets/images/posts/${ post.post_image }`)})`}"  :alt='`${ post.post_image }`'>
                    </span>
                </router-link>
            </span>
        </div>
    </div>
    <div class="emptySpace"></div>
</div>
</template>

<script>
import axios from 'axios';
import { mapActions, mapMutations, mapGetters } from "vuex";
import ModalComponent from "@/components/post/addPost/Modal.vue";

export default {
    name: 'myProfile',
    components: { ModalComponent },
    data(){
        return{
            showModalFollow: false,
            no: '',
        }
    },
    computed:{
        ...mapGetters('storePost',[
            'myPosts',
        ]),
        ...mapGetters([
            'getLoginInfo',
        ]),
    },
}
</script>

<style lang="scss" scoped>
#myProfile{
    text-align: center;
    .btn{
        margin: 8px;
    }
    button {
        border: 1px solid #dbdbdb;
        border-radius: 3px;
        color: #262626;
        font-size: 2.7vw;
        padding: 3px 12px 3px 12px;
    }
    h1{
        font-size: 7vw;
    }
    .emptySpace {
        height: 70px;
    }
    .text {
        // transition:all 0.4s ease-out;
        // text-shadow: 4px 2px 2px black;
        font-weight: bold;
        color: black;
    }
}
#profileView{
    padding: 2% 2% 0 2%;
    position: relative;
    display: inline-block;
    width: 90%;
    vertical-align: middle;
    text-align: center;
    background-color: #F2E6E1;
    border-radius: 10px;
    box-shadow: 2px 2px 10px 2px black;
    
    img {
        width: 100%;
        border-radius: 100%;
    }
    img::after{
        content: "";
        display: block;
        padding-bottom: 100%;
    }
    #leftPart{
        width: 30%;
        position: absolute;
        left: 5%;
        
        // box-sizing: border-box;
        // border: 1px solid red;
    }
    #rightPart{
        position: absolute;
        left: 40%;
        
        // box-sizing: border-box;
        // border: 1px solid red;
    }
}
#profileView::after{
    content: "";
    display: block;
    padding-bottom: 40%;
}
#summaryView{
    display: inline-block;
    font-size: 3vw;
    width: 90%;
    text-align: center;
    padding: 5px 0 5px 0;
    
    border-radius: 10px;
    // box-sizing: border-box;
    // border: 1px solid blue;
    // border-top: 1px solid black;
    // border-bottom: 1px solid black;
    .summary{
        display: inline-block;
        width: 33.3%;
        text-align: center;

        // box-sizing: border-box;
        // border: 1px solid red;
    }
}
#photoView {
    display: inline-block;
    width: 90%;
    padding: 0, 1%, 0, 1%;
    #photoList {
        text-align: left;
        .photo{
            background-color: black;
            display: inline-block;
            overflow: hidden;
            width: calc((100% - 12px) / 3);
            text-align: center;
            vertical-align: middle;
            box-sizing: border-box;
            margin: 2px;
            border-radius: 3px;
            box-shadow: 1px 1px 5px 1px black;
            // border: 1px solid red;
            background-position-x: 50%;
            background-position-y: 50%;
            background-size: cover;
            // max-width: 100%; /* Scale up to fill container width */
            // max-height: 100%; /* Scale up to fill container height */
            // min-width: 100%; /* Scale up to fill container width */
            // min-height: 100%; /* Scale up to fill container height */
            div {
                overflow: hidden;
                display: inline-block; /* Otherwise it keeps some space around baseline */
                // max-width: 100%; /* Scale up to fill container width */
                // max-height: 100%; /* Scale up to fill container height */
                -ms-interpolation-mode: bicubic; /* Scaled images look a bit better in IE now */
            }
        }
        .photo::after{
            content: '';
            display: block;
            padding-bottom: 100%;
        }
    }
}
</style>