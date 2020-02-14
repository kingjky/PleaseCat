<template>
<div id="catProfile" v-if="selectedCat">
    <div class="emptySpace">-Navigation Bar-</div>
    <div id="photoView">
        <div id="cat" >
            <router-link :to="`/catProfile/${selectedCat.cat_no}`">
            <!-- <div id="catPhoto" :style="{'background-image' : 'url('+require('../../assets/images/cat/1.jpg')+')'}"></div> -->
            <!-- <div id="catPhoto" :style="{'background-image': 'url('+require('../../assets/images/cat/1.jpg')+')'}">asdasdasdasdasd</div> -->
            <!-- <img id="catPhoto" src="../../assets/images/cat/0.jpg" alt="" > -->
            <img id="catPhoto" :src='require(`../../assets/images/cats/_profile/${selectedCat.cat_no}.jpg`)' alt="">
            <h1 id="catName" class="text">{{selectedCat.cat_name}}</h1>
            </router-link>
        </div>
        <div id="man" v-if="catManager">
            <router-link :to="`/userProfile/${catManager.user_no}`">
            <!-- <img id="manPhoto" src="../../assets/images/man/1.jpg" alt="" > -->
            <img id="manPhoto" :src='require(`../../assets/images/man/${catManager.user_no}.jpg`)' alt="">
            <h1 id="manName" class="text">{{catManager.user_id}}</h1>
            </router-link>
        </div>
    </div>
    <div id="descView" class="text">
        나이: {{selectedCat.age}}
        <br>털색: {{selectedCat.hair_color}}
        <br>눈색: {{selectedCat.eye_color}}
        <br>중성화: {{selectedCat.neuter}}
        <br>피부병:  {{selectedCat.skin_disease}}
        <br>다친곳: {{selectedCat.hurt}}
        <br>마지막 밥 먹은 시간: {{selectedCat.meal_time}}
    </div>
    <div id="mapView">
        <mapComponent v-if="postList" txt="readPost" :pos="positions" />
    </div>
    <div id="rankView" v-if="rankList">
        <div id="rankIcon" class="circle" :style="{'background-image' : `url(${require('@/assets/images/icons/rankIcon.jpg')})`}" alt="rank"></div>
        <RankComponent v-for="(rank, idx) in rankList" :key="idx" :ranking="idx+1" :user_no="rank.user_no" :score='rank.rank_point' :name="rank.user_id"/>
        <!-- <RankComponent :ranking='1' :name="'채집사'" :user_no='1' :score='100'/>
        <RankComponent :ranking='2' :name="'김집사'" :user_no='3' :score='97'/>
        <RankComponent :ranking='3' :name="'박집사'" :user_no='2' :score='89'/> -->
    </div>
    <div class="emptySpace">-Tab Bar-</div>
</div>
</template>

<script>
import mapComponent from '@/components/map/map'
import RankComponent from './view/Rank';
import axios from 'axios';
import { mapActions, mapMutations, mapGetters } from "vuex";

export default {
    name: 'catProfile',
    created() {
        this.no = this.$route.params.cat_no;
        this.getRankList({cat_no: this.no});
    },
    data(){
        return{
            no: '',
        }
    },
    computed:{
        ...mapGetters('storeCat',[
            'catList',
        ]),
        ...mapGetters('storeUser',[
            'userList',
        ]),
        ...mapGetters('storePost',[
            'postList',
        ]),
        ...mapGetters('storeUser/storeRank',[
            'rankList',
        ]),
        selectedCat: function() {
            return this.catList[this.no - 1];
        },
        catManager: function() {
            if(this.selectedCat != null){
                return this.userList[this.selectedCat.cat_manager-1];
            } else {
                return null;
            }
        },
        positions: function() {
            let array = [];
            if(this.postList != null){
                this.postList.forEach(post => {
                    if(post.cat_no == this.no){
                        array.push({
                            no: post.post_no,
                            pos_x: post.post_x,
                            pos_y: post.post_y,
                        })
                    }
                });
            }
            return array;
        }
    },
    components: {
        RankComponent,
        mapComponent,
    },
    methods: {
        ...mapActions('storeUser/storeRank',[
            'getRankList',
        ]),
    }
}
</script>

<style lang="scss" scoped>
#catProfile {
    text-align: center;
    .emptySpace {
        font-size: 50px;
        height: 60px;
        // margin: 5px;
    }
}
#photoView{
    padding: 10px;
    display: inline-block;
    width: 90%;
    text-align: center;
    // background-color: grey;
    * {
        vertical-align: middle;
    }
    img{
        width: 100%;
        border-radius: 100%;
    }
    img::after{
        content: "";
        display: block;
        padding-bottom: 100%;
    }
    h1{
        font-size: 7vw;
    }
    #cat{
        width: 30%;
        position: absolute;
        left: 30px;
        // box-sizing: border-box;
        // border: 1px solid red;
    }
    #man{
        width: 30%;
        position: absolute;
        right: 30px;
        // box-sizing: border-box;
        // border: 1px solid blue;
    }
}
#photoView::after{
    content: "";
    display: block;
    padding-bottom: 50%;
}
.text {
    // transition:all 0.4s ease-out;
    // text-shadow: 4px 2px 2px black;
    font-weight: bold;
    // color: #fff;
}
#descView{
    display: inline-block;
    width: 90%;
    text-align: left;
    // background-color: grey;
    padding: 10px;
    font-size: 4vw;
    margin-bottom: 1vw;
    border-top: 1px solid black;
    border-bottom: 1px solid black;
}
#rankView {
    // border: 1px solid black;
    #rankIcon {
        display: inline-block;
        width: 30vw;
        height: 30vw;
        background-size: 100%;
        // border: 1px solid black;
        // padding: 0px;
    }
    .circle {
        border-radius: 100%;
    }
}
#mapView{
    width: 80vw;
    height: 80vw;
}
</style>