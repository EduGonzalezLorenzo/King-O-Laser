<template>
  <div class="grid grid-cols-2">
    <div class="w-2/3">
      <div class="grid grid-cols-1 gap-12 game_container">
        <div class="mt-0 mb-auto">
          <UserProfileGameCard />
          <StartedMatchList />
        </div>
      </div>
    </div>
    <div class="-ml-80 grid h-screen place-items-center canvas_container justify-self-center mr-auto">
      <Grid
        @send-movement="sendMovement"
      />
    </div>
    <div
      v-if="openSendMenu"
      class="send_menu fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
    >
      <p> R{{ newRotationValue }}</p>
      <p> MX{{ newSelectedMovementX }}</p>
      <p> MY{{ newSelectedMovementY }}</p>
      <p> PX{{ newSelectedPieceX }}</p>
      <p> PY{{ newSelectedPieceY }}</p>

      <button
        class="confirm_button hover:bg-green-300"
        @click="fetchMovement"
      >
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
import Grid from "~/components/GridComp.vue";
import UserProfileGameCard from "~/components/UserProfileGameCard.vue";
import StartedMatchList from "~/components/StartedMatchList.vue";

const route = useRoute()

const newSelectedPieceY = ref(0);
const newSelectedPieceX = ref(0);
const newSelectedMovementY = ref(0);
const newSelectedMovementX = ref(0);
const newRotationValue = ref("");
const openSendMenu = ref(false);
const id = ref(route.params.id);
const jwt = ref<String>("")


async function fetchMovement() {
  const currentPosY = newSelectedPieceY.value
  const currentPosX = newSelectedPieceX.value
  const newPosY = newSelectedMovementY.value
  const newPosX = newSelectedMovementX.value
  const rotateTo = newRotationValue.value
  const localStore = localStorage.getItem("jwt")
  jwt.value = localStore as String;
  await fetch("http://localhost:8080/match/" + id.value + "/action", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt.value,
    },
    body: JSON.stringify({
      currentPosY,
      currentPosX,
      newPosY,
      newPosX,
      rotateTo
    }),
  });
  location.reload()
}


const sendMovement = (
  selectedPieceY: number,
  selectedPieceX: number,
  selectedMovementY: number,
  selectedMovementX: number,
  rotationValue: string
) => {
  newSelectedPieceY.value = selectedPieceY;
  newSelectedPieceX.value = selectedPieceX;
  newSelectedMovementY.value = selectedMovementY;
  newSelectedMovementX.value = selectedMovementX;
  newRotationValue.value = rotationValue;
  openSendMenu.value = true;
};

const closeSendMenu = () => {
  openSendMenu.value = false;
};
</script>
<style scoped>
.game_container {
  display: flex;
  align-items: flex-start;
  align-items: center;
  width: 100%;
  height: 100%;
}

.canvas_container {
  position: relative;
}

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
}
</style>
