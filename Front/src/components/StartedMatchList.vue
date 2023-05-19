<template>
  <div class="flex flex-col bg-slate-300 jusify-start">
    <ul class="grid grid-cols-1">
      <li
        v-for="(game, index) in users"
        :key="index"
        class="border-white border-4 p-4"
      >
        <div
          class="flex items-start" 
          @click="goMatch(game.id)"
        >
          <div>
            <p class="font-bold">
              {{ game.name }}
            </p>
            <p class="font-bold">
              Players: {{ game.currPlayers }}/2
            </p>
            <p
              v-if="game.status === 'WAITING'"
              class="font-bold bg-yellow-300 p-2 rounded"
            >
              {{ game.status }}
            </p>
            <p
              v-else
              class="font-bold"
            >
              {{ game.status }}
            </p>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup>
const users = ref([]);

const jwt = ref(localStorage.getItem("jwt"));

await fetch("http://localhost:8080/match", {
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
    users.value = data.map((userData) => ({
      id: userData.id,
      name: userData.name,
      isPublic: userData.isPublic,
      currPlayers: userData.currentPlayers,
      matchCreation: userData.matchCreation,
      status: userData.status,
    }));
  });

function goMatch(id) {
  navigateTo(`/games/` + id);
}
</script>
