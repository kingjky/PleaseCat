<template>
    <div id="catList">
        <div class="emptySpace">-Navigation Bar-</div>
        <h1><router-link :to="'/nearMap'">지도</router-link></h1>
        <!-- <CatCardComponent key="1" name="name" desc1="♀" desc2="loca" src="1" /> -->
        <CatCardComponent v-for="cat in nearCatList" :key=cat.cat_no :name=cat.cat_name :desc1="cat.sex==='남'?'♂':'♀'" :desc2=cat.cat_location :src=cat.cat_no  />
        <div class="emptySpace">-Tab Bar-</div>
    </div>
</template>
<script>
import CatCardComponent from './catCard/CatCard'
import axios from 'axios'
import { mapActions, mapMutations, mapGetters } from "vuex";

export default {
    created() {
    },
    data() {
        return {
      }
    },
    components: {
        CatCardComponent,
    },
    computed: {
        ...mapGetters([
            'getDist', 'getUserLoc',
        ]),
        ...mapGetters('storeCat',[
            'catList',
        ]),
        nearCatList: function(getDist) {
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
                console.log(this.getUserLoc);
                this.catList.forEach(cat => {
                    if(distance(this.getUserLoc.lat, this.getUserLoc.lng, cat.cat_x, cat.cat_y) < this.getDist){
                        array.push(cat);
                    }
                });
            }
            return array;
        }
    },
}
</script>

<style lang="scss" scoped>
#catList {
    text-align: center;
    .emptySpace {
        // display: block;
        height: 100px;
        text-align: center;
    }
}
</style>