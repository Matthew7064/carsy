<script>
import { getUsers, updateUserRole } from '../api.js';
import { store } from '../store';

export default {
  data() {
    return {
      users: [],
      isLoading: true,
    };
  },
  created() {
    if (!store.user || store.user.role !== 'admin') {
      return this.$router.push('/login');
    }
    this.searchUsers();
  },
  methods: {
    async searchUsers() {
      this.isLoading = true;
      this.users = await getUsers();
      this.isLoading = false;
    },
    async saveRole(id, role, user) {
      await updateUserRole(id, role, user);
    },
  },
};
</script>

<template>
	<div class="flex flex-col items-center w-full h-full">
		<h2 class="text-2xl font-bold text-center">
			Dashboard - Users - {{ users.length }}
		</h2>
		<div class="w-full h-full">
			<div v-if="users.length > 0" class="overflow-x-auto">
				<table
					class="table-layout mx-auto my-2 text-sm xl:text-base w-full text-center"
				>
					<thead class="border-b border-b-red-500">
						<tr>
							<th>User Name</th>
							<th>Role</th>
							<th>Save</th>
						</tr>
					</thead>
					<tbody class="leading-none">
						<tr v-for="user in users" :key="user.id">
							<td>{{ user.name }} {{user.surname}}</td>
							<td>
								<select v-model="user.role" class="bg-zinc-700 px-4 py-2">
									<option disabled>Select role</option>
									<option value="user">user</option>
									<option value="admin">admin</option>
								</select>
							</td>
							<td>
								<button
									class="hover:text-red-500 transition-colors"
									@click="saveRole(user.id, user.role, user)"
								>
									Save
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div
				v-else-if="isLoading"
				class="col-start-1 sm:col-start-2 lg:col-start-3 col-end-13 row-start-2 row-end-[13] flex flex-col justify-center items-center p-8 bg-zinc-700"
			>
				<svg
					class="animate-spin h-24 w-24 text-white"
					xmlns="http://www.w3.org/2000/svg"
					fill="none"
					viewBox="0 0 24 24"
				>
					<circle
						class="opacity-25"
						cx="12"
						cy="12"
						r="10"
						stroke="currentColor"
						stroke-width="4"
					></circle>
					<path
						class="opacity-75"
						fill="currentColor"
						d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
					></path>
				</svg>
			</div>
			<div v-else-if="users.length === 0" class="my-4 text-xl text-center">
				No users
			</div>
		</div>
	</div>
</template>

<style scoped>
td,
th {
	padding: 1rem;
}

tbody tr:nth-child(odd) {
	background-color: rgb(39 39 42 / 0.5);
}
</style>
