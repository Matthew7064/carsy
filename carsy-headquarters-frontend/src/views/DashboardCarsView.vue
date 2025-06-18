<script>
import CarForm from './CarForm.vue';
import { getCars, addCar, updateCar, deleteCar, API_BASE_URL } from '../api.js';
import { store } from '../store';
import axios from 'axios';

export default {
  components: { CarForm },
  data() {
    return {
      cars: [],
      branches: [],
      isLoading: true,
      showModal: false,
      selectedCar: null,
      isEdit: false,
    };
  },
  created() {
    if (!store.user || store.user.role !== 'admin') {
      return this.$router.push('/login');
    }
    this.fetchCars();
  },
  methods: {
    async fetchBranches() {
      try {
        const response = await axios.get(`${API_BASE_URL}/branches`);
        this.branches = response.data;
        for (const branch of response.data) {
          for (const car of branch.cars) {
            const foundCar = this.cars.find(c => c.id_car === car.id);
            foundCar.branch = branch.id
          }
        }
      } catch (error) {
        console.error("Error fetching branches:", error);
      }
    },
    async fetchCars() {
      this.isLoading = true;
      this.cars = await getCars();
      this.cars.sort((a, b) => a.id_car.localeCompare(b.id_car));
      this.isLoading = false;
      this.fetchBranches();
    },
    openAddModal() {
      this.selectedCar = {
        brand: "",
        model: "",
        year: "",
        value: "",
        rentalPricePerDay: "",
        vin: "",
        registrationNumber: "",
        carStatus: "AVAILABLE",
        fuel_type: "PETROL",
        transmission_type: "MANUAL",
        mileage: 0,
        eng_power: 0,
        registrationDate: new Date().toISOString().split('T')[0],
        insuranceExpiryDate: new Date().toISOString().split('T')[0],
        inspectionExpiryDate: new Date().toISOString().split('T')[0],
        branch: this.branches[0]?.id || "",
        locations: []
      };
      this.isEdit = false;
      this.showModal = true;
    },
    openEditModal(car) {
      console.log(car)
      this.selectedCar = { ...car };
      this.isEdit = true;
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
    },
    async handleSubmit(car) {
      try {
        if (!car.model) return;
        if (this.isEdit) {
          await updateCar(car.id_car, car);
        } else {
          car.price_deposit = 0;
          await addCar(car);
        }
        this.fetchCars();
      } catch (error) {
        console.error("Error saving car:", error);
      } finally {
        this.closeModal();
      }
    },
    async deleteCar(id) {
      if (confirm("Are you sure you want to delete this car?")) {
        try {
          await deleteCar(id);
          this.fetchCars();
        } catch (error) {
          console.error("Error deleting car:", error);
        }
      }
    },
    async toggleRentable(id, rentable, car) {
      let newStatus = rentable ? 'IN_SERVICE' : 'AVAILABLE';
      car.carStatus = newStatus;
      await updateCar(id, car);
      this.fetchCars();
    },
    async savePrice(id, price, car) {
      car.price_deposit = Number(price);
      await updateCar(id, car);
      this.fetchCars();
    },
  },
};
</script>

<template>
  <div class="flex flex-col items-center w-full h-full">
    <h2 class="text-2xl font-bold text-center">
      Dashboard - Cars - {{ cars.length }}
    </h2>
    <button @click="openAddModal" class="bg-blue-500 hover:bg-blue-600 text-white px-6 py-3 rounded-lg mb-6 transition-colors">
      Add New Car
    </button>
    <div class="w-full h-full">
      <div v-if="cars.length > 0" class="overflow-x-auto">
        <table class="table-layout mx-auto my-2 text-sm xl:text-base w-full text-center">
          <thead class="border-b border-b-red-500">
          <tr>
            <th>Registration Number</th>
            <th>Brand</th>
            <th>Model</th>
            <th>Mileage</th>
            <th>Rentable</th>
            <th>Availability</th>
            <th>Rental Price</th>
            <th>Save Price</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody class="leading-none">
          <tr v-for="car in cars" :key="car.id_car">
            <td>
              {{ car.license_plate }}
            </td>
            <td>{{ car.brand }}</td>
            <td>{{ car.model }}</td>
            <td>{{ `${car.mileage} km` }}</td>
            <td :class="{ 'bg-green-600/10 text-green-300': car.rentable, 'bg-red-600/10 text-red-300': !car.rentable }">
              {{ car.rentable ? 'Yes' : 'No' }}
            </td>
            <td>
              <button class="hover:text-red-500 transition-colors" @click="toggleRentable(car.id_car, car.rentable, car)">
                Toggle
              </button>
            </td>
            <td :class="{ 'bg-green-600/10 text-green-300': car.price_deposit > 0, 'bg-red-600/10 text-red-300': car.price_deposit === 0 }">
              <input v-model="car.price_deposit" min="0" type="number" class="bg-zinc-700 px-4 py-2 w-24" />
            </td>
            <td>
              <button class="hover:text-red-500 transition-colors" @click="savePrice(car.id_car, car.price_deposit, car)">
                Save
              </button>
            </td>
            <td>
              <button @click="openEditModal(car)" class="text-blue-400 hover:text-red-500 mr-4 transition-colors">Edit</button>
              <button @click="deleteCar(car.id_car)" class="text-red-400 hover:text-red-500 transition-colors">Delete</button>
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
      <div v-else class="my-4 text-xl text-center">No Cars</div>
      <CarForm :show="showModal" :car="selectedCar" :isEdit="isEdit" :branches="branches" @close="closeModal" @submit="handleSubmit" />
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