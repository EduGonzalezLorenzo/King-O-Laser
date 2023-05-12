<template>
  <div class="grid grid-cols-4">
    <div class="justify-self-start">
      <UserProfileGameCard />
      <StartedMatchList />
    </div>
    <div class="grid h-screen place-items-center">
      <div class="canvas_container">
        <h1 class="text-center">
          My Grid Component
        </h1>
        <h1 class="text-center">
          X: {{ posX }}
        </h1>
        <h1 class="text-center">
          Y: {{ posY }}
        </h1>
        <div class="justify-self-center">
          <Grid @sendPosition="showClick" />
        </div>
      </div>
    </div>
  </div>
</template>


  
<script setup lang="ts">
import Grid from '~/components/GridComp.vue';
import UserProfileGameCard from '~/components/UserProfileGameCard.vue';
import StartedMatchListVue from '~/components/StartedMatchList.vue';
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