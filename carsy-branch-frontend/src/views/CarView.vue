<script>
import { getCar } from '../api.js';
import { RouterLink } from 'vue-router';
import ButtonPrimary from '../components/ButtonPrimary.vue';
import ButtonSecondary from '../components/ButtonSecondary.vue';
import CarImage from '../components/CarImage.vue';
import CarStatItem from '../components/CarStatItem.vue';
import IconDoor from '../components/icons/IconDoor.vue';
import IconDrive from '../components/icons/IconDrive.vue';
import IconEngine from '../components/icons/IconEngine.vue';
import IconFuel from '../components/icons/IconFuel.vue';
import IconFuelCapacity from '../components/icons/IconFuelCapacity.vue';
import IconSeat from '../components/icons/IconSeat.vue';
import IconSpeed from '../components/icons/IconSpeed.vue';
import IconTimer from '../components/icons/IconTimer.vue';
import IconTransmission from '../components/icons/IconTransmission.vue';
import IconWeight from '../components/icons/IconWeight.vue';
import { store } from '../store';

export default {
  components: {CarStatItem,
    IconEngine,
    IconTimer,
    IconDrive,
    IconSpeed,
    IconTransmission,
    IconFuel,
    IconFuelCapacity,
    IconDoor,
    IconSeat,
    IconWeight,
    ButtonPrimary,
    ButtonSecondary,
    CarImage,
    RouterLink,},
  data() {
    return {
      car: null,
      rent_start: this.getCurrentDate(),
      rent_end: this.getTomorrowDate(),
    };
  },
  created() {
    this.getCar();
  },
  methods: {
    async getCar() {
      this.car = await getCar(this.$route.params.id);
      if (!this.car) {
        this.$router.push('/cars');
      }
    },
    getCurrentDate() {
      let now = new Date();
      now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
      return now.toISOString().slice(0, 16);
    },
    getTomorrowDate() {
      let now = new Date();
      now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
      now.setDate(now.getDate() + 1);
      return now.toISOString().slice(0, 16);
    },
    goToRentView() {
      if (!store.user) return this.$router.push('/login');
      if (!this.car.rentable) {
        return this.$router.push('/cars');
      }

      this.$router.push(`/rent/${this.car.id_car}`);
    },
  },
};
</script>

<template>
	<main
		v-if="car"
		class="col-start-1 sm:col-start-2 lg:col-start-3 col-end-13 row-start-2 row-end-[13] flex flex-col overflow-y-auto lg:pl-0 p-4 text-white bg-zinc-700"
	>
		<div
			class="flex flex-col items-center justify-center p-12 gap-6 relative z-0 text-center"
		>
			<span class="text-6xl sm:text-9xl lg:text-[10.5rem] font-bold">{{
				car.brand
			}}</span>
			<span
				class="text-6xl sm:text-9xl lg:text-[10.5rem] font-display stroked"
				>{{ car.model }}</span
			>
			<CarImage
				:car="car"
				class="absolute inset-0 w-full h-full object-cover object-center opacity-40 blur-[1.25px] grayscale-[60%] -z-[1] pointer-events-none"
			/>
		</div>
		<div>
			<ul class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-5 gap-4 my-8">
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
			<h2 class="text-2xl font-bold text-center">Price List</h2>
			<div class="overflow-x-auto">
				<table
					class="table-layout mx-auto my-2 text-sm xl:text-lg w-full max-w-screen-lg text-center"
				>
					<thead class="border-b border-b-red-500">
						<tr>
							<th></th>
							<th>Day</th>
							<th>Weekend</th>
							<th>Week</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>Price with deposit</th>
							<td>{{ car.price_deposit }} zł</td>
							<td>{{ car.price_deposit * 2 }} zł</td>
							<td>{{ car.price_deposit * 7 }} zł</td>
						</tr>
						<tr>
							<th>Price without deposit</th>
							<td>{{ car.price_no_deposit }} zł</td>
							<td>{{ car.price_no_deposit * 2 }} zł</td>
							<td>{{ car.price_no_deposit * 7 }} zł</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="flex flex-col sm:flex-row gap-4 mt-6">
				<RouterLink to="/cars" class="w-full">
					<ButtonSecondary width="full">Return to Car List</ButtonSecondary>
				</RouterLink>
				<ButtonPrimary width="full" @click="goToRentView"
					>Rent the Car</ButtonPrimary
				>
			</div>
		</div>
	</main>
	<div
		v-else
		class="col-start-1 sm:col-start-2 lg:col-start-3 col-end-13 row-start-2 row-end-[13] flex flex-col justify-center items-center bg-zinc-700"
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
</template>

<style scoped>
@supports (-webkit-text-stroke: 4px white) {
	.stroked {
		color: transparent;
		-webkit-text-stroke: 2px white;
	}

	@media (min-width: 1024px) {
		.stroked {
			-webkit-text-stroke: 4px white;
		}
	}
}

td,
th {
	padding: 0.5rem;
}

tr th:first-child {
	border-right: 1px solid rgb(239, 68, 68);
	text-align: center;
}

tbody tr:nth-child(odd) {
	background-color: rgb(39 39 42 / 0.5);
}
</style>
