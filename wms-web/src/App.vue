<template>
  <div id="app">
     <router-view/>
  </div>
</template>

<script>


export default {
  name: 'App',
  components: {

  },
  created(){
    this.$store.commit("setMenu",JSON.parse(sessionStorage.getItem("CurMenu")))
  }
}
</script>

<style>
#app {
    height: 100%;
}
</style>
