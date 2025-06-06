import { createRouter, createWebHistory } from 'vue-router';
import CarList from "../views/CarList.vue";
import Cars from "../views/Cars.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {path: '/cars', component: Cars},
        { path: "/list", component: CarList },
    ],
});

export default router;
