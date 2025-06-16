import { store } from './store';
import axios from "axios";

const API_BASE_URL = 'http://localhost:8080';

let users = {
  'uuid-user1': {
    id: 'uuid-user1',
    pesel: '12345678901',
    name: 'John',
    surname: 'Doe',
    email: 'user@example.com',
    phoneNumber: '+48-123-456-789',
    accountNumber: '12345678901234567890123456',
    login: 'user1',
    password: 'password',
    roles: { id: 'uuid-role1', role: 'ROLE_CUSTOMER' },
    address: 'uuid-address1',
    branches: []
  },
  'uuid-admin1': {
    id: 'uuid-admin1',
    pesel: '98765432109',
    name: 'Admin',
    surname: 'Admin',
    email: 'admin@example.com',
    phoneNumber: '+48-987-654-321',
    accountNumber: '65432109876543210987654321',
    login: 'admin1',
    password: 'adminpass',
    roles: [{ id: 'uuid-role2', role: 'ROLE_EMPLOYEE' }],
    address: 'uuid-address2',
    branches: []
  }
};

let roles = {
  'uuid-role1': { id: 'uuid-role1', role: 'ROLE_CUSTOMER' },
  'uuid-role2': { id: 'uuid-role2', role: 'ROLE_EMPLOYEE' }
};

function convertCarToFrontend(car) {
  return {
    id_car: car.id,
    vin: car.vin,
    license_plate: car.registrationNumber,
    brand: car.brand,
    model: car.model,
    year: car.year,
    price_deposit: car.rentalPricePerDay,
    price_no_deposit: car.rentalPricePerDay + Math.round(car.rentalPricePerDay*0.1),
    value: car.value,
    carStatus: car.carStatus,
    rentable: car.carStatus === 'AVAILABLE',
    fuel_type: car.fuel,
    transmission_type: car.transmission,
    mileage: car.mileage,
    eng_power: car.horsepower,
    registrationDate: new Date(car.registrationDate),
    insuranceExpiryDate: new Date(car.insuranceExpiryDate),
    inspectionExpiryDate: new Date(car.inspectionExpiryDate),
    branch: car.branch,
    locations: car.locations
  };
}

function convertOrderToFrontend(order) {
  return {
    id: order.id,
    id_car: order.car,
    id_user: order.user,
    rent_start: new Date(order.startDate),
    rent_end: new Date(order.endDate),
    status: order.paid ? 'paid' : 'not_paid',
    total_price: order.price
  };
}

function convertUserToFrontend(user) {
  return {
    id: user.id,
    email: user.email,
    password: user.password,
    role: user.roles.some(role => role.role === 'ROLE_EMPLOYEE') ? 'admin' : 'user',
    pesel: user.pesel,
    name: user.name,
    surname: user.surname,
    phoneNumber: user.phoneNumber,
    accountNumber: user.accountNumber,
    login: user.login,
    address: user.address,
    branches: user.branches
  };
}

function convertCarToBackend(car) {
  return {
    id: car.id_car,
    vin: car.vin,
    registrationNumber: car.license_plate,
    brand: car.brand,
    model: car.model,
    year: car.year,
    rentalPricePerDay: car.price_deposit,
    value: car.value,
    carStatus: car.carStatus,
    fuel: car.fuel_type,
    transmission: car.transmission_type,
    mileage: car.mileage,
    horsepower: car.eng_power,
    registrationDate: car.registrationDate instanceof Date ? car.registrationDate.toISOString().split('T')[0] : car.registrationDate,
    insuranceExpiryDate: car.insuranceExpiryDate instanceof Date ? car.insuranceExpiryDate.toISOString().split('T')[0] : car.insuranceExpiryDate,
    inspectionExpiryDate: car.inspectionExpiryDate instanceof Date ? car.inspectionExpiryDate.toISOString().split('T')[0] : car.inspectionExpiryDate,
    branch: car.branch,
    locations: car.locations
  };
}

function convertOrderToBackend(order) {
  return {
    id: order.id,
    car: order.id_car,
    user: order.id_user,
    paid: order.status === 'paid',
    startDate: order.rent_start instanceof Date ? order.rent_start.toISOString() : order.rent_start,
    endDate: order.rent_end instanceof Date ? order.rent_end.toISOString() : order.rent_end,
    price: order.total_price
  };
}

function generateRandomNumber(length) {
  return Array.from({ length }, () => Math.floor(Math.random() * 10)).join('');
}

function generateEmail(name, surname) {
  const domains = ["example.com", "testmail.com", "mailservice.org"];
  const domain = domains[Math.floor(Math.random() * domains.length)];
  return `${name.toLowerCase()}.${surname.toLowerCase()}@${domain}`;
}

function generatePhoneNumber() {
  return `+48-${generateRandomNumber(3)}-${generateRandomNumber(3)}-${generateRandomNumber(3)}`;
}

function generateAccountNumber() {
  return generateRandomNumber(26); // Polish bank account numbers have 26 digits
}

// Mock Authentication
export const mockAuth = {
  currentUser: null,
  signIn(login, password) {
    getUsers().then((users) => {
      const user = users.find(user => user.login === login && user.password === password);
      if (user) {
        this.currentUser = user;
        store.user = this.currentUser;
        localStorage.setItem('currentUserId', user.id);
        return Promise.resolve(this.currentUser);
      }
      return Promise.reject(new Error('Invalid credentials'));
    })
  },
  signOut() {
    this.currentUser = null;
    store.user = null;
    localStorage.removeItem('currentUserId');
    return Promise.resolve();
  },
  createUser(login, password, name, surname) {
    axios.get(`${API_BASE_URL}/roles`).then(response => {
      const customerRole = response.data.find(customerRole => customerRole.role === "ROLE_CUSTOMER");
      const newUser =  {
        pesel: generateRandomNumber(11),
        name: name,
        surname: surname,
        email: generateEmail(name, surname),
        phoneNumber: generatePhoneNumber(),
        accountNumber: generateAccountNumber(),
        login: login,
        password: password,
        roles: [customerRole],
        address: {
          "street": "ul. Przykładowa",
          "city": "Warszawa",
          "postalCode": "00-001",
          "country": "Polska",
          "number": "10",
          "flatNumber": "6"
        },
        branches: []
      };
      console.log(newUser);
      return axios.post(`${API_BASE_URL}/users`, newUser).then(response => {
        const createdUser = response.data;
        const frontendUser = convertUserToFrontend(createdUser);
        this.currentUser = frontendUser;
        store.user = frontendUser;
        localStorage.setItem('currentUserId', createdUser.id);
        return frontendUser;
      });
    });
  }
};

// Car Functions
export function getCars() {
  return axios.get(`${API_BASE_URL}/cars`).then(response => response.data.map(convertCarToFrontend));
}

export function getRentableCars(model = '') {
  return axios.get(`${API_BASE_URL}/cars`).then(response => {
    let filteredCars = response.data.filter(car => car.carStatus === 'AVAILABLE');
    if (model) {
      filteredCars = filteredCars.filter(car => car.model.toLowerCase().startsWith(model.toLowerCase()));
    }
    filteredCars.sort((a, b) => a.model.localeCompare(b.model));
    return filteredCars.map(convertCarToFrontend);
  });
}

export function getCar(id) {
  return axios.get(`${API_BASE_URL}/cars/${id}`).then(response => convertCarToFrontend(response.data));
}

export function updateCarAvailability(id, carStatus) {
  const backendUpdates = { carStatus: carStatus };
  console.log(backendUpdates)
  return axios.patch(`${API_BASE_URL}/cars/${id}`, backendUpdates).then(() => {});
}

export function updateCarPrice(id, new_price) {
  const backendUpdates = { rentalPricePerDay: new_price };
  return axios.patch(`${API_BASE_URL}/cars/${id}`, backendUpdates).then(() => {});
}

function generateVIN() {
  const chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  let vin = "1HGCM82633A";

  for (let i = 0; i < 6; i++) {
    vin += chars[Math.floor(Math.random() * chars.length)];
  }

  return vin;
}

export async function addCar(car) {
  const backendCar = convertCarToBackend(car);
  axios.get(`${API_BASE_URL}/branches`).then((response) => {
    const branchID = response.data[0].id;
    console.log(branchID);
    backendCar.branch = {'id': branchID};
    backendCar.fuel = "PETROL";
    backendCar.horsepower = 132;
    backendCar.vin = generateVIN();
    backendCar.transmission = "AUTOMATIC";
    console.log(backendCar)
    return axios.post(`${API_BASE_URL}/cars`, backendCar).then(() => {});
  });
}

export function updateCar(id, car) {
  const backendCar = convertCarToBackend(car);
  console.log(backendCar)
  return axios.patch(`${API_BASE_URL}/cars/${id}`, backendCar).then(() => {});
}

export function deleteCar(id) {
  return axios.delete(`${API_BASE_URL}/cars/${id}`).then(() => {});
}

// Rent Functions
export function getRents() {
  return axios.get(`${API_BASE_URL}/orders`).then(response => response.data.map(convertOrderToFrontend));
}

export function getUserRents(userId) {
  return axios.get(`${API_BASE_URL}/orders`).then(response => {
    const userOrders = response.data
        .filter(o => o.user.id === userId)
        .map(convertOrderToFrontend);
    userOrders.sort((a, b) => new Date(b.rent_start) - new Date(a.rent_start));
    return userOrders;
  });
}

export function addRent(orderData) {
  const backendOrder = convertOrderToBackend(orderData);
  const userID = backendOrder.user;
  const carID = backendOrder.car;
  backendOrder.user = {"id": userID};
  backendOrder.car = {"id": carID};
  return axios.post(`${API_BASE_URL}/orders`, backendOrder).then(response => response.data.id);
}

export function updateRentStatus(id, newStatus, updatedRent) {
  const backendUpdates = {startDate: updatedRent.rent_start instanceof Date ? updatedRent.rent_start.toISOString() : updatedRent.rent_start,
    endDate: updatedRent.rent_end instanceof Date ? updatedRent.rent_end.toISOString() : updatedRent.rent_end, price: updatedRent.total_price, paid: newStatus === 'paid' };
  return axios.patch(`${API_BASE_URL}/orders/${id}`, backendUpdates).then(() => {});
}

// User Functions
export function getUsers() {
  return axios.get(`${API_BASE_URL}/users`).then(response => response.data.map(convertUserToFrontend));
}

export function updateUserRole(id, roleName, user) {
  axios.get(`${API_BASE_URL}/roles`).then(response => {
    const adminRole = response.data.find(adminRole => adminRole.role === "ROLE_EMPLOYEE");
    const customerRole = response.data.find(customerRole => customerRole.role === "ROLE_CUSTOMER");
    axios.get(`${API_BASE_URL}/users/${id}`).then(response => {
      if(response.data.roles.some(adminRole => adminRole.role === "ROLE_EMPLOYEE") && roleName === "user"){
        const newUser = response.data;
        newUser.roles = [customerRole];
        return axios.put(`${API_BASE_URL}/users/${id}`, newUser).then(() => {});
      }
      if(!response.data.roles.some(adminRole => adminRole.role === "ROLE_EMPLOYEE") && roleName === "admin"){
        const newUser = response.data;
        newUser.roles = [adminRole];
        return axios.put(`${API_BASE_URL}/users/${id}`, newUser).then(() => {});
      }
    })
  })
}

export function assignCarToBranch(carId, branchId) {
  return axios.post(`${API_BASE_URL}/cars/${carId}/assign/${branchId}`, {}).then(() => {});
}

export function getCarLocations(id, since) {
  return axios.get(`${API_BASE_URL}/cars/${id}/locations`, { params: { since } }).then(response => response.data);
}

// Initialize current user
const currentUserId = localStorage.getItem('currentUserId');
if (currentUserId) {
  axios.get(`${API_BASE_URL}/users/${currentUserId}`).then(response => {
    const user = response.data;
    if (user){
      mockAuth.currentUser = convertUserToFrontend(user);
      store.user = mockAuth.currentUser;
    }
  });
}