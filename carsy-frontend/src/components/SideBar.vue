<script>
import SideBarItem from './SideBarItem.vue';
import SideBarLogo from './SideBarLogo.vue';
import IconCar from './icons/IconCar.vue';

export default {
  components: { SideBarItem, SideBarLogo, IconCar },
};
</script>

<template>
  <aside class="col-span-1 row-span-full bg-zinc-800 hidden sm:block">
    <ul class="flex flex-col h-full">
      <SideBarLogo href="/" class="py-3" />
      <div class="mb-auto">
        <SideBarItem to="/list" tooltip="Car List">
          <IconCar />
        </SideBarItem>
      </div>
    </ul>
  </aside>
</template>

<style></style>