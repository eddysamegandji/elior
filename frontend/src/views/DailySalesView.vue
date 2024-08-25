<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Product from '@/models/product'
import Article from '@/models/article'
import Accounting from '@/models/accounting'
import Expense from '@/models/expense'
import HTTP from '@/services/axios'
import { useRouter } from 'vue-router'

const router = useRouter();
const articles = ref(Array(2).fill(null).map(() => new Article(null, 0, 0, 0, 0, 0, null, new Product(null, '', 0), null)))
const expenses = ref([new Expense(null, '', 0, null)])
const accounting = ref(new Accounting(null, '', null, '', 0, 0, 0, 0, articles.value, expenses.value))
const products = ref([new Product(null, '', 0)])

const addArticle = () => {
    articles.value.push(new Article(null, 0, 0, 0, 0, 0, null, new Product(null, '', 0), null))
}

const deleteArticle = (index: number) => {
    articles.value.splice(index, 1)
}

const addExpense = () => {
    expenses.value.push(new Expense(null, '', 0, null))
}

const deleteExpense = (index: number) => {
    expenses.value.splice(index, 1)
}

onMounted(async () => {
    try {
        const response = await HTTP.get("product")
        products.value = response.data
    } catch (error) {
        console.error("Erreur lors de la récupération des produits", error)
    }
})

const submitForm = async () => {
    try {
      const response = await HTTP.post('accounting', accounting.value)
      console.log("Accounting created : ", response.data)
      await router.push({name: 'accountingList'});
    } catch (error) {
        console.log("Erreur lors de la sauvégarde, revérifiez vos données.")
    }
}
const numbers = ref(Array.from({ length: 101 }, (_, i) => i))

</script>

<template>
    <div class="pa-15">
        <form @submit.prevent="submitForm">
            <v-row>
                <v-col>
                    <v-text-field v-model="accounting.eventName" label="Nom de la soirée" />
                </v-col>
                <v-col>
                    <v-text-field v-model="accounting.employeeName" label="Employé" required />
                </v-col>
                <v-col>
                    <v-text-field v-model="accounting.flooz" label="Flooz" />
                </v-col>
                <v-col>
                    <v-text-field v-model="accounting.tmoney" label="TMoney" />
                </v-col>
                <v-col>
                    <v-text-field v-model="accounting.unpaid" label="Impayés" />
                </v-col>
                <v-col>
                    <v-text-field v-model="accounting.organizerPercent" label="Pourcentage organisateur" />
                </v-col>
                  <span style="margin-top: 25px">Date</span>
                <v-col style="margin-top: 10px">
                    <VueDatePicker v-model="accounting.eventDate" format="dd/MM/yyyy" required
                        :enable-time-picker="false" auto-apply />
                </v-col>
            </v-row>
            <v-row v-for="(article, index) in articles" :key="index">
                <v-col>
                    <v-autocomplete :items="products" item-title="name" item-value="id" v-model="article.product" required
                        return-object label="Nom de l'article" />
                </v-col>
                <v-col>
                    <v-select :items="numbers" v-model="article.quantity" label="Quantité" type="number" min="1" />
                </v-col>
                <v-col>
                    <v-text-field v-model="article.product.unitPurchasePrice" label="Prix d'achat unitaire" />
                </v-col>
                <v-col>
                    <v-text-field v-model="article.sellingPrice" label="Prix de vente" type="number" min="1"/>
                </v-col>
                <v-col style="margin: auto;">
                    <v-btn color="#e01517" rounded="xl" @click.prevent="deleteArticle(index)">Supprimer</v-btn>
                </v-col>
            </v-row>
            <v-row>
                <v-col>
                    <v-btn color="primary" rounded="xl" style="margin-right: 30px;" @click.prevent="addArticle">
                        + ajouter un article
                    </v-btn>
                </v-col>
            </v-row>
            <v-row v-for="(expense, index) in expenses" :key="index">
                <v-col>
                    <v-text-field v-model="expense.denomination" label="Type de dépense" required />
                </v-col>
                <v-col>
                    <v-text-field v-model="expense.price" label="Prix dépense" type="number" min="1" />
                </v-col>
                <v-col style="margin: auto;">
                    <v-btn color="#e01517" rounded="xl" @click.prevent="deleteExpense(index)">Supprimer</v-btn>
                </v-col>
            </v-row>
            <v-row>
                <v-col>
                    <v-btn color="primary" rounded="xl" style="margin-right: 30px;" @click.prevent="addExpense">
                        + ajouter une dépense
                    </v-btn>
                </v-col>
            </v-row>
            <v-row>
                <v-col>
                    <v-btn type="submit" color="success" rounded="xl">Valider</v-btn>
                </v-col>
            </v-row>

        </form>
    </div>
</template>
