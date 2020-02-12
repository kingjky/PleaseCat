<template>
<div style="text-align: center">
    <div class="emptySpace"></div>
    <div><span>내 주변 반경</span></div>
    <div>
        <input type="radio" id="d300" value="300" v-model="distance">
        <label for="d300">300m</label>
        <input type="radio" id="d500" value="500" v-model="distance">
        <label for="d500">500m</label>
        <input type="radio" id="d1000" value="1000" v-model="distance">
        <label for="d1000">1000m</label>
    </div>
    <div id="mapView">
        <mapComponent v-if="catList" txt="catProfile" :pos="positions" :curLoca="user" :range="distance"/>
    </div>
    <div class="emptySpace"></div>
</div>
</template>

<script>
import mapComponent from '@/components/catMap/map';
import { mapActions, mapMutations, mapGetters } from "vuex";
export default {
    name: 'nearMap',
    data() {
        return {
            user: {
                pos_x: 37.54099,
                pos_y: 127.09598,
            },
            distance: 500,
        }
    },
    components: {
        mapComponent,
    },
    computed:{
        ...mapGetters('storeCat',[
            'catList',
        ]),
        positions: function(distance) {
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
                    if(distance(this.user.pos_x, this.user.pos_y, cat.cat_x, cat.cat_y) < this.distance){
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