<template>
  <div class="grid grid-cols-3 gap-24 game_container">
    <div class="col-span-1 mt-0 mb-auto">
      <UserProfileGameCard />
      <StartedMatchList />
    </div>
    <div class="col-span-2 grid h-screen place-items-center">
      <div class="canvas_container">
        <div class="justify-self-center">
          <Grid @send-movement="sendMovement" />
        </div>
      </div>
    </div>
    <div
      v-if="openSendMenu"
      class="send_menu fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
    >
      <button class="confirm_button hover:bg-green-300">
        Confirm Movement
      </button>
      <button
        class="cancel_button hover:bg-red-300"
        @click="closeSendMenu"
      >
        Cancel
      </button>
    </div>
  </div>
</template>



  
<script setup lang="ts">
import Grid from '~/components/GridComp.vue';
import UserProfileGameCard from '~/components/UserProfileGameCard.vue';
import StartedMatchList from '~/components/StartedMatchList.vue';

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
  background-color: rgba(211, 211, 211, 0.516);
  padding: 1em;
  display: flex;
  flex-direction: column;

}

button {
  background-color: azure;
  border: 1px solid black;
  padding: 1em;
  margin: 0.5em;
  /* font-size: 25px; */
}
</style>