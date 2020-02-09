<template>
<div id="catProfile">
    <div class="emptySpace">-Navigation Bar-</div>
    <div id="photoView">
        <div id="cat">
            <!-- <div id="catPhoto" :style="{'background-image' : 'url('+require('../../assets/images/cat/1.jpg')+')'}"></div> -->
            <!-- <div id="catPhoto" :style="{'background-image': 'url('+require('../../assets/images/cat/1.jpg')+')'}">asdasdasdasdasd</div> -->
            <!-- <img id="catPhoto" src="../../assets/images/cat/0.jpg" alt="" > -->
            <img id="catPhoto" :src='require(`../../assets/images/cat/${cat.cat_no}.jpg`)' alt="" v-if="cat.cat_no">
            <h1 id="catName" class="text">{{cat.cat_name}}</h1>
        </div>
        <div id="man">
            <!-- <img id="manPhoto" src="../../assets/images/man/1.jpg" alt="" > -->
            <img id="manPhoto" :src='require(`../../assets/images/man/${man.user_no}.jpg`)' alt="" v-if="man.user_no">
            <h1 id="manName" class="text">{{man.user_id}}</h1>
        </div>
    </div>
    <div id="descView" class="text">
        나이: {{cat.age}}
        <br>털색: {{cat.hair_color}}
        <br>눈색: {{cat.eye_color}}
        <br>중성화: {{cat.neuter}}
        <br>피부병:  {{cat.skin_disease}}
        <br>다친곳: {{cat.hurt}}
        <br>마지막 밥 먹은 시간: {{cat.meal_time}}
    </div>
    <div id="mapView">
    </div>
    <div id="rankView">
        <div id="rankIcon" class="circle" :style="{'background-image' : `url(${require('@/assets/images/icons/rankIcon.jpg')})`}" alt="rank"></div>
        <RankComponent :ranking='1' :name="'채집사'" :user_no='1' :score='100'/>
        <RankComponent :ranking='2' :name="'김집사'" :user_no='3' :score='97'/>
        <RankComponent :ranking='3' :name="'박집사'" :user_no='2' :score='89'/>
    </div>
    <div class="emptySpace">-Tab Bar-</div>
</div>
</template>

<script>
import RankComponent from './view/Rank';
import axios from 'axios';
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";

export default {
    name: 'catProfile',
    created() {
        this.no = this.$route.params.cat_no;
        this.pullCat();
        // console.log(this.$store.state.moduleCat);
        console.log(this.a);
    },
    data(){
        return{
            no: '',
            selectedCat: {},
            man: {},
        }
    },
    computed: {
        ...mapGetters('storeCat',[
            'catList',
        ]),
    },
    components: {
        RankComponent,
    },
    methods: {
        pullMan(){
            const vm = this;
            axios
                .get(`${this.server}/api/user/searchUserNo/?user_no=${vm.cat.cat_manager}`)
                .then(res => {
                    // handle success
                    vm.man = res.data.data
                })
                .catch(err =>  {
                    // handle error
                })
                .then(() => {
                    // always executed
                    // console.log(vm.man);
                });
        }
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
</style>