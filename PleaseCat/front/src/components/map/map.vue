<template>
<div id="mapSection">
    <div>{{ dist }}</div>
    <div id="map"></div>
</div>
</template>

<script>
import router from '@/router/index';
export default {
    props: ['txt', 'pos', 'curLoca', 'range'],
    /* 
        txt: 지도상 위치 클릭 시 이동할 url (ex, catProfile)
            => this.url
        pos: 지도상에 표시 할 좌표 배열 [{pos_x,pos_y}, {pos_x,pos_y}, ...]
            => this.positions
        curLoca: 현재 User 위치
            => this. user
        range: 지도상에 표시 할 동그라미 반지름 미터 단위
            => this. dist
    */
    data() {
        return {
            url: this.txt,
            user: this.curLoca,
            // dist: this.range,     
        }
    },
    computed: {
        positions() {
            return this.pos;
        },
        dist() {
            return this.range;
        },
        avgX() {
            let sumX = 0;
            let len = this.positions.length;
            for (var i = 0; i < len; i ++) {
                sumX += this.positions[i].pos_x;
            }
            return sumX / len;
        },
        avgY() {
            let sumY = 0;
            let len = this.positions.length;
            for (var i = 0; i < len; i ++) {
                sumY += this.positions[i].pos_y;
            }
            return sumY / len;
        },
    },
    mounted(){
        this.drawMap(this.dist, this.positions);
    },
    updated(){
        this.drawMap(this.dist, this.positions);
    },
    methods: {
        drawMap(dist, positions){
            const vm = this;
            var mapContainer = document.getElementById('map'); // 지도를 표시할 div 
            var mapOption = { 
                center: (this.user == undefined)?(new kakao.maps.LatLng(this.avgX, this.avgY)):(new kakao.maps.LatLng(this.user.pos_x, this.user.pos_y)), // 지도의 중심좌표
                level: 5 // 지도의 확대 레벨
            };

            var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
            // var map = new kakao.maps.StaticMap(mapContainer, mapOption); // 지도를 생성합니다

            // var imageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 마커이미지의 주소입니다    
            var markerImages = [];
            for (var i = 0; i < positions.length; i ++) {
                var imageSrc = require(`@/assets/images/cats/_profile/${positions[i].no}.jpg`), // 마커이미지의 주소입니다    
                imageSize = new kakao.maps.Size(50, 50), // 마커이미지의 크기입니다
                imageOption = {offset: new kakao.maps.Point(30, 30)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
                
            // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
                markerImages.push(new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption));
            }

            if(this.user != undefined){
                var circle = new kakao.maps.Circle({
                    map: map,
                    center : new kakao.maps.LatLng(this.user.pos_x, this.user.pos_y),  // 원의 중심좌표 입니다 
                    radius: dist, // 미터 단위의 원의 반지름입니다 
                    strokeWeight: 5, // 선의 두께입니다 
                    strokeColor: '#75B8FA', // 선의 색깔입니다
                    strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                    strokeStyle: 'dashed', // 선의 스타일 입니다
                    fillColor: '#CFE7FF', // 채우기 색깔입니다
                    fillOpacity: 0.7  // 채우기 불투명도 입니다   
                });
            }
            // 마커를 생성합니다
            // var marker = new kakao.maps.Marker({
            //     position: markerPosition, 
            //     image: markerImage, // 마커이미지 설정 
            //     no: 1,
            // });

            // 마커에 클릭이벤트를 등록합니다
            

            for (var i = 0; i < positions.length; i ++) {
                // 마커를 생성합니다
                if(vm.url === 'catProfile'){
                    var marker = new kakao.maps.Marker({
                        map: map, // 마커를 표시할 지도
                        position: new kakao.maps.LatLng(positions[i].pos_x, positions[i].pos_y), // 마커의 위치
                        image: markerImages[i],
                    });
                } else {
                    var marker = new kakao.maps.Marker({
                        map: map, // 마커를 표시할 지도
                        position: new kakao.maps.LatLng(positions[i].pos_x, positions[i].pos_y), // 마커의 위치
                    });
                }

                // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
                // 이벤트 리스너로는 클로저를 만들어 등록합니다 
                // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
                kakao.maps.event.addListener(marker, 'click', makeOverListener(positions[i].no));
            }
            function makeOverListener(i) {
                return function() {
                    router.push(`/${vm.url}/${i}`);
                };
            }
            // 마커가 지도 위에 표시되도록 설정합니다
            // marker.setMap(map);  
        }
    }
}
</script>

<style lang="scss" scoped>
#map {
    width: 100%;
    height: 100%;
}
#mapSection {
    width: 100%;
    height: 100%;
}
</style>