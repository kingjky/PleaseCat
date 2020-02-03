var mapInstance = {};

// var map = {};
export default {
    init() {            
        // #$#$ 여기에 현재 내 위치를 넣어줘야 한다. pos_x, pos_y 형태로 넣자
        // 사람의 위치
        let center_x = 37.54699
        let center_y = 127.09598
        // 고양이의 위치
        let pos_x = 37.54799
        let pos_y = 127.09798

        let pos_center = new kakao.maps.LatLng(center_x, center_y)

        let mapContainer = document.getElementById('map'), // 지도를 표시할 div new kakao.maps.LatLng(37.54699, 127.09598)
            mapOption = { 
                center: pos_center, // 지도의 중심좌표
                level: 4 // 지도의 확대 레벨
            };
        let mapInstance = new kakao.maps.Map(mapContainer, mapOption);

        // 지도에 표시할 원을 생성합니다
        let circle = new kakao.maps.Circle({
            center : pos_center,  // 원의 중심좌표 입니다 
            radius: 500, // 미터 단위의 원의 반지름입니다 
            strokeWeight: 3, // 선의 두께입니다 
            strokeColor: '#75B8FA', // 선의 색깔입니다
            strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'dashed', // 선의 스타일 입니다
            fillColor: '#CFE7FF', // 채우기 색깔입니다
            fillOpacity: 0.4  // 채우기 불투명도 입니다   
        }); 

        // 지도에 원을 표시합니다 
        circle.setMap(mapInstance); 

                
        // 마커를 생성합니다
        let marker_center = new kakao.maps.Marker({
            position: pos_center
        });
        // 아래 코드는 위의 마커를 생성하는 코드에서 clickable: true 와 같이

        // 마커가 지도 위에 표시되도록 설정합니다
        marker_center.setMap(mapInstance);
        
        
        let imageSrc = 'https://i.pinimg.com/236x/68/94/93/6894931eb3e93f6d6ef2dd000d8acdc6.jpg', // 마커이미지의 주소입니다        
            imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
            imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

        // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
        let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
            markerPosition = new kakao.maps.LatLng(37.54799, 127.09798); // 마커가 표시될 위치입니다

        // 마커를 생성합니다
        let marker = new kakao.maps.Marker({
            position: markerPosition,
            image: markerImage // 마커이미지 설정 
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(mapInstance);

        // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        let content = 
            '<style>' +
            '.customoverlay {position:relative;bottom:85px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;}' +
            '.customoverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}' +
            '.customoverlay a {display:block;text-decoration:none;color:#000;text-align:center;border-radius:6px;font-size:14px;font-weight:bold;overflow:hidden;background: #d95050;background: #d95050 url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}' +
            '.customoverlay .title {display:block;text-align:center;background:#fff;margin-right:35px;padding:10px 15px;font-size:14px;font-weight:bold;}' +
            ".customoverlay:after {content:'';position:absolute;margin-left:-12px;left:50%;bottom:-12px;width:22px;height:12px;background:url('http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}" +
            '</style>' +
            '<div class="customoverlay">' +
            '  <a href="http://70.12.246.77:8080/catList" target="_blank">' +
            '    <span class="title">나비</span>' +
            '  </a>' +
            '</div>'
            ;

        // 커스텀 오버레이가 표시될 위치입니다 
        let position = new kakao.maps.LatLng(pos_x, pos_y);  

        // 커스텀 오버레이를 생성합니다
        let customOverlay = new kakao.maps.CustomOverlay({
            map: mapInstance,
            position: position,
            content: content,
            yAnchor: 1 
        });

    },
    getMap() {
        return mapInstance;
    },
    // getMarker() {
    //     return marker;
    // },
};

    
    // var mapContainer = document.getElementById("map");
//     var mapOption = { 
//         center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
//         level: 3 // 지도의 확대 레벨
//     };
//     mapInstance = new kakao.maps.Map(mapContainer, mapOption);
    // // 마커가 표시될 위치입니다 
    // var markerPosition  = new kakao.maps.LatLng(33.450701, 126.570667); 

    // // 마커를 생성합니다
    // var marker = new kakao.maps.Marker({
    //     position: markerPosition
    // });

    // // 마커가 지도 위에 표시되도록 설정합니다
    // marker.setMap(mapContainer);
    // // 아래 코드는 지도 위의 마커를 제거하는 코드입니다
    // // marker.setMap(null);    


//     var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
//     mapOption = { 
//         center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
//         level: 3 // 지도의 확대 레벨
//     };

//     var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

//     // 지도를 클릭했을때 클릭한 위치에 마커를 추가하도록 지도에 클릭이벤트를 등록합니다
//     kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
//         // 클릭한 위치에 마커를 표시합니다 
//         addMarker(mouseEvent.latLng);  
//     });


//     // 지도에 표시된 마커 객체를 가지고 있을 배열입니다
//     var markers = [];
//     // 마커 하나를 지도위에 표시합니다 
//     addMarker(new kakao.maps.LatLng(33.450701, 126.570667));
//     // addMarker(new kakao.maps.LatLng(33.451800, 126.570667));
//     addMarker(new kakao.maps.LatLng(33.449720, 126.570767));

//     //*****************************************************************************************//
//     //***************************** 마커 누르면 이벤트 진행! **********************************//
    

//     // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
//     marker.setClickable(true);
//     // 마커를 지도에 표시합니다.
//     marker.setMap(map);
//     // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
//     var iwContent = '<div style="padding:5px;">We can do it! Cat!!!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
//         iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
//     // 인포윈도우를 생성합니다
//     var infowindow = new kakao.maps.InfoWindow({
//         content : iwContent,
//         removable : iwRemoveable
//     });
//     // 마커에 클릭이벤트를 등록합니다
//     kakao.maps.event.addListener(marker, 'click', function() {
//         // 마커 위에 인포윈도우를 표시합니다
//         infowindow.open(map, marker);     
//     });


//     // 마커를 생성하고 지도위에 표시하는 함수입니다
//     function addMarker(position) {
        
//         // 마커를 생성합니다
//         var marker = new kakao.maps.Marker({
//             position: position
//         });
//         // 아래 코드는 위의 마커를 생성하는 코드에서 clickable: true 와 같이

//         // 마커가 지도 위에 표시되도록 설정합니다
//         marker.setMap(map);
        
//         // 생성된 마커를 배열에 추가합니다
//         markers.push(marker);
//     }

//     // 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
//     function setMarkers(map) {
//         for (var i = 0; i < markers.length; i++) {
//             markers[i].setMap(map);
//         }            
//     }

//     // "마커 보이기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에 표시하는 함수입니다
//     function showMarkers() {
//         setMarkers(map)    
//     }

//     // "마커 감추기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에서 삭제하는 함수입니다
//     function hideMarkers() {
//         setMarkers(null);    
//     }

//   },
//   getMap() {
//     return mapInstance;
//   },
//   getMarker() {
//       return marker;
//   },
//   search() {

//   }
// };