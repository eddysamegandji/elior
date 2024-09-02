<script setup lang="ts">
import { ref, watch } from 'vue'
import HTTP from '@/services/axios'
import { useRouter } from 'vue-router'
import type { AxiosRequestConfig } from 'axios'

const router = useRouter()
const dialog = ref(false)
let accountings = ref<any[]>([])
let dateRange = ref([])
let editedId = ref(-1)
let startDate: AxiosRequestConfig<any> | undefined
let endDate: AxiosRequestConfig<any> | undefined
let loading = ref(false)

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
      startDate = newRange[0]
      endDate = newRange[1]
      url = `accountings?startDate=${startDate}&endDate=${endDate}`
    } else {
      url = 'accounting'
    }
    const response = await HTTP.get(url)
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

function editItem(id: number) {
  editedId.value = id
  router.push({ path: `edit-dailySales/${id}` })
}

async function exportData() {
  try {
    const response = await HTTP.get('report', { params: { startDate, endDate }, responseType: 'blob' })
    downloadData(response)
  } catch (error) {
    console.error("Erreur lors du téléchargement du fichier", error)
  }
}

function downloadData(response: any) {
  loading.value = true
  let fileURL = window.URL.createObjectURL(new Blob([response.data]))
  let fileLink = document.createElement('a')
  fileLink.href = fileURL
  const filename = response.headers.get('content-disposition').split('filename=')[1].replace(/"/g, "")
  fileLink.setAttribute('download', filename)
  document.body.appendChild(fileLink)
  fileLink.click()
  fileLink.remove()
  setTimeout(() => (loading.value = false), 300)
}

let sortBy = [{ key: 'eventDate', order: 'asc' }]

</script>

<template>
  <div class="pa-10">
    <v-row>
      <v-col>
        <h1>Liste des comptes</h1>
      </v-col>
      <v-col cols="4"></v-col>
      <v-col cols="3">
        <VueDatePicker v-model="dateRange" range model-type="yyyy-MM-dd" format="dd/MM/yyyy" :enable-time-picker="false"
          auto-apply />
      </v-col>
      <v-col align-end>
        <v-icon icon="mdi mdi-file-download-outline" style="margin: 10px;" size="large" color="primary"
          @click="exportData()"></v-icon>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-data-table :sort-By="sortBy" :headers="headers" :items="accountings"
          itemsPerPageText="Nombre d'entrées à afficher :" items-per-page="" class="accountingTable">
          <template v-slot:item.eventDate="{ item }">
            <v-chip color="black">
              {{ formatDate(item.eventDate) }}
            </v-chip>
          </template>
          <template v-slot:item.totalExpense="{ item }">
            <v-chip color="red">
              {{ item.totalExpense }}
            </v-chip>
          </template>
          <template v-slot:item.realBenefit="{ item }">
            <v-chip color="#228B22">
              {{ item.realBenefit }}
            </v-chip>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-icon icon="mdi-pencil" class="me-3" size="large" color="primary" @click="editItem(item.id)"></v-icon>
            <v-icon icon="mdi-delete" size="large" color="red" @click="editItem(item.id)"></v-icon>
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