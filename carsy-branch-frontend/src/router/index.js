import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
			path: '/',
			name: 'home',
			component: HomeView,
		},
		{
			path: '/cars',
			name: 'cars',
			component: () => import('../views/CarsView.vue'),
		},
		{
			path: '/cars/:id',
			name: 'car',
			component: () => import('../views/CarView.vue'),
		},
		{
			path: '/rent/:id',
			name: 'rent',
			component: () => import('../views/RentView.vue'),
		},
		{
			path: '/login',
			name: 'login',
			component: () => import('../views/LoginView.vue'),
		},
		{
			path: '/register',
			name: 'register',
			component: () => import('../views/RegisterView.vue'),
		},
		{
			path: '/account',
			name: 'account',
			component: () => import('../views/AccountView.vue'),
		},
		{
			path: '/dashboard',
			name: 'dashboard',
			component: () => import('../views/DashboardView.vue'),
			children: [
				{
					path: 'rents',
					name: 'dashboard rents',
					component: () => import('../views/DashboardRentsView.vue'),
				},
				{
					path: 'map',
					name: 'dashboard map',
					component: () => import('../views/DashboardMapView.vue'),
				},
			],
		},
	],
});

export default router;
