<script setup>
import { ref, onMounted } from "vue";
import CarCard from "../components/CarCard.vue";
import config from "@/config.js";

const cars = ref([]);

const mockData = [
  {
    id: 1,
    brand: "Jeep",
    model: "Renegade",
    year: 2021,
    value: 25000.0,
    rentalPricePerDay: 200.0,
  },
  {
    id: 2,
    brand: "Toyota",
    model: "Corolla",
    year: 2022,
    value: 22000.0,
    rentalPricePerDay: 180.0,
  },
  {
    id: 3,
    brand: "Jeep",
    model: "Renegade",
    year: 2021,
    value: 25000.0,
    rentalPricePerDay: 200.0,
  },
  {
    id: 4,
    brand: "Toyota",
    model: "Corolla",
    year: 2022,
    value: 22000.0,
    rentalPricePerDay: 180.0,
  },
  {
    id: 5,
    brand: "Jeep",
    model: "Renegade",
    year: 2021,
    value: 25000.0,
    rentalPricePerDay: 200.0,
  },
  {
    id: 6,
    brand: "Toyota",
    model: "Corolla",
    year: 2022,
    value: 22000.0,
    rentalPricePerDay: 180.0,
  },
];

const fetchCars = async () => {
  try {
    const response = await fetch(config.API_BASE_URL + "/cars");
    if (!response.ok) throw new Error("Failed to fetch");
    cars.value = await response.json();
  } catch (error) {
    console.error("Error fetching cars:", error);
    cars.value = mockData;
  }
};

onMounted(fetchCars);
</script>

<template>
  <h1 class="text-3xl sm:text-4xl font-bold text-gray-900 mt-6 mb-4 col-span-12 row-span-12 p-4">
    Car Rental - Select a Car
  </h1>
  <div class="col-span-12 row-span-12 flex flex-col items-center w-full min-h-screen">
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 w-full max-w-7xl p-4">
      <car-card v-for="car in cars" :key="car.id" :item="car" />
    </div>
  </div>
</template>