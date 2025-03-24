import { createRouter, createWebHistory } from 'vue-router';
import Cars from '../views/Cars.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: Cars,
        },
    ],
});

export default router;
