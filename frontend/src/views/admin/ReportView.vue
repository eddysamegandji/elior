<script setup lang="ts">
import {onMounted} from 'vue'
let months = [];
let date = new Date();
onMounted( async () => {
  for(let i = 0; i < 12; i++){
    months.push(date.toLocaleString('fr-FR', { year: 'numeric', month: 'numeric' }));
    date.setMonth(date.getMonth() - 1);
  }
  console.log(months);
})

</script>

<template>
  <div></div>

</template>

<style scoped>

</style>