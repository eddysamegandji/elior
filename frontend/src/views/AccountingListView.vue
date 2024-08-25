<script setup lang="ts">
import { ref, watch } from 'vue'
import HTTP from '@/services/axios'

const dialog = ref(false)
let accountings = ref<any[]>([])
let dateRange = ref([])

const headers = [
    { title: "Date", key: 'eventDate' },
    { title: "Nom de l'evenement", key: 'eventName' },
    { title: "Nom de l'employé", key: 'employeeName' },
    { title: "Dépense pure", key: 'pureExpense' },
    { title: "Part Organisateur soirée", key: 'eventOrganizerCommission' },
    { title: "Prix d'achat total", key: 'totalPurcharsePrice' },
    { title: "Prix de vente total", key: 'totalSellingPrice' },
    { title: "Marge (Bénéfice brut)", key: 'totalBenefit' },
    { title: "Dépense totale (Dépense pure + part organisateur)", key: 'totalExpense' },
    { title: "Bénéfice réel", key: 'realBenefit' },
    { title: 'Actions', key: 'actions' },
]

watch(dateRange, async (newRange) => {
  try {
    let url
    if (newRange && newRange.length === 2) {
      url = `accountings?startDate=${newRange[0]}&endDate=${newRange[1]}`
    } else {
      url = 'accounting'
    }

    const response = await HTTP.get(url);
    accountings.value = Array.isArray(response.data) ? response.data : []
  } catch (error) {
    console.error("Erreur lors de la récupération des comptes", error)
  }
}, { immediate: true })

function formatDate(dateTime: string | number | Date) {
    const date = new Date(dateTime)
    const days = ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi']
    const dayOfWeek = days[date.getUTCDay()]
    return `${dayOfWeek}, ${date.toLocaleDateString()}`
}
let sortBy = [{ key: 'eventDate', order: 'asc' }]

</script>

<template>
    <div class="pa-10">
      <v-row>
        <v-col>
          <h1>Liste des comptes</h1>
        </v-col>
        <v-col cols="3">
          <VueDatePicker v-model="dateRange" range model-type="yyyy-MM-dd" format="dd/MM/yyyy" :enable-time-picker="false" auto-apply />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-data-table v-model:sort-by="sortBy" :headers="headers" :items="accountings"
              itemsPerPageText="Nombre d'entrées à afficher :" class="accountingTable">
              <template v-slot:item.eventDate="{ item }">
                  <v-chip color="black">
                      {{ formatDate(item.eventDate) }}
                  </v-chip>
              </template>
              <template v-slot:item.totalExpense="{ item }">
                  <v-chip color="error">
                      {{ item.totalExpense }}
                  </v-chip>
              </template>
              <template v-slot:item.realBenefit="{ item }">
                  <v-chip color="success">
                      {{ item.realBenefit }}
                  </v-chip>
              </template>
              <template v-slot:item.actions="{ item }">
                  <div class="noWrap">
                      <v-icon icon="bi bi-pencil-square" color="primary" style="margin-right: 15px;"
                          click="editItem(item.id)"></v-icon>
                      <v-icon icon="bi bi-trash" color="red" @click="editItem(item.id)"></v-icon>
                  </div>
              </template>
          </v-data-table>
        </v-col>
      </v-row>
    </div>
    <v-dialog v-model="dialog" max-width="400" persistent>
        <v-card>
            <v-card-title class="headline">Confirmation</v-card-title>
            <v-card-text> Confirmer l'opération ? </v-card-text>
            <v-card-actions>
                <v-btn color="primary" @click="changeStatus">Confirmer</v-btn>
                <v-btn @click="closeDialog">Annuler</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>