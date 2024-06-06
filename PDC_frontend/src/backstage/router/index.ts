import { createRouter, createWebHashHistory, createWebHistory, RouteRecordRaw } from 'vue-router'


const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        component: () => import('../views/Charts.vue')
    },
    {
        path: '/manage',
        component: () => import('../views/Manage.vue')
    },
    {
        path: '/edit',
        component: () => import('../views/EditContest.vue')
    },
    {
        path: '/setAward',
        component: () => import('../views/SetAward.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router