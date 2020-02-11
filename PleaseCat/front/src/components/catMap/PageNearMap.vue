<template>
<div style="text-align: center">
    <div id="mapView">
    <mapComponent v-if="catList" txt="catProfile" :pos="positions" :curLoca="user" />
    </div>
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
            }
        }
    },
    components: {
        mapComponent,
    },
    computed:{
        ...mapGetters('storeCat',[
            'catList',
        ]),
        positions: function() {
            // console.log(Math.PI)
            let array = [];
            // function function_one() {
            //     function_two(); // considering the next alert, I figured you wanted to call function_two first
            //     console.log("The function called 'function_one' has been called.");
            // }
            // function function_two() {
            //     console.log("The function called 'function_two' has been called.");
            // }
            // function_one();

            function deg2rad(deg) {
                // console.log(deg * Math.PI / 180.0);
                // console.log(deg * Math.PI / 180.0);
                return deg * Math.PI / 180.0;
            }
            function rad2deg(rad) {
                // console.log("rad2deg")
                return (rad * 180 / Math.PI);
            }
            function distance(lat1, lon1, lat2, lon2) {
                let theta = lon1 - lon2;
                // console.log(rad2deg(lat1));
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
                    if(distance(this.user.pos_x, this.user.pos_y, cat.cat_x, cat.cat_y) < 500){
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
#mapView {
    display: inline-block;
    width: 90vw;
    height: 100vw; 
}
</style>