import { createRouter, createWebHistory } from 'vue-router';
import CarList from "../views/CarList.vue";
import CarForm from "../views/CarForm.vue";
import Cars from "../views/Cars.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {path: '/', component: Cars},
        { path: "/list", component: CarList },
        { path: "/add", component: CarForm },
        { path: "/edit/:id", component: CarForm, props: true },
    ],
});

export default router;
