<template>
<div id="catProfile">
    <div id="profileView">
        <div id="leftPart">
            <!-- <div id="catPhoto" :style="{'background-image' : 'url('+require('../../assets/images/cat/1.jpg')+')'}"></div> -->
            <!-- <div id="catPhoto" :style="{'background-image': 'url('+require('../../assets/images/cat/1.jpg')+')'}">asdasdasdasdasd</div> -->
            <!-- <img id="catPhoto" src="../../assets/images/cat/0.jpg" alt="" > -->
            <img id="catPhoto" :src='cat.cat_no!=null?require(`@/assets/images/cat/${cat.cat_no}.jpg`):null' alt="" >
            
            
        </div>
        <section id="rightPart">
            <div id="name"><h1 id="catName" class="text">{{cat.cat_name}}</h1></div>
            <div id="buttons">
                <span id="followButton" class="btn text">
                    <button>팔로우</button>
                </span>
                <span id="detailButton" class="btn text">
                    <button>상세 정보</button>
                </span>
            </div>
        </section>
    </div>
    <div id="summaryView" class="text">
        <span class="summary">게시물<br>123</span>
        <span class="summary">팔로우<br>123</span>
        <span class="summary">좋아요<br>123</span>
    </div>
    <div id="photoView">
        <div id="photoList">
            <span class="photo" v-for="n in cnt_pics" :key=n>
                <img :src='require(`@/assets/images/cats/${cat.cat_no}/${n}.jpg`)' alt="pic">
            </span>
        </div>
    </div>
</div>
</template>

<script>
import axios from 'axios';
export default {
    name: 'catProfile',
    created() {
        this.no = this.$route.params.no;
        this.pullCat();
    },
    data(){
        return{
            cat: {},
            man: {},
            no: '',
            cnt_pics: 10,
        }
    },
    methods: {
        pullCat(){
            console.log(this.no);
            const vm = this;
            axios
                .get(`http://70.12.247.116:8080/api/cat/searchCat/{cat_no}?cat_no=${vm.no}`)
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
                .get(`http://70.12.247.116:8080/api/user/searchUserNo/{user_no}?user_no=${vm.cat.cat_manager}`)
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
#catProfile .btn{
    margin: 8px;
}
#catProfile button {
    border: 1px solid #dbdbdb;
    border-radius: 3px;
    color: #262626;
    font-size: 2.7vw;
    padding: 3px 12px 3px 12px;
}
#profileView{
    padding: 2% 2% 0 2%;
    position: relative;
    display: inline-block;
    width: 90%;
    vertical-align: middle;
    text-align: center;
    background-color: white;

    // border: 2px solid red;
}
#profileView::after{
    content: "";
    display: block;
    padding-bottom: 40%;
}
#profileView img{
    width: 100%;
    border-radius: 100%;
}
#profileView img::after{
    content: "";
    display: block;
    padding-bottom: 100%;
}
#profileView h1{
    font-size: 7vw;
}
#profileView > div{
    display: inline;
}
#profileView #leftPart{
    width: 30%;
    position: absolute;
    left: 5%;
    
    // box-sizing: border-box;
    // border: 1px solid red;
}
#profileView #rightPart{
    position: absolute;
    left: 40%;
    
    // box-sizing: border-box;
    // border: 1px solid red;
}
#profileView .line { 
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
    // text-shadow: 4px 2px 2px black;
    font-weight: bold;
    color: black;
}
#summaryView{
    display: inline-block;
    font-size: 3vw;
    width: 90%;
    text-align: center;
    padding: 5px 0 5px 0;
    // box-sizing: border-box;
    // border: 1px solid blue;
    border-top: 1px solid black;
    border-bottom: 1px solid black;
}
#summaryView .summary{
    display: inline-block;
    width: 33.3%;
    text-align: center;

    // box-sizing: border-box;
    // border: 1px solid red;
}
#photoView {
    width: 90%;
    padding: 0, 1%, 0, 1%;
    text-align: center;
}
#photoList {
    display: block;
    text-align: center;
}
#photoView .photo{
    display: inline-block;
    overflow: hidden;
    width: 200px;
    height: 200px;
}
#photoView .photo::after{
    // content: "";
    // display: block;
    // padding-bottom: 100%;
}
.photo img{
    display: inline-block; /* Otherwise it keeps some space around baseline */
    min-width: 100%; /* Scale up to fill container width */
    min-height: 100%; /* Scale up to fill container height */
    -ms-interpolation-mode: bicubic; /* Scaled images look a bit better in IE now */
}
</style>