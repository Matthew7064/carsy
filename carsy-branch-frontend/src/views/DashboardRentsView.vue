<script>
import { getRents, updateRentStatus } from '../api.js';
import { store } from '../store';
import QRCode from 'qrcode';
import jsQR from 'jsqr';

export default {
  data() {
    return {
      rents: [],
      isLoading: true,
      showQRModal: false,
      qrImage: '',
      selectedRent: null,
      decodedRent: null,
      showRentModal: false,
    };
  },
  created() {
    if (!store.user || store.user.role !== 'admin') {
      return this.$router.push('/login');
    }
    this.searchRents();
  },
  methods: {
    async searchRents() {
      this.isLoading = true;
      this.rents = await getRents();
      this.isLoading = false;
    },
    async toggleStatus(id, status) {
      const updatedRent = this.rents.find(rent => rent.id === id);
      if (updatedRent.status === 'paid') return;
      const newStatus = 'paid';
      updatedRent.status = newStatus;
      updatedRent.id_car.carStatus = "AVAILABLE";
      await updateRentStatus(id, newStatus, updatedRent).catch(() => {
        updatedRent.status = status;
      });
    },
    async generateQRCode(rent) {
      this.selectedRent = rent;
      const rentData = JSON.stringify(rent);
      try {
        this.qrImage = await QRCode.toDataURL(rentData);
        this.showQRModal = true;
      } catch (error) {
        console.error('Error generating QR code:', error);
      }
    },
    closeQRModal() {
      this.showQRModal = false;
      this.qrImage = '';
      this.selectedRent = null;
    },
    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          const img = new Image();
          img.onload = () => {
            const canvas = document.createElement('canvas');
            canvas.width = img.width;
            canvas.height = img.height;
            const ctx = canvas.getContext('2d');
            ctx.drawImage(img, 0, 0);
            const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
            const code = jsQR(imageData.data, imageData.width, imageData.height);
            if (code) {
              const rentData = JSON.parse(code.data);
              this.decodedRent = rentData;
              this.showRentModal = true;
            } else {
              alert('No QR code found in the image.');
            }
          };
          img.src = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },
    closeRentModal() {
      this.showRentModal = false;
      this.decodedRent = null;
    },
  },
};
</script>

<template>
  <div class="flex flex-col items-center w-full h-full">
    <h2 class="text-2xl font-bold text-center">
      Dashboard - Rentals - {{ rents.length }}
    </h2>
    <div class="my-4">
      <label for="qr-upload" class="block text-gray-300 mb-2">Upload external order QR code here</label>
      <input id="qr-upload" type="file" @change="handleFileUpload" accept="image/*" class="w-full bg-zinc-700 text-white border border-zinc-600 p-2 rounded" />
    </div>
    <div class="w-full h-full">
      <div v-if="rents.length > 0" class="overflow-x-auto">
        <table class="table-layout mx-auto my-2 text-sm xl:text-base w-full text-center">
          <thead class="border-b border-b-red-500">
          <tr>
            <th>Car</th>
            <th>User Name</th>
            <th>Rental Start</th>
            <th>Rental End</th>
            <th>Paid</th>
            <th>Cost</th>
            <th>Accept payment</th>
            <th>QR Code</th>
          </tr>
          </thead>
          <tbody class="leading-none">
          <tr v-for="rent in rents" :key="rent.id">
            <td>
              <RouterLink :to="`/cars/${rent.id_car.id}`" class="hover:text-red-500 transition-colors">
                {{ rent.id_car.brand }} {{ rent.id_car.model }} {{ rent.id_car.registrationNumber }}
              </RouterLink>
            </td>
            <td>{{ rent.id_user.name }} {{ rent.id_user.surname }}</td>
            <td>{{ rent.rent_start.toLocaleString() }}</td>
            <td>{{ rent.rent_end.toLocaleString() }}</td>
            <td :class="{ 'bg-green-600/10 text-green-300': rent.status === 'paid', 'bg-red-600/10 text-red-300': rent.status === 'not_paid' }">
              {{ rent.status === 'paid' ? 'Paid' : 'Not Paid' }}
            </td>
            <td>{{ `${rent.total_price} zł` }}</td>
            <td>
              <button class="hover:text-red-500 transition-colors" @click="toggleStatus(rent.id, rent.status)">
                Payment accepted
              </button>
            </td>
            <td>
              <button class="hover:text-red-500 transition-colors" @click="generateQRCode(rent)">
                Generate QR
              </button>
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
      <div v-else class="my-4 text-xl text-center">No rentals</div>
    </div>
    <!-- QR Code Modal -->
    <div v-if="showQRModal" class="fixed inset-0 flex items-center justify-center z-50">
      <div class="absolute inset-0 bg-black opacity-50" @click="closeQRModal"></div>
      <div class="bg-zinc-800 p-8 rounded-lg shadow-lg z-10 max-w-lg w-full text-white">
        <h2 class="text-2xl font-bold mb-6">QR Code for Order of car {{ selectedRent.id_car.registrationNumber }}</h2>
        <img :src="qrImage" alt="QR Code" class="mx-auto" />
        <button @click="closeQRModal" class="mt-4 bg-red-500 hover:bg-red-600 text-white px-6 py-3 rounded-lg transition-colors">
          Close
        </button>
      </div>
    </div>
    <!-- Rent Information Modal -->
    <div v-if="showRentModal" class="fixed inset-0 flex items-center justify-center z-50">
      <div class="absolute inset-0 bg-black opacity-50" @click="closeRentModal"></div>
      <div class="bg-zinc-800 p-8 rounded-lg shadow-lg z-10 max-w-lg w-full text-white">
        <h2 class="text-2xl font-bold mb-6">Rental Information</h2>
        <div v-if="decodedRent">
          <h3 class="text-xl font-bold mb-2">Car Details</h3>
          <p>Brand: {{ decodedRent.id_car.brand }}</p>
          <p>Model: {{ decodedRent.id_car.model }}</p>
          <p>Registration: {{ decodedRent.id_car.registrationNumber }}</p>
          <h3 class="text-xl font-bold mt-4 mb-2">User Details</h3>
          <p>Name: {{ decodedRent.id_user.name }} {{ decodedRent.id_user.surname }}</p>
          <p>Email: {{ decodedRent.id_user.email }}</p>
          <h3 class="text-xl font-bold mt-4 mb-2">Rental Details</h3>
          <p>Start: {{ new Date(decodedRent.rent_start).toLocaleString() }}</p>
          <p>End: {{ new Date(decodedRent.rent_end).toLocaleString() }}</p>
          <p>Total Price: {{ decodedRent.total_price }} zł</p>
          <p>Paid: {{ decodedRent.status === 'paid' ? 'Yes' : 'No' }}</p>
        </div>
        <button @click="closeRentModal" class="mt-4 bg-red-500 hover:bg-red-600 text-white px-6 py-3 rounded-lg transition-colors">
          Close
        </button>
      </div>
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