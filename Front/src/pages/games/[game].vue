<template>
  <div class="grid grid-cols-4 game_container">
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
      <div class="send_menu" v-if="openSendMenu">
      <h1>SelectedPieceY: {{ newSelectedPieceY }}</h1>
      <h1>SelectedPieceX: {{ newSelectedPieceX }}</h1>
      <h1>SelectedMovementY: {{ newSelectedMovementY }}</h1>
      <h1>SelectedMovementY: {{ newSelectedMovementX }}</h1>
      <button class="confirm_button">Confirm Movement</button>
      <button class="cancel_button" @click="closeSendMenu">Cancel</button>
    </div>
  </div>
</template>


  
<script setup lang="ts">
import Grid from '~/components/GridComp.vue';
import UserProfileGameCard from '~/components/UserProfileGameCard.vue';
import StartedMatchListVue from '~/components/StartedMatchList.vue';

const newSelectedPieceY = ref(0)
const newSelectedPieceX = ref(0)
const newSelectedMovementY = ref(0)
const newSelectedMovementX = ref(0)
const openSendMenu = ref(false)


const sendMovement = (selectedPieceY: number, selectedPieceX: number, selectedMovementY: number, selectedMovementX: number) => {
  newSelectedPieceY.value = selectedPieceY
  newSelectedPieceX.value = selectedPieceX
  newSelectedMovementY.value = selectedMovementY
  newSelectedMovementX.value = selectedMovementX
  openSendMenu.value = true

};

const closeSendMenu = () => {
  openSendMenu.value = false
}

</script>
<style scoped>
.game_container {
  /* position: relative; */
  display: flex;
  align-items: flex-start;
  align-items: center;
  width: 100%;
  height: 100%;
}

.canvas_container {
  position: relative;
}

/* h1 {
  font-size: 35px;
} */

.send_menu {
  background-color: lightgrey;
  padding: 1em;
  display: flex;
  flex-direction: column;
}

button {
  border: 1px solid black;
  padding: 1em;
  margin: 0.5em;
  /* font-size: 25px; */
}

button:hover {
  background-color: lightgreen;
}
</style>