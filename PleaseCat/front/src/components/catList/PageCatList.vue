<template>
    <div id="catList">
        <div class="emptySpace">-Navigation Bar-</div>
        <h1><router-link :to="'/nearMap'">지도</router-link></h1>
        <!-- <CatCardComponent key="1" name="name" desc1="♀" desc2="loca" src="1" /> -->
        <CatCardComponent v-for="cat in catList" :key=cat.cat_no :name=cat.cat_name :desc1="cat.sex==='남'?'♂':'♀'" :desc2=cat.cat_location :src=cat.cat_no  />
        <div class="emptySpace">-Tab Bar-</div>
    </div>
</template>
<script>
import CatCardComponent from './catCard/CatCard'
import axios from 'axios'
import { mapActions, mapMutations, mapGetters } from "vuex";

export default {
    created() {
    },
    data() {
        return {
      }
    },
    components: {
        CatCardComponent,
    },
    computed: {
        ...mapGetters('storeCat',[
            'catList',
        ]),
    },
    methods: {
        ...mapActions('storeCat',[
            'getCatList',
        ]),
    }
}
</script>

<style lang="scss" scoped>
#catList {
    text-align: center;
    .emptySpace {
        // display: block;
        height: 100px;
        text-align: center;
    }
}
</style>