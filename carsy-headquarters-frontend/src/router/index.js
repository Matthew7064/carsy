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
			path: '/login',
			name: 'login',
			component: () => import('../views/LoginView.vue'),
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
					path: 'cars',
					name: 'dashboard cars',
					component: () => import('../views/DashboardCarsView.vue'),
				},
				{
					path: 'rents',
					name: 'dashboard rents',
					component: () => import('../views/DashboardRentsView.vue'),
				},
				{
					path: 'users',
					name: 'dashboard users',
					component: () => import('../views/DashboardUsersView.vue'),
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
