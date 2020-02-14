<template>
    <div id="test">
        <mapComponent />
        <!-- <mapComponent txt="readPost" :pos="positions" /> -->
    </div>
</template>

<script>
import mapComponent from '@/components/map/selectlLocationMap'

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
    computed: {
        getUser(navigator) {
        // 사용자의 현재 위치를 가져오는 함수
            if (navigator) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    return {
                        pos_x: position.coords.latitude,
                        pos_y: position.coords.longitude
                    };
                });
            } else {
                return null;
            }
        },
    },
    components: {
        mapComponent,
    },
    methods: {
        
    }
}
</script>