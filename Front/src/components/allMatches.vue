<template>
  <div class="flex flex-col bg-slate-300 jusify-start">
    <ul class="grid grid-cols-1">
      <li
        v-for="(game, index) in props.matches"
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

<script setup lang="ts">
const jwt = ref<String>("");
const msg = ref("");
const password = ref("")
onMounted(() =>{
  const localStore = localStorage.getItem("jwt");
  jwt.value = localStore as String;
})
const props = defineProps({
  matches: {
    type: Object,
    required: false,
    default: () => ({
      id: 0,
      name: "",
      isPublic: false,
      currentPlayers: 0,
      matchCreation: "",
      status: "",
      position: "",
    }),
  },
});
async function goMatch(id:Number) {
  await fetch("http://localhost:8080/match/" + id, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt.value,
    },
    body: JSON.stringify({ password: password.value }),
  }).then((response) => {
    if (response.status !== 200) {
      throw new Error("Error en la solicitud al servidor");
    }
    return response.json();
  });

  navigateTo(`/games/` + id);
}
</script>
