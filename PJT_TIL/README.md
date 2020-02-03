# PJT

- 소재 : 길고양이 관리를 위한 SNS

## 다음 링크로 접속하시면 고양이를 부탁캣 웹앱 프로그램을 직접 시현 가능합니다.

- http://70.12.246.120:8080/
- Map 탭의 경우 보안상의 문제로 로컬에서 직접 다운로드 받으신 뒤 작동하시면 시현이 가능합니다.  
  ![2](..\image\2.png)
  - image 폴더 내부에 사진이 위치
  - 아래와 같은 화면에서 보시다시피 현재 나의 위치가 정중앙에 표시되어 있고 내 주변 반경 500m이내의 고양이들이 지도상에 표시됩니다.
  - 고양이 이름 옆의 버튼을 누르면 고양이 리스트 페이지로 이동합니다.

## 기능별 세부 사항

- NewsFeedPage
  - 기능
    - 뉴스피드 무한 스크롤 페이징
  - 구현내용
    - DB 데이터 동시 출력시 발생하는 시간 지연을 방지하기 위한 순차적 데이터 출력	- 기능
    - 뉴스피드 내용 출력
  - 구현내용
    - UI/UX 설계
    - DB설계
    - sql 쿼리문 작성
    - DB내용 출력
- PageLogin
  - 기능
    - axios.get 비동기 http 통신 로그인 기능
    - data (user_email, user_pw)
  - 구현내용
    - css 작성
- PageSignup
  - 기능
    - axios.post 비동기 http 통신 회원가입 기능
    - data (user_id, user_pw, user_email)
  - 구현내용
    - css 작성
- Tabbar
  - 기능
    - 메인 페이지에서 주요 기능 5가지 (뉴스피드, 근처, 게시글 작성, 검색, 타임라인)에 빠르게 접근 가능한 탭 바를 하단에 만듬
    - 각 탭 클릭 시 router path 넘어가도록 설계
  - 구현내용
    - vuetify 이용 탭 바 컴포넌트 구현
- CatListPage
  - 기능
    - Axios 통신을 통해 DB의 고양이들의 정보를 읽어와 Card  형태의 Component 로 출력
  - 구현내용
    - Pure CSS로 구현된 CatCard Component
    - hover 효과 적용
    - 반응형 페이지, 목록에 따라 Scroll
- CatProfilePage
  - 기능
    - CatList에서 선택된 고양이의 상세 정보를 DB에서 읽어와 출력
  - 구현내용
    - Pure CSS로 구현된 CatProfile
    - 반응형 페이지
- CatMapPage
  - 기능
    - Map tab 버튼을 누르면 내 주변의 반경 500m의 고양이들이 표시된 지도가 component로 출력
  - 구현내용
    - Kakao Map API를 통해 실제 내 위치와 고양이 위치를 설정하여 지도 출력
    - 고양이의 위치와 이름 정보를 읽어와 출력
    - 고양이의 이름을 누르면 해당 고양이의 리스트 페이지로 이동

# PJT_Front

## Front 과정

- 기능별 Branch 에서 개발 완성 후 상위 Branch 에 적용하는 방법


- ex) 현재 작업 Branch가 "front_bar" 일 경우

1. 로컬 Branch 에서의 수정사항을 Commit 후 HEAD를 상위 Branch로 이동
   - HEAD: front_bar
   - git add -> git commit -> git checkout front
2. 상위 Branch의 현재까지 최종본을 Pull 한 후 다시 하위 기능 Branch로 이동
   - HEAD: front
   - git pull -> git checkout front_bar
3. 상위 Branch의 최종본을 나의 현재 작업 Branch에 Merge시킨 후 Remote의 나의 작업 Branch 로 Push
   - HEAD: front_bar
   - git merge front -> git push origin front_bar
4. https://lab.ssafy.com/ 에서 merge request 하기
   - [Create merge request]
   - Merge의 목적지의 default 값이 master 로 설정되어 있으므로 목적지를 상위 Branch인 front로 수정한다
     - [Change branches]
   - Merge하는 이유, 즉 업데이트 내용(추가 생성 및 수정 사항들)을 작성
   - [Submit merge request]
   - Maintainer 의 승인을 기다린다

## 프로젝트 과정

- 2020-01-20-Mon

  - 주제 구체화 진행
  - 서비스 UI 작성 & 와이어프레임 구성 시작
  - 필요한 기능 세부 정리

- 2020-01-21-Tue

  - ERD작성
  - 서비스 와이어 프레임 작성
  - Git version 관리 연습 및 Git 버젼 관리 규칙 공유


- 2020-01-22 Wed

- 공통
  - Front 개발환경 구축 및 Git 업로드
- front_list
  - 저는 list입니다