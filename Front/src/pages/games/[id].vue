<template>
  <div v-if="loading">
    <LoadingComponent />
  </div>
  <div
    v-else
    class="grid grid-cols-1 md:grid-cols-1 lg:grid-cols-3"
  >
    <div class="col-span-1 md:col-span-1 lg:col-span-1 w-full">
      <div class="flex flex-col h-full">
        <UserProfileGameCard
          :user="user"
          @dropdown-click="showStartedMatchList = !showStartedMatchList"
        />
        <StartedMatchList
          v-if="showStartedMatchList"
          :users="users"
        />
      </div>
    </div>
    <div class="col-span-2 ml-auto mr-auto">
      <div
        class="grid mt-10 lg:h-screen items-center canvas_container justify-center"
      >
        <Grid @send-movement="sendMovement" />
      </div>
    </div>

    <div
      v-if="openSendMenu"
      class="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
    >
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

const users = ref([]);
const route = useRoute();

const newSelectedPieceY = ref(0);
const newSelectedPieceX = ref(0);
const newSelectedMovementY = ref(0);
const newSelectedMovementX = ref(0);
const newRotationValue = ref("");
const openSendMenu = ref(false);
const id = ref(route.params.id);
const jwt = ref<String>("");
const loading = ref(false);
const showStartedMatchList = ref(false);

const user = ref({
  name: String,
  loggedIn: Boolean,
  profileImg: String,
});
interface UserData {
  id: Number;
  name: String;
  isPublic: Boolean;
  currentPlayers: Number;
  matchCreation: String;
  status: String;
  position: String;
}
async function fetchMovement() {
  const currentPosY = newSelectedPieceY.value;
  const currentPosX = newSelectedPieceX.value;
  const newPosY = newSelectedMovementY.value;
  const newPosX = newSelectedMovementX.value;
  const rotateTo = newRotationValue.value;

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
      rotateTo,
    }),
  });
  location.reload();
}
onMounted(async () => {
  loading.value = true;
  const localStore = localStorage.getItem("jwt");
  jwt.value = localStore as String;
  await fetch("http://localhost:8080/getPlayer", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt.value,
    },
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      user.value.name = data.playerName;
      user.value.loggedIn = data.loggedIn;
      user.value.profileImg = data.profileImg;
    });
  await fetch("http://localhost:8080/match", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt.value,
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Error en la solicitud al servidor");
      }
      return response.json();
    })
    .then((data) => {
      users.value = data.map((userData: UserData) => ({
        id: userData.id,
        name: userData.name,
        isPublic: userData.isPublic ? "Public" : "Private",
        currPlayers: userData.currentPlayers,
        matchCreation: userData.matchCreation,
        status: userData.status,
        position: userData.position,
      }));
    })
    .catch((error) => {
      console.error(error);
    });
  loading.value = false;
});
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
