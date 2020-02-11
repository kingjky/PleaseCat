<template>
    <div id="test">
        <mapComponent txt="catProfile" :pos="positions" />
        <!-- <mapComponent txt="readPost" :pos="positions" /> -->
    </div>
</template>

<script>
import mapComponent from '@/components/catMap/map'

export default {
    data() {
      return {
        //   positions: [
        //         {
        //             no: '1', 
        //             latlng: new kakao.maps.LatLng(33.450705, 126.570677)
        //         },
        //         {
        //             no: '2', 
        //             latlng: new kakao.maps.LatLng(33.450936, 126.569477)
        //         },
        //         {
        //             no: '3', 
        //             latlng: new kakao.maps.LatLng(33.450879, 126.569940)
        //         },
        //         {
        //             no: '4', 
        //             latlng: new kakao.maps.LatLng(33.451393, 126.570738)
        //         }
        //     ],
            positions: [
                {
                    no: '1', 
                    pos_x: 33.450705,
                    pos_y: 126.570677,
                },
                {
                    no: '2', 
                    pos_x: 33.450936,
                    pos_y: 126.569477,
                },
                {
                    no: '3',
                    pos_x: 33.450879,
                    pos_y: 126.569940,
                },
                {
                    no: '4', 
                    pos_x: 33.451393,
                    pos_y: 126.570738,
                }
            ],
      }
    },
    components: {
        mapComponent,
    },
    methods: {
        
    }
}
</script>