<script setup lang="ts">
import { computed, onBeforeMount, ref, watch } from 'vue'
import HTTP from '@/services/axios'
import Product from "@/models/product"

const products = ref<Product[]>([])
const dialog = ref(false)
const dialogDelete = ref(false)
let editedIndex = -1
let defaultItem = new Product(null, '', 0)
let editedItem = ref(Object.assign({}, defaultItem))
let deleteItemId = -1

const formTitle = computed(() => (editedItem.value.id === null ? 'Nouveau produit' : 'Mise à jour du produit'))
let sortBy = [{ key: 'name', order: 'asc' }]

watch(dialog, (val) => {
  console.log("valll", val)
  if (!val) close()
})
watch(dialogDelete, (val) => {
  if (!val) closeDelete()
})
const headers = [
  { title: "Dénomination", key: "name" },
  { title: "Prix d'achat unitaire", key: "unitPurchasePrice" },
  { title: 'Actions', key: 'actions', sortable: false },
]

const getProducts = async () => {
  try {
    const response = await HTTP.get("product")
    products.value = response.data
  } catch (error) {
    console.error("Erreur lors de la récupération des produits", error)
  }
}

onBeforeMount(async () => {
  await getProducts()
})

function editItem(item: any) {
  editedIndex = products.value.indexOf(item)
  editedItem.value = Object.assign({}, item)
  dialog.value = true
}

function deleteItem(itemId: any) {
  deleteItemId = itemId
  dialogDelete.value = true
}

async function deleteItemConfirm() {
  try {
    await HTTP.delete("product/" + deleteItemId)
  } catch (error) {
    console.error("Erreur lors de la suppression du produit", error)
  }
  closeDelete()
}

function close() {
  dialog.value = false
  editedItem.value = Object.assign({}, defaultItem)
  editedIndex = -1
}
function closeDelete() {
  dialogDelete.value = false
  editedItem.value = Object.assign({}, defaultItem)
  editedIndex = -1
  getProducts()
}

async function save() {
  if (editedItem.value.id != null) {
    try {
      await HTTP.put("product/" + editedItem.value.id, editedItem.value)
    } catch (error) {
      console.error("Erreur lors de la mise à jour du produit", error)
    }
  } else {
    try {
      await HTTP.post("product", editedItem.value)
    } catch (error) {
      console.error("Erreur lors de la création du produit", error)
    }
  }
  getProducts()
  close()
}

</script>

<template>
  <div class="mx-10 mt-9">
    <v-data-table :sort-By="sortBy" density="compact" :items="products" :headers="headers"
      itemsPerPageText="Nombre d'entrées à afficher :" :items-per-page="20" class="productTable">
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Liste des produits</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="500px">
            <template v-slot:activator="{ props }">
              <v-btn color="primary" variant="elevated" v-bind="props">
                Ajouter un produit
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" md="6" sm="6">
                      <v-text-field v-model="editedItem.name" label="Dénomination"></v-text-field>
                    </v-col>
                    <v-col cols="12" md="5" sm="6">
                      <v-text-field v-model="editedItem.unitPurchasePrice" label="Prix d'achat unitaire"></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="red" variant="flat" @click="close()">
                  Retour
                </v-btn>
                <v-btn color="primary" variant="flat" @click="save()">
                  Valider
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-dialog v-model="dialogDelete" max-width="500px">
            <v-card>
              <v-card-title class="text-h5">Confirmer la suppression</v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" variant="flat" @click="closeDelete">Abandonner</v-btn>
                <v-btn color="red" variant="flat" @click="deleteItemConfirm">Confirmer</v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon class="me-3" size="large" color="primary" @click="editItem(item)">
          mdi-pencil
        </v-icon>
        <v-icon size="large" color="red" @click="deleteItem(item.id)">
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
  </div>
</template>

<style></style>