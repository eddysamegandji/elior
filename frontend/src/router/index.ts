import DailySalesView from '@/views/DailySalesView.vue'
import AccountingListView from '@/views/AccountingListView.vue'
import ProductListView from '@/views/admin/ProductListView.vue'
import { createRouter, createWebHashHistory } from 'vue-router'
import ReportView from '@/views/admin/ReportView.vue'

const router = createRouter({
  history: createWebHashHistory('/'),
  routes: [
    {
      path: '/',
      name: 'dailySales',
      component: DailySalesView
    },
    {
      path: '/edit-dailySales/:id',
      name: 'editDailySales',
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
    },
    {
      path: '/reports',
      name: 'reports',
      component: ReportView
    }
  ]
})

export default router
