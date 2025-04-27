<script>
import IconEMail from './icons/IconEMail.vue';
import IconMenu from './icons/IconMenu.vue';
import IconPhone from './icons/IconPhone.vue';
import NavItem from './NavItem.vue';
import { RouterLink } from 'vue-router';

export default {
  components: { NavItem, IconEMail, IconPhone, IconMenu, RouterLink },
  data() {
    return {
      isNavDrawerVisible: false,
    };
  },
};
</script>

<template>
  <header
    class="col-start-1 sm:col-start-3 col-end-13 row-span-1 flex items-center justify-end md:justify-between ml-0"
  >
    <nav class="hidden md:block">
      <ul class="flex flex-row first:-ml-8">
        <NavItem to="/" class="p-8">Main Page</NavItem>
        <NavItem to="/list" class="p-8">Car List</NavItem>
      </ul>
    </nav>
    <div
      class="hidden lg:flex flex-row items-center gap-2 xl:gap-4 fill-white text-zinc-400 font-semibold"
      :class="{ 'lg:hidden': $route.fullPath !== '/' }"
    >
      <span class="flex items-center gap-2">
        <IconEMail class="w-6 h-6" />car@rent.pl
      </span>
      <span class="h-10 w-px bg-zinc-400 mx-1"></span>
      <span class="flex items-center gap-2 mr-8">
        <IconPhone class="w-6 h-6" />123 456 789
      </span>
    </div>
    <button
      class="block md:hidden mx-4 p-4 shrink-0"
      @click="isNavDrawerVisible = true"
    >
      <IconMenu class="w-8 h-8 sm:w-12 sm:h-12 fill-white" />
    </button>
    <Teleport to="body">
      <Transition name="fade">
        <div
          v-if="isNavDrawerVisible"
          class="fixed inset-0 bg-black/50 z-10"
          @click="isNavDrawerVisible = !isNavDrawerVisible"
        ></div>
      </Transition>
      <Transition name="slide-fade">
        <div
          v-if="isNavDrawerVisible"
          class="fixed top-0 right-0 h-full bg-zinc-800 z-20 flex flex-col"
        >
          <RouterLink
            to="/"
            class="mx-4 sm:mx-6 my-2 sm:my-4 text-2xl sm:text-3xl font-semibold tracking-[0.5ch] text-white logo__text"
          >
            CAR RENT
          </RouterLink>
          <ul class="flex flex-col items-center justify-center gap-2 mt-4">
            <NavItem to="/" class="p-4">Main Page</NavItem>
            <NavItem to="/list" class="p-4">Car List</NavItem>
          </ul>
        </div>
      </Transition>
    </Teleport>
  </header>
</template>

<style scoped>
.btn {
  box-shadow: 0px 10px 60px 5px rgb(239, 68, 68, 0.3);
}

.logo__text {
  text-shadow: 2px 2px 0px rgb(239, 68, 68);
}

/* Slide and Fade */
.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: opacity 150ms ease-in-out, transform 150ms ease-in-out;
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(100%);
  opacity: 0;
}

.slide-fade-enter-to {
  transform: translateX(0%);
  opacity: 1;
}

/* Fade */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 150ms ease-in-out;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-to {
  opacity: 1;
}
</style>