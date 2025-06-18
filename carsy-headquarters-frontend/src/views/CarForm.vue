<template>
  <div v-if="show" class="fixed inset-0 flex items-center justify-center z-50">
    <div class="absolute inset-0 bg-black opacity-50" @click="close"></div>
    <div class="bg-zinc-800 p-8 rounded-lg shadow-lg z-10 max-w-lg w-full text-white">
      <h2 class="text-2xl font-bold mb-6">{{ isEdit ? "Edit Car" : "Add Car" }}</h2>
      <form @submit.prevent="submit" class="space-y-4">
        <div>
          <label class="block text-gray-300">Brand</label>
          <input v-model="car.brand" type="text" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" required />
        </div>
        <div>
          <label class="block text-gray-300">Model</label>
          <input v-model="car.model" type="text" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" required />
        </div>
        <div>
          <label class="block text-gray-300">Registration Number</label>
          <input v-model="car.license_plate" type="text" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" required />
        </div>
        <div>
          <label class="block text-gray-300">Year</label>
          <input v-model="car.year" type="number" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" required />
        </div>
        <div>
          <label class="block text-gray-300">Value</label>
          <input v-model="car.value" type="number" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" required />
        </div>
        <div>
          <label class="block text-gray-300">Mileage</label>
          <input v-model="car.mileage" type="number" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" required />
        </div>
        <div>
          <label class="block text-gray-300">Horsepower</label>
          <input v-model="car.eng_power" type="number" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" required />
        </div>
        <div>
          <label class="block text-gray-300">Fuel</label>
          <select v-model="car.fuel_type" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" required>
            <option value="PETROL">Petrol</option>
            <option value="DIESEL">Diesel</option>
            <option value="LPG">LPG</option>
            <option value="HYBRID">Hybrid</option>
            <option value="PLUG_IN_HYBRID">Plug-in Hybrid</option>
            <option value="ELECTRIC">Electric</option>
            <option value="HYDROGEN">Hydrogen</option>
          </select>
        </div>
        <div>
          <label class="block text-gray-300">Transmission</label>
          <select v-model="car.transmission_type" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" required>
            <option value="AUTOMATIC">Automatic</option>
            <option value="MANUAL">Manual</option>
          </select>
        </div>
        <div>
          <label class="block text-gray-300">Branch</label>
          <select v-model="car.branch" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" required>
            <option v-for="branch in branches" :key="branch.id" :value="branch.id">
              {{ branch.address.city }} - {{ branch.address.street }}
            </option>
          </select>
        </div>
        <button type="submit" class="bg-green-500 hover:bg-green-600 text-white px-6 py-3 rounded-lg transition-colors">
          {{ isEdit ? "Update Car" : "Add Car" }}
        </button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    show: Boolean,
    car: Object,
    isEdit: Boolean,
    branches: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    close() {
      this.$emit("close");
    },
    submit() {
      this.$emit("submit", this.car);
    }
  }
};
</script>