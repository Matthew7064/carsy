<script>
  import { RouterLink } from 'vue-router';
  import CarImage from '../components/CarImage.vue';
  import CarMainStatsItem from '../components/CarMainStatsItem.vue';
  import IconEngine from '../components/icons/IconEngine.vue';
  import IconHP from '../components/icons/IconHP.vue';
  import IconSpeed from '../components/icons/IconSpeed.vue';

  export default {
  components: {CarMainStatsItem,
  IconEngine,
  IconHP,
  IconSpeed,
  RouterLink,
  CarImage,},
  computed: {},
  created() {
},
  methods: {
},
};
</script>

<template>
	<main
		class="col-start-1 sm:col-start-2 lg:col-start-3 col-end-13 xl:col-end-9 row-start-3 lg:row-start-3 row-end-[12] flex flex-col justify-center items-center sm:items-start lg:pl-0 p-8 lg:p-4 text-white"
	>
    <Teleport to="body">
      <div
          class="fixed inset-0 bg-black -z-20 car__bg xl:after:hidden overflow-hidden"
      >
        <img
            src="@/assets/background.jpg"
            alt="Background"
            class="absolute inset-0 w-full h-full object-cover object-center xl:opacity-40 xl:blur-[1.25px] xl:grayscale-[60%] pointer-events-none"
        />
      </div>
    </Teleport>
		<div class="flex flex-col items-center sm:items-start">
			<span
				class="text-2xl lg:text-5xl ml-0 sm:ml-4 lg:leading-tight text-center"
				>Welcome to the Car Rental Management System</span
			>
		</div>
	</main>
</template>
