<template >
  <main class="col-start-1 sm:col-start-2 lg:col-start-3 col-end-13 row-start-2 row-end-[13] flex flex-col items-center justify-center overflow-y-auto lg:pl-0 p-4 text-white bg-zinc-700">
  <div class="flex flex-col items-center w-full h-full">
    <h2 class="text-2xl font-bold text-center my-2">{{ cars.length }} cars available</h2>
    <button @click="openAddModal" class="bg-blue-500 hover:bg-blue-600 text-white px-6 py-3 rounded-lg mb-6 transition-colors">
      Add New Car
    </button>
    <div v-if="cars.length > 0" class="overflow-x-auto w-full">
      <table class="min-w-full bg-zinc-800 rounded-lg shadow-lg text-sm xl:text-base text-center">
        <thead class="bg-zinc-700 border-b border-b-red-500">
        <tr>
          <th class="p-4">Brand</th>
          <th class="p-4">Model</th>
          <th class="p-4">Year</th>
          <th class="p-4">Value</th>
          <th class="p-4">Rental Price</th>
          <th class="p-4">Actions</th>
        </tr>
        </thead>
        <tbody class="leading-none">
        <tr v-for="car in cars" :key="car.id" class="border-b border-zinc-600 hover:bg-zinc-600 transition-colors">
          <td class="p-4">{{ car.brand }}</td>
          <td class="p-4">{{ car.model }}</td>
          <td class="p-4">{{ car.year }}</td>
          <td class="p-4">${{ car.value }}</td>
          <td class="p-4">${{ car.rentalPricePerDay }}</td>
          <td class="p-4">
            <button @click="openEditModal(car)" class="text-blue-400 hover:text-red-500 mr-4 transition-colors">Edit</button>
            <button @click="deleteCar(car.id)" class="text-red-400 hover:text-red-500 transition-colors">Delete</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-else-if="isLoading" class="col-start-1 sm:col-start-2 lg:col-start-3 col-end-13 row-start-2 row-end-[13] flex flex-col justify-center items-center p-8 bg-zinc-700">
      <svg class="animate-spin h-24 w-24 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
      </svg>
    </div>
    <div v-else class="my-4 text-xl text-center">No cars available</div>
    <CarForm :show="showModal" :car="selectedCar" :isEdit="isEdit" @close="closeModal" @submit="handleSubmit" />
  </div>
  </main>
</template>

<script>
import axios from "axios";
import config from "@/config.js";
import CarForm from "./CarForm.vue";

export default {
  components: { CarForm },
  data() {
    return {
      cars: [],
      isLoading: true,
      showModal: false,
      selectedCar: null,
      isEdit: false,
    };
  },
  created() {
    this.fetchCars();
  },
  methods: {
    async fetchCars() {
      this.isLoading = true;
      try {
        const response = await axios.get(config.API_BASE_URL + "/cars");
        this.cars = response.data.sort((a, b) => a.id - b.id); // Sort by id ascending
      } catch (error) {
        console.error("Error fetching cars:", error);
      } finally {
        this.isLoading = false;
      }
    },
    openAddModal() {
      this.selectedCar = { brand: "", model: "", year: "", value: "", rentalPricePerDay: "" };
      this.isEdit = false;
      this.showModal = true;
    },
    openEditModal(car) {
      this.selectedCar = { ...car };
      this.isEdit = true;
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
    },
    async handleSubmit(car) {
      try {
        if (!car.value) return false;
        if (this.isEdit) {
          await axios.put(config.API_BASE_URL + `/cars/${car.id}`, car);
        } else {
          await axios.post(config.API_BASE_URL + "/cars", car);
        }
        await this.fetchCars();
      } catch (error) {
        console.error("Error saving car:", error);
      } finally {
        this.closeModal();
      }
    },
    async deleteCar(id) {
      if (confirm("Are you sure you want to delete this car?")) {
        try {
          await axios.delete(config.API_BASE_URL + `/cars/${id}`);
          this.fetchCars();
        } catch (error) {
          console.error("Error deleting car:", error);
        }
      }
    },
  },
};
</script>

<style scoped>
td,
th {
  padding: 1rem;
}

tbody tr:nth-child(odd) {
  background-color: rgba(39, 39, 42, 0.5);
}
</style>