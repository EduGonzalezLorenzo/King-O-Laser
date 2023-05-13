<template>
  <div>
    <h1 class="text-7xl text-center mt-40 mb-28">
      Select a Game
    </h1>
    <div class="grid grid-cols-3 gap-10 mx-auto max-w-screen-lg">
      <div
        v-for="(image) in images"
        :key="image.title"
        class="bg-white w-full max-w-full mx-auto mt-9 rounded-lg"
        @click="handleClick(image.title)"
      >
        <img
          :src="image.url"
          :alt="image.alt"
          class="w-full max-w-full mx-auto rounded-lg"
        >
        <h3 class="text-2xl font-medium mt-2 text-center">
          {{ image.title }}
        </h3>
      </div>
    </div>
  </div>
</template>



<script setup lang="ts">
 import useImages  from '@/utils/images'
 const {images} = useImages(); 


function handleClick(title: string) {
  return navigateTo({ path: `/create-match/${title}` });
}

</script>
