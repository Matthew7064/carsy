<script>
import { RouterLink } from 'vue-router';
import ButtonPrimary from '../components/ButtonPrimary.vue';
import { mockAuth } from '../api.js';

export default {
  components: { RouterLink, ButtonPrimary },
  data() {
    return { email: '', password: '' };
  },
  methods: {
    async signInUser() {
      try {
        await mockAuth.signIn(this.email, this.password);
        this.$router.push('/');
      } catch (error) {
        alert(error.message);
      }
    },
  },
};
</script>

<template>
	<main
		class="col-start-1 sm:col-start-2 lg:col-start-3 col-end-13 row-start-2 row-end-[13] flex flex-col items-center justify-center overflow-y-auto lg:pl-0 p-4 text-white bg-zinc-700"
	>
		<div class="flex flex-col w-full max-w-md">
			<h1 class="my-6 text-4xl font-semibold text-center md:text-left">
				Login with your account
			</h1>
			<label for="email">Login</label>
			<input
				id="email"
				v-model="email"
				type="text"
				name="email"
				class="mt-1 mb-2 px-4 py-2 rounded-md bg-zinc-800 outline-none border border-zinc-600 focus:border-red-500 focus:ring focus:ring-red-500/50 text-lg"
			/>
			<label for="password">Password</label>
			<input
				id="password"
				v-model="password"
				type="password"
				name="password"
				class="mt-1 mb-2 px-4 py-2 rounded-md bg-zinc-800 outline-none border border-zinc-600 focus:border-red-500 focus:ring focus:ring-red-500/50 text-lg"
			/>
			<ButtonPrimary class="my-2" width="full" @click="signInUser"
				>Log In</ButtonPrimary
			>
			<p>
				Don't have an account?
				<RouterLink to="/register" class="text-red-500 font-bold"
					>Join now!</RouterLink
				>
			</p>
		</div>
	</main>
</template>

<style scoped></style>
