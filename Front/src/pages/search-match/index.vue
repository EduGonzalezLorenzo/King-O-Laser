<template>
  <div class="p-10">
    <h1 class="text-5xl lg:text-7xl text-center mt-12 text-white mb-10">
      Search Game
    </h1>
    <allMatches :matches="matches" />
  </div>
</template>
<script setup lang="ts">
import allMatches from "~/components/allMatches.vue";
const matches = ref([]);
const jwt = ref("");

interface UserData {
  id: Number;
  name: String;
  isPublic: Boolean;
  currentPlayers: Number;
  matchCreation: String;
  status: String;
  position: String;
}
useHead({
  title: 'Search Match',
})

onMounted(async () => {
  const localStore = localStorage.getItem("jwt") as string;
  jwt.value = localStore;

  await fetch("http://localhost:8080/matches", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt.value,
    },
  })
    .then((response) => {
      if (response.status !== 200) {
        throw new Error("Error en la solicitud al servidor");
      }
      return response.json();
    })
    .then((data) => {
      matches.value = data.map((userData:UserData) => ({
        id: userData.id,
        name: userData.name,
        isPublic: userData.isPublic ? "Public" : "Private",
        currPlayers: userData.currentPlayers,
        matchCreation: userData.matchCreation,
        status: userData.status,
      }));
    })
    .catch((error) => {
      console.error(error);
    });
});
definePageMeta({
  layout:"game-layout"
})
</script>
