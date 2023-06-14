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
    <div class="col-span-2 m-auto mt-10 lg:mt-0">
      <div
        class="grid lg:h-screen items-center canvas_container justify-center"
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
import api from '@/utils/axios';
import Grid from '~/components/GridComp.vue';
import LoadingComponent from "~/components/LodingComp.vue";
import { useRoute } from "vue-router";

const users = ref([]);
const route = useRoute();

const newSelectedPieceY = ref(0);
const newSelectedPieceX = ref(0);
const newSelectedMovementY = ref(0);
const newSelectedMovementX = ref(0);
const newRotationValue = ref('');
const openSendMenu = ref(false);
const id = ref("");
const loading = ref(false);
const showStartedMatchList = ref(true);

const user = ref({
  name: '',
  loggedIn: false,
  profileImg: '',
});

interface UserData {
  id: number;
  name: string;
  isPublic: boolean;
  currentPlayers: number;
  matchCreation: string;
  status: string;
  position: string;
}

async function fetchMovement() {
  const currentPosY = newSelectedPieceY.value;
  const currentPosX = newSelectedPieceX.value;
  const newPosY = newSelectedMovementY.value;
  const newPosX = newSelectedMovementX.value;
  const rotateTo = newRotationValue.value;

  try {
    await api.post(`match/${id.value}/action`, {
      currentPosY,
      currentPosX,
      newPosY,
      newPosX,
      rotateTo,
    });

    location.reload();
  } catch (error) {
    console.error(error);
  }
}

onMounted(async () => {
  loading.value = true;
  id.value = route.params.id as string

  try {
    const response = await api.get('getPlayer');

    const data = response.data;

    user.value.name = data.playerName;
    user.value.loggedIn = data.loggedIn;
    user.value.profileImg = data.profileImg;
  } catch (error) {
    console.error(error);
  }

  try {
    const response = await api.get('match');

    const data = response.data;

    users.value = data.map((userData: UserData) => ({
      id: userData.id,
      name: userData.name,
      isPublic: userData.isPublic ? 'Public' : 'Private',
      currPlayers: userData.currentPlayers,
      matchCreation: userData.matchCreation,
      status: userData.status,
      position: userData.position,
    }));
  } catch (error) {
    console.error(error);
  }

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
useHead({
  title: `Game` ,
})
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
