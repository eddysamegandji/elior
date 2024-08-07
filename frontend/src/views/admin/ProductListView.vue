<script setup lang="ts">
import { onMounted, ref } from 'vue'
import HTTP from '@/services/axios'

const products = ref([])
const currentItem = ref(null)
const dialog = ref(false)
const operation = ref('')

const headers = [
    { title: "Dénomination", key: "name" },
    { title: "Prix d'achat unitaire", key: "unitPurchasePrice" },
    { title: 'Actions', key: 'actions' },
]

const getProducts = async () => {
    try {
        const response = await HTTP.get("product")
        products.value = response.data
    } catch (error) {
        console.error("Erreur lors de la récupération des produits", error)
    }
}

onMounted(async () => {
    getProducts()
})

let sortBy = [{ key: 'name', order: 'asc' }]

const getConfirmation = (item: any, op: string) => {
    currentItem.value = item
    operation.value = op
    dialog.value = true
}

const confirmAction = async () => {
    if (currentItem.value) {
        if (operation.value === 'delete') {
            try {
                await HTTP.delete("product/" + currentItem.value['id'])
            } catch (error) {
                console.error("Erreur lors de la suppression du produit", error)
            }
        } else if (operation.value === 'edit') {
            try {
                await HTTP.put("product/" + currentItem.value['id'], currentItem.value)
            } catch (error) {
                console.error("Erreur lors de la mise à jour du produit", error)
            }
        }
    }
    getProducts()
    dialog.value = false
}

</script>

<template>
    <div class="mx-5 mt-4">
        <v-row>
            <v-col cols="3"></v-col>
            <v-col>
                <h1>Liste des produits</h1>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="3"></v-col>
            <v-col cols="6">
                <v-data-table :sortBy="sortBy" density="compact" :items="products" :headers="headers"
                    itemsPerPageText="Nombre d'entrées à afficher :" class="productTable">
                    <template v-slot:item.unitPurchasePrice="{ item }">
                        <v-text-field v-model="item.unitPurchasePrice" class="filter" variant="outlined"
                            density="compact" @change="getConfirmation(item, 'edit')">
                        </v-text-field>
                    </template>
                    <template v-slot:item.actions="{ item }">
                        <div class="noWrap">
                            <v-icon icon="bi bi-trash" color="red" @click="getConfirmation(item, 'delete')"></v-icon>
                        </div>
                    </template>
                </v-data-table>
            </v-col>
        </v-row>
    </div>
    <v-dialog v-model="dialog" max-width="400" persistent>
        <v-card>
            <v-card-title class="headline">Confirmation</v-card-title>
            <v-card-text> Êtes-vous sûr de vouloir enregistrer les modifications ? </v-card-text>
            <v-card-actions>
                <v-btn color="primary" @click="confirmAction()">{{ operation == 'delete' ?
                    'Supprimer'
                    :
                    'Confirmer'
                    }}</v-btn>
                <v-btn @click="dialog = false; getProducts()">Annuler</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<style></style>