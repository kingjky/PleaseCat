import Vue from 'vue'
import VueRouter from 'vue-router'
import Test from '../components/Test.vue'
import Login from '../components/member/login/PageLogin'
import SignUp from '../components/member/signup/PageSignUp'
import Home from '../components/newsfeed/Home.vue'
import NewsFeed from '../components/newsfeed/NewsFeed.vue'
import PageCatList from '../components/catList/PageCatList.vue'
import PageCatProfile from '../components/catProfile/PageCatProfile.vue'
import SelectPhoto from '../components/post/addPost/PageSelectPhoto'
import PageCatDetail from '@/components/catProfile/PageCatDetail.vue'
import PageAddCat from '../components/post/addCat/PageAddCat'
import PageNearMap from '@/components/map/PageNearMap'
import PageMyProfile from '@/components/userProfile/PageMyProfile'
import PageUserProfile from '@/components/userProfile/PageUserProfile'

Vue.use(VueRouter)

const routes = [
  {
    path: '/test',
    name: 'Test',
    component: Test
  },
  {
    path: '/',
    name: 'NewsFeed',
    component: NewsFeed
  },
  {
    path: '/Home',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signUp',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/nearMap',
    name: 'PageNearMap',
    component: PageNearMap
  },
  {
    path: '/catList',
    name: 'PageCatList',
    component: PageCatList
  },
  {
    path: '/catProfile/:cat_no',
    name: 'PageCatProfile',
    component: PageCatProfile
  },
  // {
  //   path: '/addPost',
  //   name: 'SelectPhoto',
  //   component: SelectPhoto
  // },
  {
    path: '/catDetail/:cat_no',
    name: 'PageCatDetail',
    component: PageCatDetail
  },
  {
    path: '/addPost',
    name: 'PageAddCat',
    component: PageAddCat
  },
  {
    path: '/myProfile',
    name: 'PageMyProfile',
    component: PageMyProfile
  },
  {
    path: '/userProfile/:user_no',
    name: 'PageUserProfile',
    component: PageUserProfile
  },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../components/about/About.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
