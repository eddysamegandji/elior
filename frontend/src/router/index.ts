import DailySalesView from '@/views/DailySalesView.vue'
import AccountingListView from '@/views/AccountingListView.vue'
import ProductListView from '@/views/admin/ProductListView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'dailySales',
      component: DailySalesView
    },
    {
      path: '/accountingList',
      name: 'accountingList',
      component: AccountingListView
    },
    {
      path: '/products',
      name: 'products',
      component: ProductListView
    }
  ]
})

export default router
