<!--
    catMap 컴포넌트로 컴포넌트화 해도 될 듯! -->

<template>
    <div>
        <div id="kmap">
            <h1>고양이의 포스트 업로드 위치 보기</h1>
            <h2>{{msg}}</h2>
            <div id="CatPostMap" style="width:500px;height:400px;"></div>
            <div class="emptySpace"></div>
        </div>
        <!-- for문으로 다 뽑아보기! -->
        <h1>고양이 하나에 포스트들!!</h1>
        <!-- <div>{{cat}}</div>
        <div>{{ posts}}</div> -->
    </div>
</template>
<script>
    // import KakaoMap from "../../apis/KakaoMap"
    import axios from 'axios';

    export default {
        name: 'CatPostMap',
        // props: ['cats'], // 필요한 건 이름, 좌표 //'name','desc1','desc2','src'
        // cat_image, cat_location
                data() {
            return {
                msg: "고양이를 부탁캣-카카오맵 테스트",
                server: '',
                cat: {},
                no: 1,
                posts: [],
            }
        },
        // 나중에 컴포넌트화 하기
        // components: {
        // },
        methods: {
            async pullCat(){
                // console.log(this.no);
                const vm = this;
                await axios
                    .get(`${this.server}/api/cat/searchCat/?Cat_no=${vm.no}`)
                    .then(res=> {
                        //handle success
                        vm.cat = res.data.data // data.data?
            // cat 1마리에 대한 정보이므로 배열일 필요 없음
                        // res.data.data.forEach(el => {
                        // });
                    })
                    .catch(err => {
                        // handle error
                    })
                    .then(() => {
                        // always executed
                        // console.log(vm.cat);
                    });
                // console.log(vm.cat)
                console.log(this.cat)
                // this.cat = vm.cat // ?? 
            },
    // 잘 나오는지 확인부터 할 것!
            async pullPost(){
                console.log(this.no)
                const vm = this;
                await axios
                    .get(`${this.server}/api/post/searchPostCat/{Cat_no}?Cat_no=${vm.no}`)

                    .then(res=> {
                        // handle success
                        vm.posts = res.data.data
                        res.data.data.forEach(el=> {
                        });
                    })
                    .catch(err => {
                        // handle error
                    })
                    .then(() => {
                        //always excuted
                        // console.log(vm.posts);
                    });
                console.log(this.posts);
                
            },
            // /api/post/searchPostCat/{Cat_no}
        },
        async created() {   
    //         console.log("created")
    //         this.server = this.$store.state.server;
    //         this.no = 2// this.$route.params.no;
    //         this.pullCat();
    // // 잘 나오는지 확인하자!
    //         this.pullPost();

        },
        // async mounted() {
            

        // },
        async mounted() {
            this.server = this.$store.state.server;
            this.no = 2// this.$route.params.no;
            await this.pullCat();
    // 잘 나오는지 확인하자!
            await this.pullPost();
            console.log( this.cat )
            console.log( this.posts )
            console.log( this.cat.cat_name)
            console.log( this.cat.sex)
    // cat_image를 쓸 것인가 VS cat_name을 쓸 것인가?**
            console.log( this.cat.cat_image)


            let cat_sex = this.cat.sex
            let cat_name = this.cat.cat_name
            let cat_position_x_list = []
            let cat_position_y_list = []
            let post_image_list = []

            let posts_ = this.posts
            console.log( posts_ )
            for (let i=0; i<posts_.length; i++) {
                console.log( posts_[i].post_image )
                console.log( posts_[i] )
                await post_image_list.push(posts_[i].post_image)
                await cat_position_x_list.push(37.54699 + i*0.0008)
                await cat_position_y_list.push(127.09598 - i*0.0006)
            }
            console.log(post_image_list)
            console.log(cat_position_x_list)
            console.log(cat_position_y_list)


    // map 함수 넣기!
        // 사람의 위치
            let center_x = 37.54699
            let center_y = 127.09598

            // 고양이의 위치
            let pos_x = 37.54799
            let pos_y = 127.09798

            let pos_center = new kakao.maps.LatLng(center_x, center_y)

            let mapContainer = await document.getElementById('CatPostMap'), // 지도를 표시할 div new kakao.maps.LatLng(37.54699, 127.09598)
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

            // 선을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 선을 표시합니다
            var linePath = [];
            for (let i = 0 ; i < posts_.length; i++){
                linePath.push(new kakao.maps.LatLng(cat_position_x_list[i], cat_position_y_list[i]))
            }
            // 지도에 표시할 선을 생성합니다
            var polyline = new kakao.maps.Polyline({
                path: linePath, // 선을 구성하는 좌표배열 입니다
                strokeWeight: 5, // 선의 두께 입니다
                strokeColor: '#FFAE00', // 선의 색깔입니다
                strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle: 'solid' // 선의 스타일입니다
            });

            // 지도에 선을 표시합니다 
            polyline.setMap(mapInstance);  



                // 마커를 생성합니다
            let marker_center = new kakao.maps.Marker({
                position: pos_center
            });
            // 아래 코드는 위의 마커를 생성하는 코드에서 clickable: true 와 같이

            // 마커가 지도 위에 표시되도록 설정합니다
            // marker_center.setMap(mapInstance);
           
            marker_center.setMap(mapInstance);
            
            let marker, imageSrc, imageSize, imageOption, markerImage, markerPosition, content, position, customOverlay


            // 이미지 뽑는 부분을 정리하자!
            for (let i=0; i<posts_.length; i++) {
                imageSrc = 'https://i.pinimg.com/236x/68/94/93/6894931eb3e93f6d6ef2dd000d8acdc6.jpg', // 마커이미지의 주소입니다        
                    imageSize = new kakao.maps.Size(44,49)// (64, 69), // 마커이미지의 크기입니다
                    imageOption = {offset: new kakao.maps.Point(20, 49)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.


                // https://i.pinimg.com/236x/68/94/93/6894931eb3e93f6d6ef2dd000d8acdc6.jpg
                //  imageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 마커이미지의 주소입니다    
                //     imageSize = new kakao.maps.Size(44, 49), // 마커이미지의 크기입니다
                //     imageOption = {offset: new kakao.maps.Point(20, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
                    

                // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
                markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                    markerPosition = new kakao.maps.LatLng(cat_position_x_list[i], cat_position_y_list[i]); // 마커가 표시될 위치입니다

                // 마커를 생성합니다
                marker = new kakao.maps.Marker({
                    position: markerPosition,
                    image: markerImage // 마커이미지 설정 
                });

                // 마커가 지도 위에 표시되도록 설정합니다
                marker.setMap(mapInstance);

                // ##$$ 이 부분을 router로 수정 가능한지만 확인!
                // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
                content = 
                    '<style>' +
                    '.customoverlay {position:relative;bottom:85px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;}' +
                    '.customoverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}' +
                    '.customoverlay a {display:block;text-decoration:none;color:#000;text-align:center;border-radius:6px;font-size:14px;font-weight:bold;overflow:hidden;background: #d95050;background: #d95050 url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}' +
                    '.customoverlay .title {display:block;text-align:center;background:#fff;margin-right:35px;padding:10px 15px;font-size:14px;font-weight:bold;}' +
                    ".customoverlay:after {content:'';position:absolute;margin-left:-12px;left:50%;bottom:-12px;width:22px;height:12px;background:url('http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}" +
                    '</style>' +
                    `<div class="customoverlay">` +
                    `  <a href="http://70.12.246.77:3000/catList" target="_blank">` +
                    `       <span class="title">${cat_name}</span>` +
                    '  </a>' +
                    '</div>'
                    ;
                // on click event listener

                    // '  <a href="http://70.12.246.77:8080/catList" target="_blank">' +
                    // '    <span class="title">나비</span>' +
                    // '  </a>' +
                // 커스텀 오버레이가 표시될 위치입니다 
        // 최초 위치만 표시하고 나머지는 표시 X, 다른 위치의 경우
                position = new kakao.maps.LatLng(cat_position_x_list[0], cat_position_y_list[0]);  

                // 커스텀 오버레이를 생성합니다
                customOverlay = new kakao.maps.CustomOverlay({
                    map: mapInstance,
                    position: position,
                    content: content,
                    yAnchor: 1 
                });
            }
        },
        updated() {
    //         mapInstance = {};  
    // // map 함수 넣기!
    //     // 사람의 위치
    //         let center_x = 37.54699
    //         let center_y = 127.09598

    //         // 고양이의 위치
    //         let pos_x = 37.54799
    //         let pos_y = 127.09798

    //         let pos_center = new kakao.maps.LatLng(center_x, center_y)

    //         let mapContainer = document.getElementById('CatPostMap'), // 지도를 표시할 div new kakao.maps.LatLng(37.54699, 127.09598)
    //             mapOption = { 
    //                 center: pos_center, // 지도의 중심좌표
    //                 level: 4 // 지도의 확대 레벨
    //             };
    //         let mapInstance = new kakao.maps.Map(mapContainer, mapOption);

    //         // 지도에 표시할 원을 생성합니다
    //         let circle = new kakao.maps.Circle({
    //             center : pos_center,  // 원의 중심좌표 입니다 
    //             radius: 500, // 미터 단위의 원의 반지름입니다 
    //             strokeWeight: 3, // 선의 두께입니다 
    //             strokeColor: '#75B8FA', // 선의 색깔입니다
    //             strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    //             strokeStyle: 'dashed', // 선의 스타일 입니다
    //             fillColor: '#CFE7FF', // 채우기 색깔입니다
    //             fillOpacity: 0.4  // 채우기 불투명도 입니다   
    //         }); 

    //         // 지도에 원을 표시합니다 
    //         circle.setMap(mapInstance); 

        /////////////////////
                    
            // 마커를 생성합니다
            let marker_center = new kakao.maps.Marker({
                position: pos_center
            });
            // 아래 코드는 위의 마커를 생성하는 코드에서 clickable: true 와 같이

            // 마커가 지도 위에 표시되도록 설정합니다
            // marker_center.setMap(mapInstance);
            let marker = marker_center
            marker.setMap(mapInstance);

            let imageSrc = 'https://i.pinimg.com/236x/68/94/93/6894931eb3e93f6d6ef2dd000d8acdc6.jpg', // 마커이미지의 주소입니다        
                imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
                imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

            // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
            let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                markerPosition = new kakao.maps.LatLng(37.54799, 127.09798); // 마커가 표시될 위치입니다

            // 마커를 생성합니다
            marker = new kakao.maps.Marker({
                position: markerPosition,
                image: markerImage // 마커이미지 설정 
            });

            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(mapInstance);

            // ##$$ 이 부분을 router로 수정 가능한지만 확인!
            // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            let content = 
                '<style>' +
                '.customoverlay {position:relative;bottom:85px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;}' +
                '.customoverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}' +
                '.customoverlay a {display:block;text-decoration:none;color:#000;text-align:center;border-radius:6px;font-size:14px;font-weight:bold;overflow:hidden;background: #d95050;background: #d95050 url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}' +
                '.customoverlay .title {display:block;text-align:center;background:#fff;margin-right:35px;padding:10px 15px;font-size:14px;font-weight:bold;}' +
                ".customoverlay:after {content:'';position:absolute;margin-left:-12px;left:50%;bottom:-12px;width:22px;height:12px;background:url('http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}" +
                '</style>' +
                '<div class="customoverlay" id="navi1">' +

                    '<span class="title">나비</span>' +

                '</div>'
                ;
            // on click event listener

                // '  <a href="http://70.12.246.77:8080/catList" target="_blank">' +
                // '    <span class="title">나비</span>' +
                // '  </a>' +
            // 커스텀 오버레이가 표시될 위치입니다 

            let position = new kakao.maps.LatLng(pos_x, pos_y);  

            // 커스텀 오버레이를 생성합니다
            let customOverlay = new kakao.maps.CustomOverlay({
                map: mapInstance,
                position: position,
                content: content,
                yAnchor: 1 
            });

        }
    }
// #$#$ 여기에 현재 내 위치를 넣어줘야 한다. pos_x, pos_y 형태로 넣자
// 실험해보기 : created에서 만들고 -> mounted -> updated 되면 맵 인스턴스 안에 반영이 되나 
            // // 사람의 위치
            // let center_x = 37.54699
            // let center_y = 127.09598
        
            // // 고양이의 위치
            // let pos_x = 37.54799
            // let pos_y = 127.09798

            // let pos_center = new kakao.maps.LatLng(center_x, center_y)

            // let mapContainer = document.getElementById('catMapPost'), // 지도를 표시할 div new kakao.maps.LatLng(37.54699, 127.09598)
            //     mapOption = { 
            //         center: pos_center, // 지도의 중심좌표
            //         level: 4 // 지도의 확대 레벨
            //     };
            // let mapInstance = new kakao.maps.Map(mapContainer, mapOption);



        // mounted() {
            // this.server = this.$store.state.server;
    //         this.no = 2// this.$route.params.no;
    //         this.pullCat();
    // // 잘 나오는지 확인하자!
    //         this.pullPost();
            
    // // 사람의 위치
    //         let center_x = 37.54699
    //         let center_y = 127.09598
        
    //         // 고양이의 위치
    //         let pos_x = 37.54799
    //         let pos_y = 127.09798

    //         let pos_center = new kakao.maps.LatLng(center_x, center_y)

    //         let mapContainer = document.getElementById('catPostMap'), // 지도를 표시할 div new kakao.maps.LatLng(37.54699, 127.09598)
    //             mapOption = { 
    //                 center: pos_center, // 지도의 중심좌표
    //                 level: 4 // 지도의 확대 레벨
    //             };
    //         // let mapInstance = new kakao.maps.Map(mapContainer, mapOption);
    //         // 지도에 표시할 원을 생성합니다
    //         let circle = new kakao.maps.Circle({
    //             center : pos_center,  // 원의 중심좌표 입니다 
    //             radius: 500, // 미터 단위의 원의 반지름입니다 
    //             strokeWeight: 3, // 선의 두께입니다 
    //             strokeColor: '#75B8FA', // 선의 색깔입니다
    //             strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    //             strokeStyle: 'dashed', // 선의 스타일 입니다
    //             fillColor: '#CFE7FF', // 채우기 색깔입니다
    //             fillOpacity: 0.4  // 채우기 불투명도 입니다   
    //         }); 

    //         // 지도에 원을 표시합니다 
    //         circle.setMap(mapInstance); 

    //         // 마커를 생성합니다
    //         let marker_center = new kakao.maps.Marker({
    //             position: pos_center
    //         });
    //         // 아래 코드는 위의 마커를 생성하는 코드에서 clickable: true 와 같이

    //         // 마커가 지도 위에 표시되도록 설정합니다
    //         // marker_center.setMap(mapInstance);
    //         marker = marker_center
    //         marker.setMap(mapInstance);
    // // 1. 데이터를 배열 형태로 가져오기

    // // 2. 배열형태 데이터를 활용하여 markers = [marker1, marker2, ...] 생성하기

    // // 3.   for (const marker in markers){
    //         // }

    // // catImage->cat_image로 대체해서 사용!
    //         let catImage='https://i.pinimg.com/236x/68/94/93/6894931eb3e93f6d6ef2dd000d8acdc6.jpg'
            
    //         let imageSrc = catImage, // 마커이미지의 주소입니다        
    //             imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
    //             imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

    //         // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
    //         let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
    //             markerPosition = new kakao.maps.LatLng(37.54799, 127.09798); // 마커가 표시될 위치입니다

    //         // 마커를 생성합니다
    //         let marker = new kakao.maps.Marker({
    //             position: markerPosition,
    //             image: markerImage // 마커이미지 설정 
    //         });

    //         // 마커가 지도 위에 표시되도록 설정합니다
    //         marker.setMap(mapInstance);

    //         // ##$$ 이 부분을 router로 수정 가능한지만 확인!
    //         // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    //         let content = 
    //             '<style>' +
    //             '.customoverlay {position:relative;bottom:85px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;}' +
    //             '.customoverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}' +
    //             '.customoverlay a {display:block;text-decoration:none;color:#000;text-align:center;border-radius:6px;font-size:14px;font-weight:bold;overflow:hidden;background: #d95050;background: #d95050 url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}' +
    //             '.customoverlay .title {display:block;text-align:center;background:#fff;margin-right:35px;padding:10px 15px;font-size:14px;font-weight:bold;}' +
    //             ".customoverlay:after {content:'';position:absolute;margin-left:-12px;left:50%;bottom:-12px;width:22px;height:12px;background:url('http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}" +
    //             '</style>' +
    //             '<div class="customoverlay" id="navi1">' +

    //             '<span class="title">나비</span>' +

    //             '</div>'
    //             ;
    //         // on click event listener

    //             // '  <a href="http://70.12.246.77:8080/catList" target="_blank">' +
    //             // '    <span class="title">나비</span>' +
    //             // '  </a>' +
    //         // 커스텀 오버레이가 표시될 위치입니다 

    //         let position = new kakao.maps.LatLng(pos_x, pos_y);  

    //         // 커스텀 오버레이를 생성합니다
    //         let customOverlay = new kakao.maps.CustomOverlay({
    //             map: mapInstance,
    //             position: position,
    //             content: content,
    //             yAnchor: 1 
    //         });

            
    //         // });

    //         // const map_ = KakaoMap.getMap()
    //         // map_.search
        
    //         // marker_.search()
            
    //         // 마커가 지도 위에 표시되도록 설정합니다
    //         // marker.setMap(mapContainer);

    //         // 아래 코드는 지도 위의 마커를 제거하는 코드입니다
    //         // marker.setMap(null);    

    //         // var container = document.getElementById('map');
    //         // var options = {
    //         //     center: new kakao.maps.LatLng(33.450701, 126.570667),
    //         //     level: 3
    //         // };

    //         // var map = new kakao.maps.Map(container, options)
    //     },
    //     updated() {                   
    //         // let comp = 'navi1'
    //         // const cat = document.getElementById(comp);
    //         // cat.onmouseover = function() {
    //         //     console.log("삐요삐용")
    //         // }
    //         // 콜백 함수를 제공함으로써 '클릭' 이벤트를 처리하는 핸들러를 추가합니다.
    //         // 엘리먼트가 클릭될 떄마다, "누름!" 팝업이 나타날것입니다.
    //         // cat.addEventListener('', function (event) {
    //         //     console.log("삐용삐용")
    //         //     this.$route.push({'name': 'PageCatProfile'})
    //         // });
    //     },

    // };
</script>

<style scoped>
    /* .customoverlay {position:relative;bottom:85px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;}
    .customoverlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}
    .customoverlay a {display:block;text-decoration:none;color:#000;text-align:center;border-radius:6px;font-size:14px;font-weight:bold;overflow:hidden;background: #d95050;background: #d95050 url(http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px center;}
    .customoverlay .title {display:block;text-align:center;background:#fff;margin-right:35px;padding:10px 15px;font-size:14px;font-weight:bold;}
    .customoverlay:after {content:'';position:absolute;margin-left:-12px;left:50%;bottom:-12px;width:22px;height:12px;background:url('http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')} */
.emptySpace {
    height: 75px;
}
</style>