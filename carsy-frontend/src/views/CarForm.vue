<template>
  <div class="container mx-auto p-6">
    <h1 class="text-2xl font-bold mb-4">{{ isEdit ? "Edit Car" : "Add Car" }}</h1>

    <form @submit.prevent="saveCar" class="bg-white p-6 shadow rounded-lg">
      <div class="mb-4">
        <label class="block text-gray-700">Brand</label>
        <input v-model="car.brand" type="text" class="w-full border p-2 rounded" required />
      </div>

      <div class="mb-4">
        <label class="block text-gray-700">Model</label>
        <input v-model="car.model" type="text" class="w-full border p-2 rounded" required />
      </div>

      <div class="mb-4">
        <label class="block text-gray-700">Year</label>
        <input v-model="car.year" type="number" class="w-full border p-2 rounded" required />
      </div>

      <div class="mb-4">
        <label class="block text-gray-700">Value</label>
        <input v-model="car.value" type="number" class="w-full border p-2 rounded" required />
      </div>

      <div class="mb-4">
        <label class="block text-gray-700">Rental Price Per Day</label>
        <input v-model="car.rentalPricePerDay" type="number" class="w-full border p-2 rounded" required />
      </div>

      <button type="submit" class="bg-green-500 text-white px-4 py-2 rounded">
        {{ isEdit ? "Update Car" : "Add Car" }}
      </button>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import config from "@/config.js";

export default {
  props: ["id"],
  data() {
    return {
      car: { brand: "", model: "", year: "", value: "", rentalPricePerDay: "" },
      isEdit: false,
    };
  },
  async created() {
    if (this.id) {
      this.isEdit = true;
      const response = await axios.get(config.API_BASE_URL + `/cars/${this.id}`);
      this.car = response.data;
    }
  },
  methods: {
    async saveCar() {
      if (this.isEdit) {
        await axios.put(config.API_BASE_URL + `/cars/${this.id}`, this.car);
      } else {
        await axios.post(config.API_BASE_URL + "/cars", this.car);
      }
      this.$router.push("/list");
    },
  },
};
</script>
