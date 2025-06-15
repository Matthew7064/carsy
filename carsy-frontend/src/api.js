import { store } from './store';

const API_BASE_URL = '/api';

let addresses = {
  'uuid-address1': {
    id: 'uuid-address1',
    street: 'Main St',
    number: '123',
    flatNumber: '',
    postalCode: '00-000',
    city: 'Warsaw',
    country: 'Poland'
  },
  'uuid-address2': {
    id: 'uuid-address2',
    street: 'Second St',
    number: '456',
    flatNumber: '7',
    postalCode: '11-111',
    city: 'Krakow',
    country: 'Poland'
  }
};

let roles = {
  'uuid-role1': { id: 'uuid-role1', role: 'user' },
  'uuid-role2': { id: 'uuid-role2', role: 'admin' }
};

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
    roles: ['uuid-role1'],
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
    roles: ['uuid-role2'],
    address: 'uuid-address2',
    branches: []
  }
};

let branches = {
  'uuid-branch1': {
    id: 'uuid-branch1',
    address: 'uuid-address1',
    users: ['uuid-user1', 'uuid-admin1'],
    cars: ['uuid-car1', 'uuid-car2']
  },
  'uuid-branch2': {
    id: 'uuid-branch2',
    address: 'uuid-address2',
    users: [],
    cars: ['uuid-car3']
  }
};

let cars = {
  'uuid-car1': {
    id: 'uuid-car1',
    vin: 'VIN123456789',
    registrationNumber: 'S0RENT01',
    brand: 'Seat',
    model: 'Leon Cupra R',
    year: 2020,
    rentalPricePerDay: 100.0,
    value: 50000.0,
    carStatus: 'AVAILABLE',
    fuel: 'PETROL',
    transmission: 'MANUAL',
    mileage: 32000,
    horsepower: 310,
    registrationDate: '2020-01-01',
    insuranceExpiryDate: '2025-01-01',
    inspectionExpiryDate: '2024-01-01',
    branch: 'uuid-branch1',
    locations: ['uuid-location1']
  },
  'uuid-car2': {
    id: 'uuid-car2',
    vin: 'VIN987654321',
    registrationNumber: 'S0RENT02',
    brand: 'Volkswagen',
    model: 'Golf GTI',
    year: 2021,
    rentalPricePerDay: 90.0,
    value: 45000.0,
    carStatus: 'AVAILABLE',
    fuel: 'PETROL',
    transmission: 'AUTOMATIC',
    mileage: 25000,
    horsepower: 245,
    registrationDate: '2021-01-01',
    insuranceExpiryDate: '2025-01-01',
    inspectionExpiryDate: '2024-01-01',
    branch: 'uuid-branch1',
    locations: []
  },
  'uuid-car3': {
    id: 'uuid-car3',
    vin: 'VIN456789123',
    registrationNumber: 'S0RENT03',
    brand: 'BMW',
    model: 'M3',
    year: 2022,
    rentalPricePerDay: 150.0,
    value: 80000.0,
    carStatus: 'RENTED',
    fuel: 'PETROL',
    transmission: 'AUTOMATIC',
    mileage: 15000,
    horsepower: 510,
    registrationDate: '2022-01-01',
    insuranceExpiryDate: '2025-01-01',
    inspectionExpiryDate: '2024-01-01',
    branch: 'uuid-branch2',
    locations: []
  }
};

let locations = {
  'uuid-location1': {
    id: 'uuid-location1',
    latitude: 52.2297,
    longitude: 21.0122,
    time: '2023-01-01T10:00:00',
    car: 'uuid-car1'
  },
  'uuid-location2': {
    id: 'uuid-location2',
    latitude: 50.0647,
    longitude: 19.9450,
    time: '2023-02-01T12:00:00',
    car: 'uuid-car2'
  }
};

let orders = {
  'uuid-order1': {
    id: 'uuid-order1',
    car: 'uuid-car1',
    user: 'uuid-user1',
    isPaid: true,
    startDate: '2023-01-01T10:00:00',
    endDate: '2023-01-02T10:00:00',
    price: 550
  },
  'uuid-order2': {
    id: 'uuid-order2',
    car: 'uuid-car3',
    user: 'uuid-admin1',
    isPaid: false,
    startDate: '2023-02-01T12:00:00',
    endDate: '2023-02-03T12:00:00',
    price: 300
  }
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
    status: order.isPaid ? 'paid' : 'not_paid',
    total_price: order.price
  };
}

function convertUserToFrontend(user) {
  const userRoles = user.roles.map(roleId => roles[roleId].role);
  return {
    id: user.id,
    email: user.email,
    password: user.password,
    role: userRoles.includes('admin') ? 'admin' : 'user',
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
    registrationDate: car.registrationDate instanceof Date ? car.registrationDate.toISOString().split('T')[0] : car.registrationDate, // Convert to string
    insuranceExpiryDate: car.insuranceExpiryDate instanceof Date ? car.insuranceExpiryDate.toISOString().split('T')[0] : car.insuranceExpiryDate, // Convert to string
    inspectionExpiryDate: car.inspectionExpiryDate instanceof Date ? car.inspectionExpiryDate.toISOString().split('T')[0] : car.inspectionExpiryDate, // Convert to string
    branch: car.branch,
    locations: car.locations
  };
}

function convertOrderToBackend(order) {
  return {
    id: order.id,
    car: order.id_car,
    user: order.id_user,
    isPaid: order.status === 'paid',
    startDate: order.rent_start instanceof Date ? order.rent_start.toISOString() : order.rent_start, // Convert to string
    endDate: order.rent_end instanceof Date ? order.rent_end.toISOString() : order.rent_end, // Convert to string
    price: order.total_price
  };
}

function convertUserToBackend(user) {
  const roleId = user.role === 'admin' ? 'uuid-role2' : 'uuid-role1';
  return {
    id: user.id,
    pesel: user.pesel,
    name: user.name,
    surname: user.surname,
    email: user.email,
    phoneNumber: user.phoneNumber,
    accountNumber: user.accountNumber,
    login: user.login,
    password: user.password,
    roles: [roleId],
    address: user.address,
    branches: user.branches
  };
}

// Mock Axios Functions
function axiosGet(url, params = {}) {
  const path = url.replace(API_BASE_URL, '');
  if (path === '/cars') {
    return Promise.resolve({ data: JSON.parse(JSON.stringify(Object.values(cars))) });
  }
  if (path.match(/^\/cars\/[^/]+$/)) {
    const id = path.split('/')[2];
    const car = cars[id];
    return car ? Promise.resolve({ data: JSON.parse(JSON.stringify(car)) }) : Promise.reject(new Error('Car not found'));
  }
  if (path.match(/^\/cars\/[^/]+\/locations$/)) {
    const id = path.split('/')[2];
    const since = params.since;
    const car = cars[id];
    if (car) {
      let carLocations = car.locations.map(locId => locations[locId]);
      if (since) {
        carLocations = carLocations.filter(loc => new Date(loc.time) > new Date(since));
      }
      return Promise.resolve({ data: JSON.parse(JSON.stringify(carLocations)) });
    }
    return Promise.reject(new Error('Car not found'));
  }
  if (path === '/orders') {
    return Promise.resolve({ data: JSON.parse(JSON.stringify(Object.values(orders))) });
  }
  if (path.match(/^\/orders\/[^/]+$/)) {
    const id = path.split('/')[2];
    const order = orders[id];
    return order ? Promise.resolve({ data: JSON.parse(JSON.stringify(order)) }) : Promise.reject(new Error('Order not found'));
  }
  if (path === '/users') {
    return Promise.resolve({ data: JSON.parse(JSON.stringify(Object.values(users))) });
  }
  if (path.match(/^\/users\/[^/]+$/)) {
    const id = path.split('/')[2];
    const user = users[id];
    return user ? Promise.resolve({ data: JSON.parse(JSON.stringify(user)) }) : Promise.resolve({ data: null });
  }
  if (path === '/branches') {
    return Promise.resolve({ data: JSON.parse(JSON.stringify(Object.values(branches))) });
  }
  if (path.match(/^\/branches\/[^/]+$/)) {
    const id = path.split('/')[2];
    const branch = branches[id];
    return branch ? Promise.resolve({ data: JSON.parse(JSON.stringify(branch)) }) : Promise.reject(new Error('Branch not found'));
  }
  if (path === '/locations') {
    return Promise.resolve({ data: JSON.parse(JSON.stringify(Object.values(locations))) });
  }
  if (path.match(/^\/locations\/[^/]+$/)) {
    const id = path.split('/')[2];
    const location = locations[id];
    return location ? Promise.resolve({ data: JSON.parse(JSON.stringify(location)) }) : Promise.reject(new Error('Location not found'));
  }
  if (path === '/roles') {
    return Promise.resolve({ data: JSON.parse(JSON.stringify(Object.values(roles))) });
  }
  if (path.match(/^\/roles\/[^/]+$/)) {
    const id = path.split('/')[2];
    const role = roles[id];
    return role ? Promise.resolve({ data: JSON.parse(JSON.stringify(role)) }) : Promise.reject(new Error('Role not found'));
  }
  return Promise.reject(new Error('Unknown endpoint'));
}

function axiosPost(url, data) {
  const path = url.replace(API_BASE_URL, '');
  if (path === '/cars') {
    const id = 'uuid-car' + Date.now();
    cars[id] = { ...data, id };
    return Promise.resolve({ data: JSON.parse(JSON.stringify(cars[id])) });
  }
  if (path === '/orders') {
    const id = 'uuid-order' + Date.now();
    orders[id] = { ...data, id };
    return Promise.resolve({ data: JSON.parse(JSON.stringify(orders[id])) });
  }
  if (path === '/users') {
    const id = 'uuid-user' + Date.now();
    users[id] = { ...data, id };
    return Promise.resolve({ data: JSON.parse(JSON.stringify(users[id])) });
  }
  if (path === '/branches') {
    const id = 'uuid-branch' + Date.now();
    branches[id] = { ...data, id };
    return Promise.resolve({ data: JSON.parse(JSON.stringify(branches[id])) });
  }
  if (path === '/locations') {
    const id = 'uuid-location' + Date.now();
    locations[id] = { ...data, id };
    return Promise.resolve({ data: JSON.parse(JSON.stringify(locations[id])) });
  }
  if (path === '/roles') {
    const id = 'uuid-role' + Date.now();
    roles[id] = { ...data, id };
    return Promise.resolve({ data: JSON.parse(JSON.stringify(roles[id])) });
  }
  if (path.match(/^\/cars\/[^/]+\/assign\/[^/]+$/)) {
    const carId = path.split('/')[2];
    const branchId = path.split('/')[4];
    if (cars[carId] && branches[branchId]) {
      cars[carId].branch = branchId;
      branches[branchId].cars.push(carId);
      return Promise.resolve({ data: null });
    }
    return Promise.reject(new Error('Car or Branch not found'));
  }
  return Promise.reject(new Error('Unknown endpoint'));
}

function axiosPut(url, data) {
  const path = url.replace(API_BASE_URL, '');
  if (path.match(/^\/cars\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (cars[id]) {
      cars[id] = { ...data, id };
      return Promise.resolve({ data: JSON.parse(JSON.stringify(cars[id])) });
    }
    return Promise.reject(new Error('Car not found'));
  }
  if (path.match(/^\/orders\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (orders[id]) {
      orders[id] = { ...data, id };
      return Promise.resolve({ data: JSON.parse(JSON.stringify(orders[id])) });
    }
    return Promise.reject(new Error('Order not found'));
  }
  if (path.match(/^\/users\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (users[id]) {
      users[id] = { ...data, id };
      return Promise.resolve({ data: JSON.parse(JSON.stringify(users[id])) });
    }
    return Promise.reject(new Error('User not found'));
  }
  if (path.match(/^\/branches\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (branches[id]) {
      branches[id] = { ...data, id };
      return Promise.resolve({ data: JSON.parse(JSON.stringify(branches[id])) });
    }
    return Promise.reject(new Error('Branch not found'));
  }
  if (path.match(/^\/roles\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (roles[id]) {
      roles[id] = { ...data, id };
      return Promise.resolve({ data: JSON.parse(JSON.stringify(roles[id])) });
    }
    return Promise.reject(new Error('Role not found'));
  }
  return Promise.reject(new Error('Unknown endpoint'));
}

function axiosPatch(url, data) {
  const path = url.replace(API_BASE_URL, '');
  if (path.match(/^\/cars\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (cars[id]) {
      cars[id] = { ...cars[id], ...data };
      return Promise.resolve({ data: JSON.parse(JSON.stringify(cars[id])) });
    }
    return Promise.reject(new Error('Car not found'));
  }
  if (path.match(/^\/orders\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (orders[id]) {
      orders[id] = { ...orders[id], ...data };
      return Promise.resolve({ data: JSON.parse(JSON.stringify(orders[id])) });
    }
    return Promise.reject(new Error('Order not found'));
  }
  if (path.match(/^\/users\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (users[id]) {
      users[id] = { ...users[id], ...data };
      return Promise.resolve({ data: JSON.parse(JSON.stringify(users[id])) });
    }
    return Promise.reject(new Error('User not found'));
  }
  if (path.match(/^\/branches\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (branches[id]) {
      branches[id] = { ...branches[id], ...data };
      return Promise.resolve({ data: JSON.parse(JSON.stringify(branches[id])) });
    }
    return Promise.reject(new Error('Branch not found'));
  }
  return Promise.reject(new Error('Unknown endpoint'));
}

function axiosDelete(url) {
  const path = url.replace(API_BASE_URL, '');
  if (path.match(/^\/cars\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (cars[id]) {
      delete cars[id];
      return Promise.resolve({ data: null });
    }
    return Promise.reject(new Error('Car not found'));
  }
  if (path.match(/^\/orders\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (orders[id]) {
      delete orders[id];
      return Promise.resolve({ data: null });
    }
    return Promise.reject(new Error('Order not found'));
  }
  if (path.match(/^\/users\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (users[id]) {
      delete users[id];
      return Promise.resolve({ data: null });
    }
    return Promise.reject(new Error('User not found'));
  }
  if (path.match(/^\/branches\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (branches[id]) {
      delete branches[id];
      return Promise.resolve({ data: null });
    }
    return Promise.reject(new Error('Branch not found'));
  }
  if (path.match(/^\/locations\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (locations[id]) {
      delete locations[id];
      return Promise.resolve({ data: null });
    }
    return Promise.reject(new Error('Location not found'));
  }
  if (path.match(/^\/roles\/[^/]+$/)) {
    const id = path.split('/')[2];
    if (roles[id]) {
      delete roles[id];
      return Promise.resolve({ data: null });
    }
    return Promise.reject(new Error('Role not found'));
  }
  return Promise.reject(new Error('Unknown endpoint'));
}

// Mock Authentication
export const mockAuth = {
  currentUser: null,
  signIn(login, password) {
    const user = Object.values(users).find(u => u.login === login && u.password === password);
    if (user) {
      this.currentUser = convertUserToFrontend(user);
      store.user = this.currentUser;
      localStorage.setItem('currentUserId', user.id);
      return Promise.resolve(this.currentUser);
    }
    return Promise.reject(new Error('Invalid credentials'));
  },
  signOut() {
    this.currentUser = null;
    store.user = null;
    localStorage.removeItem('currentUserId');
    return Promise.resolve();
  },
  createUser(login, password) {
    const backendUser = convertUserToBackend({ email: login, login, password, role: 'user' });
    return axiosPost(`${API_BASE_URL}/users`, backendUser).then(response => {
      const frontendUser = convertUserToFrontend(response.data);
      this.currentUser = frontendUser;
      store.user = frontendUser;
      localStorage.setItem('currentUserId', response.data.id);
      return frontendUser;
    });
  }
};

// Car Functions
export function getCars() {
  return axiosGet(`${API_BASE_URL}/cars`).then(response => response.data.map(convertCarToFrontend));
}

export function getRentableCars(model = '') {
  return axiosGet(`${API_BASE_URL}/cars`).then(response => {
    let filteredCars = response.data.filter(car => car.carStatus === 'AVAILABLE');
    if (model) {
      filteredCars = filteredCars.filter(car => car.model.toLowerCase().startsWith(model.toLowerCase()));
    }
    filteredCars.sort((a, b) => a.model.localeCompare(b.model));
    return filteredCars.map(convertCarToFrontend);
  });
}

export function getCar(id) {
  return axiosGet(`${API_BASE_URL}/cars/${id}`).then(response => convertCarToFrontend(response.data));
}

export function updateCarAvailability(id, carStatus) {
  const backendUpdates = {carStatus: carStatus};
  return axiosPatch(`${API_BASE_URL}/cars/${id}`, backendUpdates).then(() => {});
}

export function updateCarPrice(id, new_price) {
  const backendUpdates = {rentalPricePerDay: new_price}
  return axiosPatch(`${API_BASE_URL}/cars/${id}`, backendUpdates).then(() => {});
}

export function addCar(car) {
  const backendCar = convertCarToBackend(car);
  return axiosPost(`${API_BASE_URL}/cars`, backendCar).then(() => {});
}

export function updateCar(id, car) {
  const backendCar = convertCarToBackend(car);
  return axiosPut(`${API_BASE_URL}/cars/${id}`, backendCar).then(() => {});
}

export function deleteCar(id) {
  return axiosDelete(`${API_BASE_URL}/cars/${id}`).then(() => {});
}

export function resetCars() {
  cars = {
    'uuid-car1': { ...cars['uuid-car1'] },
    'uuid-car2': { ...cars['uuid-car2'] },
    'uuid-car3': { ...cars['uuid-car3'] }
  };
}

// Rent Functions
export function getRents() {
  return axiosGet(`${API_BASE_URL}/orders`).then(response => response.data.map(convertOrderToFrontend));
}

export function getUserRents(userId) {
  return axiosGet(`${API_BASE_URL}/orders`).then(response => {
    const userOrders = response.data
      .filter(o => o.user === userId)
      .map(convertOrderToFrontend);
    console.log(users)
    userOrders.sort((a, b) => new Date(b.rent_start) - new Date(a.rent_start));
    return userOrders;
  });
}

export function addRent(orderData) {
  const backendOrder = convertOrderToBackend(orderData);
  return axiosPost(`${API_BASE_URL}/orders`, backendOrder).then(response => response.data.id);
}

export function updateRentStatus(id, newStatus) {
  const backendUpdates = {isPaid: newStatus === 'paid'}
  return axiosPatch(`${API_BASE_URL}/orders/${id}`, backendUpdates).then(() => {});
}

// User Functions
export function getUsers() {
  return axiosGet(`${API_BASE_URL}/users`).then(response => response.data.map(convertUserToFrontend));
}

export function updateUserRole(id, roleName) {
  const roleObj = Object.values(roles).find(r => r.role === roleName);
  if (!roleObj) {
    return Promise.reject(new Error('Role not found'));
  }
  return axiosPatch(`${API_BASE_URL}/users/${id}`, { roles: [roleObj.id] }).then(() => {});
}

// Additional Backend Controller Functions
export function assignCarToBranch(carId, branchId) {
  return axiosPost(`${API_BASE_URL}/cars/${carId}/assign/${branchId}`, {}).then(() => {});
}

export function getCarLocations(id, since) {
  return axiosGet(`${API_BASE_URL}/cars/${id}/locations`, { since }).then(response => response.data);
}

// Initialize current user
const currentUserId = localStorage.getItem('currentUserId');
if (currentUserId && users[currentUserId]) {
  mockAuth.currentUser = convertUserToFrontend(users[currentUserId]);
  store.user = mockAuth.currentUser;
}