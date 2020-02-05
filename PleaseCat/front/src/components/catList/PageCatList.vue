<template>
    <div id="catList">
        <div class="emptySpace">-Navigation Bar-</div>
        <!-- <CatCardComponent key="1" name="name" desc1="♀" desc2="loca" src="1" /> -->
        <CatCardComponent v-for="cat in cats" :key=cat.cat_no :name=cat.cat_name :desc1="cat.sex==='남'?'♂':'♀'" :desc2=cat.cat_location :src=cat.cat_no  />
        <div class="emptySpace">-Tab Bar-</div>
    </div>
</template>
<script>
import CatCardComponent from './catCard/CatCard'
import axios from 'axios'

export default {
    created() {
        this.server = this.$store.state.server;
        this.pullCat();
        console.log(this.url);
    },
    data() {
      return {
          server: '',
          cats: [],
      }
    },
    components: {
        CatCardComponent,
    },
    methods: {
        pullCat(){
            const vm = this;
            axios
                .get(`${vm.server}/api/cat/searchAll`)
                .then(res => {
                    // handle success
                    vm.cats = res.data.data
                    res.data.data.forEach(el => {
                        
                    });
                })
                .catch(err =>  {
                    // handle error
                })
                .then(() => {
                    // always executed
                    console.log(vm.cats);
                });
        }
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