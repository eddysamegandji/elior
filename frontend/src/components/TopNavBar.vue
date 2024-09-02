<script setup lang="ts">
import { ref } from 'vue'


const drawer = ref(false)

const toolbarItems = ref([
    { text: 'Livre de comptes', to: '/accountingList' },
    { text: 'Ajouter une nouvelle soirée de vente', to: '/' }
])

const admins = ref([
    { text: 'Produits', icon: 'mdi-liquor', to: '/products' },
  { text: 'Rapports', icon: 'mdi-file-export', to: '/reports' }
])

</script>

<template>
    <v-app-bar app dark dense height="48" class="topbar">
        <v-toolbar dense color="white">
            <v-toolbar-items dense>
                <img src="/logo.jfif" alt="Logo" class="logo" style="margin-right: 10px;" />
                <v-btn class="btn-menu" size="small" @click="drawer = false" v-for="item in toolbarItems"
                    :key="item.text" :to="item.to">
                    {{ item.text }}
                </v-btn>
                <v-btn class="btn-menu" size="small" @click="drawer = !drawer" :class="{ 'v-btn--active': drawer }">
                    Administration
                </v-btn>
            </v-toolbar-items>
            <v-spacer></v-spacer>

        </v-toolbar>
    </v-app-bar>
    <v-navigation-drawer v-model="drawer" class="leftMenu">
        <v-list class="pt-6 pl-2" density="compact">
            <v-list-item v-for="(item, i) in admins" :key="i" :value="item" :to="item.to" color="primary">
                <template v-slot:prepend>
                    <v-icon :icon="item.icon"></v-icon>
                </template>

                <v-list-item-title>{{ item.text }}</v-list-item-title>
            </v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>