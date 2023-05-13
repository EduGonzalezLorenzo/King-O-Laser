<template>
  <div class="canvas_container">
    <Grid @sendMovement="sendMovement" />
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
.canvas_container {
  display: flex;
  align-items: flex-start;
  /* justify-content: center; */
  align-items: center;
}

h1 {
  font-size: 35px;
}

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
  font-size: 25px;
}

button:hover {
  background-color: lightgreen;
}
</style>