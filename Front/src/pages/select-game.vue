<template>
  <div
    class="max-h-screen"
  >
    <h1 class="md:text-7xl text-5xl text-center md:mb-28 mt-10 mb-10 text-white">
      Select a Game
    </h1>
    <div
      class="grid grid-cols-2 lg:grid-cols-3 gap-10 mx-auto max-w-screen-lg p-6"
    >
      <div
        v-for="image in images"
        :key="image.title"
        class="lg:bg-white lg:w-full lg:mt-9 max-w-full mx-auto mt-5 rounded-lg cursor-pointer hover:shadow-lg hover:shadow-white hover:border-4 hover:border-green-400 hover:text"
        @click="handleClick(image.title)"
      >
        <img
          :src="image.url"
          :alt="image.alt"
          class="lg:w-full max-w-full mx-auto rounded-lg"
        >
        <h3 class="text-2xl font-medium mt-2 text-center hidden lg:block">
          {{ image.title }}
        </h3>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import useImages from "@/utils/images";
const { images } = useImages();

function handleClick(title: string) {
  return navigateTo({ path: `/create-match/${title}` });
}
definePageMeta({
  layout:"game-layout"
})
useHead({
  title: 'Games',
})
</script>
