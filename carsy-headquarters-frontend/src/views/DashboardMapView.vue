<script>
import ButtonPrimary from '../components/ButtonPrimary.vue';
import { store } from '../store';
import L from 'leaflet';
import { getCars } from '../api.js';

export default {
  components: { ButtonPrimary },
  data() {
    return {
      isLoading: false,
      map: null,
      cars: [],
    };
  },
  mounted() {
    if (!store.user || (store.user && store.user.role !== 'admin')) {
      return this.$router.push('/login');
    }
    this.initMap();
    this.fetchAndDisplayCars();
  },
  methods: {
    initMap() {
      this.map = L.map('map').setView([51.746, 19.455], 18);
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      }).addTo(this.map);
    },
    async fetchAndDisplayCars() {
      try {
        this.isLoading = true;
        this.cars = await getCars();
        this.displayCarMarkers();
      } catch (error) {
        console.error('Error fetching cars:', error);
      } finally {
        this.isLoading = false;
      }
    },
    displayCarMarkers() {
      const latlngs = [];
      this.cars.forEach(car => {
        if (car.locations && car.locations.length > 0) {
          const latestLocation = car.locations[car.locations.length - 1];
          const latlng = [latestLocation.latitude, latestLocation.longitude];
          const marker = L.marker(latlng).addTo(this.map);
          marker.bindPopup(`${car.brand} ${car.model} - ${car.license_plate}`);
          latlngs.push(latlng);
        }
      });
      if (latlngs.length > 0) {
        const bounds = L.latLngBounds(latlngs);
        this.map.fitBounds(bounds, { maxZoom: 15 });
      }
    }
  },
};
</script>

<template>
  <div class="flex flex-col items-center justify-center w-full h-full">
    <h2 class="text-2xl font-bold text-center">Dashboard - Map</h2>
    <div id="map"></div>
    <div
        v-if="isLoading"
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
  </div>
</template>

<style scoped>
#map {
  width: 100%;
  height: 100%;
  flex-grow: 1;
}
</style>