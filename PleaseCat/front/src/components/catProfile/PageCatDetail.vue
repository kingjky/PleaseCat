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

    </div>
    <div class="emptySpace">-Navigation Bar-</div>
</div>
</template>

<script>
import axios from 'axios';
export default {
    name: 'catProfile',
    created() {
        this.server = this.$store.state.server;
        this.no = this.$route.params.no;
        this.pullCat();
    },
    data(){
        return{
            server: '',
            cat: {},
            man: {},
            no: '',
        }
    },
    methods: {
        pullCat(){
            console.log(this.no);
            const vm = this;
            axios
                .get(`${this.server}/api/cat/searchCat/?cat_no=${vm.no}`)
                .then(res => {
                    // handle success
                    vm.cat = res.data.data
                })
                .catch(err =>  {
                    // handle error
                })
                .then(() => {
                    // always executed
                    console.log(vm.cat);
                    this.pullMan();
                });
        },
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
                    console.log(vm.man);
                });
        }
    }
}
</script>

<style lang="scss" scoped>
#catProfile{
    text-align: center;
}
#photoView{
    padding: 10px;
    position: relative;
    display: inline-block;
    width: 90%;
    vertical-align: middle;
    text-align: center;
    background-color: grey;
}
#photoView::after{
    content: "";
    display: block;
    padding-bottom: 50%;
}
#photoView img{
    width: 100%;
    border-radius: 100%;
}
#photoView img::after{
    content: "";
    display: block;
    padding-bottom: 100%;
}
#photoView h1{
    font-size: 7vw;
}
#photoView > div{
    display: inline;
}
#photoView #cat{
    width: 30%;
    position: absolute;
    left: 30px;
    // box-sizing: border-box;
    // border: 1px solid red;
}
#photoView #man{
    width: 30%;
    position: absolute;
    right: 30px;
    // box-sizing: border-box;
    // border: 1px solid blue;
}
#photoView .line { 
    display: inline-block;
    width: 50%; 
    text-align: center; 
    vertical-align: middle;
    border-bottom: 1px solid #000; 
    line-height: 0.1em;
    margin: 10px 0 20px; 
    padding:0 10px; 
}
#catProfile .emptySpace {
    height: 10px;
}
.text {
    // transition:all 0.4s ease-out;
    text-shadow: 4px 2px 2px black;
    font-weight: bold;
    color: #fff;
}
#descView{
    display: inline-block;
    width: 90%;
    text-align: left;
    margin-bottom: 100px;
    background-color: grey;
    padding: 10px;
    font-size: 4vw
}
</style>