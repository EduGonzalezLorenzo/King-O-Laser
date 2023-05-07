<template>
  <div class="canvas_container">
    <h1>My Grid Component</h1>
    <h1>X: {{ posX }}</h1>
    <h1>Y: {{ posY }}</h1>
    <Grid @sendPosition="showClick" />
  </div>
</template>
  
<script setup lang="ts">
import Grid from '~/components/GridComp.vue';

const posY = ref(0);
const posX = ref(0);


const showClick = (newPosY: number, newPosX: number) => {
  posY.value = newPosY
  posX.value = newPosX
  console.log("from game: " + posY.value + ":" + posX.value)
};

</script>
<style>
.canvas_container {
display: flex;
flex-direction: column;
align-items: flex-start;
}
</style>