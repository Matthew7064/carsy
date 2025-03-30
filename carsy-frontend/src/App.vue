<script setup>
import { RouterView } from 'vue-router';
</script>

<template>
  <nav class="bg-blue-500 text-white p-4 flex justify-between">
    <router-link to="/" class="text-lg">Car Management</router-link>
  </nav>
  <router-view />
</template>

<style>
/*
#app {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  grid-template-rows: repeat(12, 1fr);
  width: 100%;
  height: 100%;
}

@media (min-width: 640px) {
  #app {
    grid-template-columns: repeat(2, 96px) repeat(10, 1fr);
  }
}
 */
</style>
