<template>
<div id="selectMap">
    <p style="margin-top:-12px">
        <b>Chrome 브라우저는 https 환경에서만 geolocation을 지원합니다.</b> 참고해주세요.
    </p>
    <div id="map" style="width:100%;height:350px;"></div>
    <div>
        위도: <span id="lat">{{ mark_x }}</span>
    </div>
    <div>
        경도: <span id="lng">{{ mark_y }}</span>
    </div>
    <div>
        주소: <span id="address">{{ address }}</span>
    </div>
</div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
export default {
    data() {
        return {
            mark_x: 0,
            mark_y: 0,
            address: '',
        }
    },
    computed: {
        ...mapGetters(['getUserLoc']),
    },
    methods: {
        ...mapActions(['findUserLoc']),
    },
    created(){
        this.findUserLoc();
    },
    mounted(){
        const vm = this;
        var geocoder = new kakao.maps.services.Geocoder();
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = { 
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 10 // 지도의 확대 레벨 
        }; 

        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
            
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        
        console.log(vm.$store.state.userLoc);
        var locPosition = new kakao.maps.LatLng(vm.getUserLoc.lat, vm.getUserLoc.lng); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
            // message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다
        
        // 마커와 인포윈도우를 표시합니다
        vm.mark_x = vm.getUserLoc.lat;
        vm.mark_y = vm.getUserLoc.lng;

        var coord = new kakao.maps.LatLng(locPosition.getLat(), locPosition.getLng());
        var callback = function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                vm.address = result[0].address.address_name;
            }
        };
        geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
        
        displayMarker(locPosition);

        // 지도에 마커와 인포윈도우를 표시하는 함수입니다
        function displayMarker(locPosition) {

            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({  
                map: map, 
                position: locPosition
            }); 
            
            marker.setDraggable(true); 
            // 지도 중심좌표를 접속위치로 변경합니다
            map.setCenter(locPosition);     
            
            kakao.maps.event.addListener(marker, 'dragend', function() {
                // 출발 마커의 드래그가 종료될 때 마커 이미지를 원래 이미지로 변경합니다
                // console.log(marker.getPosition().getLat());
                // console.log(marker.getPosition().getLng());
                vm.mark_x = marker.getPosition().getLat(); 
                vm.mark_y = marker.getPosition().getLng();
                
                var coord = new kakao.maps.LatLng(marker.getPosition().getLat(), marker.getPosition().getLng());
                var callback = function(result, status) {
                    if (status === kakao.maps.services.Status.OK) {
                        vm.address = result[0].address.address_name;
                    }
                };
                geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
            });
        }    
    },
}
</script>

<style>

</style>