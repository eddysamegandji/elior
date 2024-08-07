<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Product from '@/models/product'
import Article from '@/models/article'
import Accounting from '@/models/accounting'
import Expense from '@/models/expense'
import HTTP from '@/services/axios'
import { validate } from "@vuelidate/core"
import { required, helpers } from "@vuelidate/validators"

const validation = ref({
    accounting: {
        eventName: { required },
        employeeName: { required },
        flooz: { required, numeric: helpers.and(helpers.integer, helpers.min(0)) },
        tmoney: { required, numeric: helpers.and(helpers.integer, helpers.min(0)) },
        unpaid: { required, numeric: helpers.and(helpers.integer, helpers.min(0)) },
        organizerPercent: { required, numeric: helpers.and(helpers.integer, helpers.range(0, 100)) },
        eventDate: { required }
    }
})

const v$ = validate(validation)

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
        console.log(products.value)
    } catch (error) {
        console.error("Erreur lors de la récupération des produits", error)
    }
})

const submitForm = async () => {
    try {
        console.log("before send", accounting.value)
        const response = await HTTP.post('accounting', accounting.value)
        console.log("Accounting created : ", response.data)
    } catch (error) {
        console.log("Erreur lors de la sauvégarde, revérifiez vos données.")
    }
}
const numbers = ref(Array.from({ length: 101 }, (_, i) => i))
const formatedEventDate = (date: { getDate: () => any; getMonth: () => number; getFullYear: () => any }) => {
    const day = date.getDate()
    const month = date.getMonth() + 1
    const year = date.getFullYear()

    return `${day}/${month}/${year}`
}
</script>

<template>
    <div class="pa-15">
        <form @submit.prevent="submitForm">
            <v-row>
                <v-col>
                    <v-text-field v-model="accounting.eventName" label="Nom de la soirée" />
                </v-col>
                <v-col>
                    <v-text-field v-model="accounting.employeeName" label="Employé" />
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
                <v-col>
                    <span>Date</span>
                    <VueDatePicker v-model="accounting.eventDate" :format="formatedEventDate"
                        :enable-time-picker="false" auto-apply />
                </v-col>
            </v-row>
            <v-row v-for="(article, index) in articles" :key="index">
                <v-col>
                    <v-autocomplete :items="products" item-title="name" item-value="id" v-model="article.product"
                        return-object label="Nom de l'article" />
                </v-col>
                <v-col>
                    <v-select :items="numbers" v-model="article.quantity" label="Quantité" />
                </v-col>
                <v-col>
                    <v-text-field v-model="article.product.unitPurchasePrice" label="Prix d'achat unitaire" />
                </v-col>
                <v-col>
                    <v-text-field v-model="article.sellingPrice" label="Prix de vente" />
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
                    <v-text-field v-model="expense.denomination" label="Type de dépense" />
                </v-col>
                <v-col>
                    <v-text-field v-model="expense.price" label="Prix dépense" />
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
