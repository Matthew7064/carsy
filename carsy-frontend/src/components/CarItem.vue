<script>
import { RouterLink } from 'vue-router';
import ButtonSecondary from './ButtonSecondary.vue';
import CarImage from './CarImage.vue';
import CarStatItem from './CarStatItem.vue';
import IconDrive from './icons/IconDrive.vue';
import IconEngine from './icons/IconEngine.vue';
import IconSpeed from './icons/IconSpeed.vue';
import IconTimer from './icons/IconTimer.vue';
import IconTransmission from './icons/IconTransmission.vue';
import IconFuel from "@/components/icons/IconFuel.vue";
export default {
  components: {
    IconFuel,
    CarStatItem,
    IconEngine,
    IconTimer,
    IconSpeed,
    IconTransmission,
    IconDrive,
    RouterLink,
    ButtonSecondary,
    CarImage,
  },
  props: {
    car: {
      type: Object,
      default: () => {},
      required: true,
    },
  },
};
</script>
<template>
  <li
    class="flex flex-col lg:flex-row bg-zinc-800 w-full item relative before:absolute before:inset-0 before:w-full before:h-full before:transition-transform before:-z-[1] before:hover:-translate-x-2 before:hover:translate-y-2 before:bg-red-500"
  >
    <CarImage
      :car="car"
      class="lg:w-80 h-80 xl:w-96 xl:h-96 lg:aspect-square object-cover"
    />
    <div class="flex flex-col justify-evenly p-4 lg:pl-8 w-full gap-4">
      <RouterLink :to="`/cars/${car.id_car}`" class="text-center lg:text-left">
				<span
          class="text-2xl lg:text-3xl xl:text-4xl font-semibold tracking-widest hover:text-red-500 transition-colors"
        >{{ car.brand }} {{ car.model }}</span
        >
      </RouterLink>
      <ul class="grid grid-cols-2 gap-8 md:grid-cols-5">
        <CarStatItem
          parameter="Engine"
          :value="`${car.eng_power}KM`"
        ><IconEngine
        /></CarStatItem>
        <CarStatItem
          parameter="Transmission"
          :value="`${
						car.transmission_type === 'MANUAL' ? 'Manual' : 'Automatic'
					}`"
        ><IconTransmission
        /></CarStatItem>
        <CarStatItem
            parameter="Fuel"
            :value="`${
						car.fuel_type === 'PETROL'
							? 'Petrol'
							: car.fuel_type === 'DIESEL'
							? 'Diesel'
							: car.fuel_type === 'LPG'
							? 'LPG'
							: car.fuel_type === 'HYBRID'
							? 'Hybrid'
							: car.fuel_type === 'PLUG_IN_HYBRID'
							? 'Plug-in Hybrid'
							: car.fuel_type === 'ELECTRIC'
							? 'Electric'
							: car.fuel_type === 'HYDROGEN'
							? 'Hydrogen'
							: 'Other'
					}`"
        ><IconFuel
        /></CarStatItem>
      </ul>
      <div class="flex flex-col sm:flex-row items-center justify-between gap-4">
        <RouterLink :to="`/cars/${car.id_car}`">
          <ButtonSecondary>Rent</ButtonSecondary>
        </RouterLink>
        <span class="text-zinc-400 font-medium"
        >from
					<span class="text-red-500 font-bold">{{ car.price_deposit }} zł</span>
					a day!</span
        >
      </div>
    </div>
  </li>
</template>

<style scoped>
.item {
  box-shadow: 0px 0px 25px 0px rgba(0, 0, 0, 0.75);
}
</style>