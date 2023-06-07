<template>
  <div>
    <div v-if="loading">
      <LoadingComponent />
    </div>
    <UserProfileGameCard
      :user="user"
      @dropdown-click="showStartedMatchList = !showStartedMatchList"
    />
    <StartedMatchList
      v-if="showStartedMatchList"
      :users="users"
    />

    <slot />
  </div>
</template>
<script setup lang="ts">
import LoadingComponent from "~/components/LodingComp.vue";

const jwt = ref<String>("");
const loading = ref(false);
const showStartedMatchList = ref(false);
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
      console.log(data)
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
</script>
