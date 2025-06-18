<script>
export default {
  props: {
    car: { type: Object, required: true },
    res: { type: String, default: 'thumb' },
  },
  data() {
    return { url: null };
  },
  created() {
    // Placeholder image; replace with local assets if available
    this.url = 'https://www.nicepng.com/png/detail/777-7772737_car-placeholder-image-lamborghini-gallardo.png';
  },
};
</script>
<template>
	<div v-if="!url" class="bg-zinc-600 animate-pulse"></div>
	<img v-else :src="url" :alt="`${car.brand} ${car.model}`" />
</template>
