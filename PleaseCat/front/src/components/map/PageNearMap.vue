<template>
<div style="text-align: center">
    <div class="emptySpace"></div>
    <div><span>내 주변 반경</span></div>
    <div>
        <input type="radio" id="d300" value="300" v-model="dist" @click="changeDist(value)">
        <label for="d300">300m</label>
        <input type="radio" id="d500" value="500" v-model="dist" @click="changeDist(value)">
        <label for="d500">500m</label>
        <input type="radio" id="d1000" value="1000" v-model="dist" @click="changeDist(value)">
        <label for="d1000">1000m</label>
    </div>
    <div id="mapView">
        <mapComponent v-if="catList" txt="catProfile" :pos="positions" :curLoca="getUserLoc" :range="getDist"/>
    </div>
    <div class="emptySpace"></div>
</div>
</template>

<script>
import mapComponent from '@/components/map/map';
import { mapActions, mapMutations, mapGetters, mapState } from "vuex";
export default {
    name: 'nearMap',
    data() {
        return {
        }
    },
    components: {
        mapComponent,
    },
    computed:{
        ...mapState([
            'dist',
        ]),
        ...mapGetters([
            'getDist', 'getUserLoc',
        ]),
        ...mapGetters('storeCat',[
            'catList',
        ]),
        positions: function() {
            let array = [];

            function deg2rad(deg) {
                return deg * Math.PI / 180.0;
            }
            function rad2deg(rad) {
                return (rad * 180 / Math.PI);
            }
            function distance(lat1, lon1, lat2, lon2) {
                let theta = lon1 - lon2;
                let dist
                dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
                
                dist = Math.acos(dist);
                dist = rad2deg(dist);
                dist = dist * 60 * 1.1515;
                
                // if (unit == "kilometer") {
                    //     dist = dist * 1.609344;
                // } else if(unit == "meter"){
                    //     dist = dist * 1609.344;
                // }
                dist = dist * 1609.344;
                return (dist);
            };
            if(this.catList != null){
                this.catList.forEach(cat => {
                    if(distance(this.getUserLoc.lat, this.getUserLoc.lng, cat.cat_x, cat.cat_y) < this.getDist){
                        array.push({
                            no: cat.cat_no,
                            pos_x: cat.cat_x,
                            pos_y: cat.cat_y,
                        })
                    }
                });
            }
            return array;
        }
    },
    method:{
        ...mapMutations([
            'changeDist',
        ])
    },
}
</script>

<style>
.emptySpace{
    height: 70px;
}
#mapView {
    display: inline-block;
    width: 90vw;
    height: 50vw; 
}
 .dist{
     margin-left: 5vw;
 }
</style>