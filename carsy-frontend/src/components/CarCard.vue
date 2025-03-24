<script setup>
import { defineProps } from "vue";

defineProps({
  item: Object,
});
</script>

<template>
  <div class="max-w-sm border rounded overflow-hidden bg-white shadow-md">
    <div class="flex">
      <!-- Car Image Placeholder -->
      <div class="w-1/2 flex items-center justify-center bg-gray-200">
        <img
            src="https://cdn.euroncap.com/media/56661/jeep-renegade.png"
            alt="Car Image"
            class="w-full h-full object-cover"
        />
      </div>

      <!-- Car Details -->
      <div class="w-1/2 p-4 flex flex-col justify-between">
        <h3 class="font-bold text-lg">{{ item.brand }} {{ item.model }}</h3>
        <p class="text-sm text-gray-600">Year: {{ item.year }}</p>

        <div class="flex justify-end items-center mt-2">
          <span class="text-white bg-green-600 px-3 py-1 rounded-full text-sm font-bold">
            ${{ item.rentalPricePerDay }}
          </span>
          <span class="ml-2 text-green-600 text-xl cursor-pointer">🛒</span>
        </div>
      </div>
    </div>
  </div>
</template>
