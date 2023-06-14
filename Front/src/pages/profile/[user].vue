<template>
  <div>
    <div v-if="loading">
      <LoadingComponent />
    </div>
    <div
      v-else
      class="grid grid-cols-1 md:grid-cols-1 lg:grid-cols-3 gap-6"
    >
      <div class="col-span-1 md:col-span-1 lg:col-span-1">
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
      <div
        id="info"
        class="col-span-1 md:col-span-1 lg:col-span-2 flex justify-center items-center h-screen"
      >
        <div v-if="!isMobile">
          <div class="lg:max-w-full max-w-md">
            <p
              class="lg:text-3xl text-sm p-2 px-5 font-semibold text-center bg-gray-200 rounded text-black flex items-center justify-center"
            >
              Crea una partida pulsando
              <span class="m-2">
                <svg
                  width="1em"
                  height="1em"
                  viewBox="0 0 1024 1024"
                  fill="currentColor"
                  stroke="none"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                >
                  <path
                    d="M512 0C229.232 0 0 229.232 0 512c0 282.784 229.232 512 512 512c282.784 0 512-229.216 512-512C1024 229.232 794.784 0 512 0zm0 961.008c-247.024 0-448-201.984-448-449.01c0-247.024 200.976-448 448-448s448 200.977 448 448s-200.976 449.01-448 449.01zM736 480H544V288c0-17.664-14.336-32-32-32s-32 14.336-32 32v192H288c-17.664 0-32 14.336-32 32s14.336 32 32 32h192v192c0 17.664 14.336 32 32 32s32-14.336 32-32V544h192c17.664 0 32-14.336 32-32s-14.336-32-32-32z"
                  /></svg></span>
              o únete a una en
              <span class="m-2">
                <svg
                  width="1em"
                  height="1em"
                  viewBox="0 0 12 12"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="1"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  transform="rotate(90 0 0)"
                >
                  <path
                    d="M5 1a4 4 0 1 0 2.248 7.31l2.472 2.47a.75.75 0 1 0 1.06-1.06L8.31 7.248A4 4 0 0 0 5 1ZM2.5 5a2.5 2.5 0 1 1 5 0a2.5 2.5 0 0 1-5 0Z"
                  /></svg></span>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import UserProfileGameCard from "~/components/UserProfileGameCard.vue";
import StartedMatchList from "~/components/StartedMatchList.vue";
import LoadingComponent from "~/components/LodingComp.vue";
import api from '@/utils/axios';

const loading = ref(false);
const isMobile = ref(false);
const jwt = ref<String>("");
const showStartedMatchList = ref(true);
const users = ref([]);

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
onMounted(async () => {
  loading.value = true;
  checkMobile();
  window.addEventListener("resize", checkMobile);
  const localStore = localStorage.getItem("jwt");
  jwt.value = localStore as string;

  try {
    const responsePlayer = await api.get("/getPlayer");

    const dataPlayer = responsePlayer.data;

    user.value.name = dataPlayer.playerName;
    user.value.loggedIn = dataPlayer.loggedIn;
    user.value.profileImg = dataPlayer.profileImg;
  } catch (error) {
    console.error(error);
  }

  try {
    const responseMatch = await api.get("/match");

    if (responseMatch.status !== 200) {
      throw new Error("Error en la solicitud al servidor");
    }

    const dataMatch = responseMatch.data;

    users.value = dataMatch.map((userData: UserData) => ({
      id: userData.id,
      name: userData.name,
      isPublic: userData.isPublic ? "Public" : "Private",
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

function checkMobile() {
  isMobile.value = window.innerWidth < window.innerHeight;
}
useHead({
  title: `Your Profile` ,
})
</script>

<style>
body {
  background-color: #171515f5 !important;
  background-image: none !important;
}
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
