<template>
  <v-app class="flex flex-col min-h-screen">
    <TopNavBar v-if="showNavBar" />
    <div class="flex min-h-full flex-col justify-center" :class="{ menuOpen: openedMenu, marginTopBar: showNavBar }">
      <v-main>
        <router-view />
      </v-main>
    </div>

  </v-app>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import TopNavBar from './components/TopNavBar.vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const openedMenu = ref(false)

const showNavBar = computed(() => {
  const { hideNavBar } = route.meta
  return !hideNavBar
})


</script>

<style></style>
