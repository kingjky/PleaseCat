<!--
    이메일 다시 발송하기 페이지 입니다.-->

<template>
    <div id="kmap">
        <h1>내 주변 고양이 지도 보기</h1>
        <h2>{{msg}}</h2>
        <div id="map" style="width:500px;height:400px;"></div>
        <!-- <div class="customoverlay" style="margin-top: 1000px">
            <a href="https://map.kakao.com/link/map/11394059" target="_blank">
            <span class="title">나비</span>
            </a>
        </div> -->
        <div class="emptySpace"></div>
    </div>
</template>
<script>

    // import kakao from './kakao.min.js' 
    // 'https://developers.kakao.com/sdk/js/kakao.min.js'
    import KakaoMap from "../../apis/KakaoMap"

    export default {
        // name: 'map',
        data() {
            return {
                msg: "고양이를 부탁캣-카카오맵 테스트",
                cats: [],
            }
        },
        mounted() {
            KakaoMap.init()
            const map_ = KakaoMap.getMap()
            map_.search
      
            // marker_.search()
            
            // 마커가 지도 위에 표시되도록 설정합니다
            // marker.setMap(mapContainer);

            // 아래 코드는 지도 위의 마커를 제거하는 코드입니다
            // marker.setMap(null);    

            // var container = document.getElementById('map');
            // var options = {
            //     center: new kakao.maps.LatLng(33.450701, 126.570667),
            //     level: 3
            // };

            // var map = new kakao.maps.Map(container, options)
            }
        /*
        mounted() {
            var container = document.getElementById('map');
            var options = {
                center: new kakao.maps.LatLng(33.450701, 126.570667),
                level: 4
                };
            
            var map = new kakao.maps.Map(container, options);
                    
            var imageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 마커이미지의 주소입니다    
                imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
                imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
                
            // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                markerPosition = new kakao.maps.LatLng(37.54699, 127.09598); // 마커가 표시될 위치입니다

            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                position: markerPosition, 
                image: markerImage // 마커이미지 설정 
            });
            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(map);  
        }
        */
    }
</script>

<style scoped>
    .customoverlay {position:relative;bottom:85px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;}
    .customoverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}
    .customoverlay a {display:block;text-decoration:none;color:#000;text-align:center;border-radius:6px;font-size:14px;font-weight:bold;overflow:hidden;background: #d95050;background: #d95050 url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}
    .customoverlay .title {display:block;text-align:center;background:#fff;margin-right:35px;padding:10px 15px;font-size:14px;font-weight:bold;}
    .customoverlay:after {content:'';position:absolute;margin-left:-12px;left:50%;bottom:-12px;width:22px;height:12px;background:url('http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
.emptySpace {
    height: 75px;
}
</style>