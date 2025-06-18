<script>
import { RouterLink } from 'vue-router';

export default {
	components: {
		RouterLink,
	},
	props: {
		href: {
			type: String,
			default: '/',
			required: true,
		},
	},
};
</script>

<template>
  <RouterLink :to="href">
    <li class="flex items-center justify-center relative group">
      <img
          src="@/assets/logo.png"
          alt="Logo"
          class="w-full h-full m-6 text-white group-hover:text-red-500 transition-colors duration-200"
      />
    </li>
  </RouterLink>
</template>
