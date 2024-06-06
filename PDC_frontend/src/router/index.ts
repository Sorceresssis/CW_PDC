import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'


const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        component: () => import('../views/Index.vue')
    },
    {
        path: '/contests',
        component: () => import('../views/ContestList.vue')
    },
    {
        path: '/contestDetail',
        component: () => import('../views/ContestDetail.vue')
    },
    {
        path: '/auth',
        component: () => import('../views/Auth.vue')
    },
    {
        path: '/user',
        component: () => import('../views/User/User.vue'),
        children: [
            {
                path: '',
                component: () => import('../views/User/Teams.vue')
            },
            {
                path: 'awards',
                component: () => import('../views/User/AwardWork.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router