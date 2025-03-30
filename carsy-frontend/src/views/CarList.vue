<template>
  <div class="container mx-auto p-6">
    <h1 class="text-2xl font-bold mb-4">Car List</h1>
    <router-link to="/add" class="bg-blue-500 text-white px-4 py-2 rounded mb-4 inline-block">
      Add New Car
    </router-link>

    <table class="min-w-full bg-white border border-gray-200 mt-4">
      <thead>
      <tr class="bg-gray-100">
        <th class="p-2 border">ID</th>
        <th class="p-2 border">Brand</th>
        <th class="p-2 border">Model</th>
        <th class="p-2 border">Year</th>
        <th class="p-2 border">Value</th>
        <th class="p-2 border">Rental Price</th>
        <th class="p-2 border">Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="car in cars" :key="car.id" class="text-center">
        <td class="p-2 border">{{ car.id }}</td>
        <td class="p-2 border">{{ car.brand }}</td>
        <td class="p-2 border">{{ car.model }}</td>
        <td class="p-2 border">{{ car.year }}</td>
        <td class="p-2 border">${{ car.value }}</td>
        <td class="p-2 border">${{ car.rentalPricePerDay }}</td>
        <td class="p-2 border">
          <router-link :to="`/edit/${car.id}`" class="text-blue-500 mx-2">Edit</router-link>
          <button @click="deleteCar(car.id)" class="text-red-500">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";
import config from "@/config.js";

export default {
  data() {
    return { cars: [] };
  },
  methods: {
    async fetchCars() {
      const response = await axios.get(config.API_BASE_URL + '/cars');
      this.cars = response.data;
    },
    async deleteCar(id) {
      if (confirm("Are you sure?")) {
        await axios.delete(config.API_BASE_URL + `/cars/${id}`);
        this.fetchCars();
      }
    },
  },
  mounted() {
    this.fetchCars();
  },
};
</script>
