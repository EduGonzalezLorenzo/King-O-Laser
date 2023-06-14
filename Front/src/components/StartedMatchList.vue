<template>
  <div class="flex flex-col bg-gray-700 justify-start">
    <ul class="grid grid-cols-1">
      <li
        v-for="(game, index) in props.users"
        :key="index"
        class="border-white border-4 p-4"
      >
        <div
          class="flex items-start cursor-pointer"
          @click="goMatch(game.id, index)"
        >
          <div>
            <p class="font-bold text-white">
              {{ game.name }}
            </p>
            <p class="font-bold text-white">
              Players: {{ game.currPlayers }}/2
            </p>
            <div class="flex flex-row">
              <p
                v-if="game.status === 'WAITING' || game.status === 'PLAYER_ONE_WIN' || game.status === 'PLAYER_TWO_WIN'"
                class="font-bold bg-yellow-400 p-2 rounded mr-4"
              >
                {{ game.status }}
              </p>
              <p
                v-else
                class="font-bold text-white"
              >
                {{
                  (game.status === "PLAYER_ONE_TURN" && game.position === "P1")
                    || (game.status === "PLAYER_TWO_TURN" && game.position === "P2")
                    ? "YOUR TURN" : "OPPONENT TURN"
                }}
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
const props = defineProps({
  users: {
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
function goMatch(id) {
  navigateTo(`/games/` + id);
}
</script>
