<template>
  <div class="loading-component flex items-center justify-center h-screen">
    <div
      v-if="loading"
      class="spinner bg-gray-200 border-4 border-gray-300 border-t-[#1E40AF] rounded-full w-20 h-20 animate-spin"
    />
    <div v-else>
      <slot />
    </div>
  </div>
</template>
  
  <script setup>
 const loading = ref(true)
 
    onMounted(() => {
      setTimeout(() => {
        loading.value = false;
      }, 2000);
    }) 
  </script>