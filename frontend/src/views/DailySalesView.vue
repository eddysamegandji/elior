<script setup lang="ts">
import { ref } from 'vue'
import Article from '@/models/article'
import Accounting from '@/models/accounting'
import ETextField from '@/components/fields/ETextField.vue'
import Expense from '@/models/expense'

const articles = ref(Array(2).fill(null).map(() => new Article(null, '', 0, 0, 0, 0, 0, null, null)))
const expenses = ref([new Expense(null, '', 0, null)])
const accounting = new Accounting(null, '', new Date(), '', 0, 0, 0, articles.value, expenses.value)

const addArticle = () => {
    articles.value.push(new Article(null, '', 0, 0, 0, 0, 0, null, null))
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

const submitForm = () => {
    console.log(accounting)
}
</script>

<template>
    <v-sheet width="1300" class="mx-auto center mt-20">
        <form @submit.prevent="submitForm">
            <v-row>
                <v-col>
                    <ETextField v-model="accounting.eventName" label="Nom de la soirée" />
                </v-col>
                <v-col>
                    <ETextField v-model="accounting.employeeName" label="Employé" />
                </v-col>
                <v-col>
                    <ETextField v-model="accounting.flooz" label="Flooz" />
                </v-col>
                <v-col>
                    <ETextField v-model="accounting.tmoney" label="TMoney" />
                </v-col>
                <v-col>
                    <ETextField v-model="accounting.unpaid" label="Impayés" />
                </v-col>
                <v-col>
                    <span>Date</span>
                    <VueDatePicker v-model="accounting.eventDate" :enable-time-picker="false" auto-apply />
                </v-col>
            </v-row>
            <v-row v-for="(article, index) in articles" :key="index">
                <v-col>
                    <ETextField v-model="article.name" label="Nom de l'article" />
                </v-col>
                <v-col>
                    <ETextField v-model="article.quantity" label="Quantité" />
                </v-col>
                <v-col>
                    <ETextField v-model="article.purchasePrice" label="Prix d'achat" />
                </v-col>
                <v-col>
                    <ETextField v-model="article.sellingPrice" label="Prix de vente" />
                </v-col>
                <v-col>
                    <ETextField v-model="article.discount" label="Remise" />
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
                    <ETextField v-model="expense.denomination" label="Type de dépense" />
                </v-col>
                <v-col>
                    <ETextField v-model="expense.price" label="Prix dépense" />
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
    </v-sheet>
</template>
