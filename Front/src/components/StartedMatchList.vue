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
          @click="goMatch(game.id, index)"
        >
          <div>
            <p class="font-bold">
              {{ game.name }}
            </p>
            <p class="font-bold">
              Players: {{ game.currPlayers }}/2
            </p>
            <div class="flex flex-row">
              <p
                v-if="game.status === 'WAITING'"
                class="font-bold bg-yellow-300 p-2 rounded mr-4"
              >
                {{ game.status }}
              </p>
              <p
                v-else
                class="font-bold"
              >
                {{ game.status }}
              </p>
              <p
                v-if="game.isPublic === 'Private'"
                class="font-bold bg-red-500 p-2 rounded"
              >
                {{ game.isPublic }}
              </p>
            </div>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup>
const users = ref([]);

const jwt = ref("")

onMounted(async () =>{
  const localStore = localStorage.getItem("jwt")
  jwt.value = localStore;

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
    users.value = data.map((userData) => ({
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
  })
})

const emit = defineEmits(['sendPosition'])


function goMatch(id, index) {
  console.log("StratedMatchList: "+users.value[index].position, users.value[index].status)
  emit('sendPosition', users.value[index].position, users.value[index].status)
  navigateTo(`/games/` + id);
}
</script>
