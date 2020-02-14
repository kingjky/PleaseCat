<template>
  <div class="test">
    <button @click="handleClickButton">Toggle Modal</button>
    <app-my-modal
      title="This is modal"
      :visible.sync="visible">
      <div>
        This is modal body
      </div>
    </app-my-modal>
    <div>
      sddd
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions} from 'vuex'
import myModal from './TestModalTest'
export default {
  name: 'parent',
  data(){
    return {
      visible: false
    }
  },
  components: {
    appMyModal: myModal
  },
  methods: {
    handleClickButton(){
      this.visible = !this.visible
    } 
  }
}
</script>
<style lang="scss" scoped>
.test {
  padding-top: 120px;
}
</style>