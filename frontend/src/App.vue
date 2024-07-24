<template>
  <v-app class="flex flex-col min-h-screen">
    <TopNavBar />
    <div class="flex min-h-full flex-col justify-center" :class="{ marginTopBar: showNavBar }">
      <v-main>
        <router-view />
      </v-main>
    </div>

  </v-app>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import TopNavBar from './components/TopNavBar.vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const showNavBar = computed(() => {
  const { hideNavBar } = route.meta
  return !hideNavBar
});

</script>

<style>
.marginTopBar {
  margin-top: 50px !important;
}
</style>
