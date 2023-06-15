<template>
  <div class="flex flex-col bg-gray-700 justify-start">
    <div v-if="deleteMenu">
      <div class="modal">
        <div
          v-if="deleteMenu"
          class="modal_message"
        >
          <p class="font-bold text-white">
            {{ deleteMessage.valueOf() }}
          </p>
          <div class="button_container">
            <button
              class="button confirm_delete"
              @click="deleteMatch(idToDelete.valueOf())"
            >
              Yes
            </button>
            <button
              class="button abort_delete"
              @click="closeDeleteMenu()"
            >
              No
            </button>
          </div>
        </div>
      </div>
    </div>
    <ul class="grid grid-cols-1">
      <li
        v-for="(game, index) in props.users"
        :key="index"
        class="border-white border-4 p-4 flex-row"
      >
        <div class="flex flex-end items-end justify-end cursor-pointer">
          <div
            class="delete_button"
            @click="(event: Event) => openDeleteMenu(game)"
          >
            <p>X</p>
          </div>
        </div>
        <div
          class="flex items-start cursor-pointer"
          @click="goMatch(game.id)"
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
                v-if="
                  game.status === 'WAITING' ||
                    game.status === 'PLAYER_ONE_WIN' ||
                    game.status === 'PLAYER_TWO_WIN'
                "
                class="font-bold bg-yellow-400 p-2 rounded mr-4"
              >
                {{ game.status }}
              </p>
              <p
                v-else
                class="font-bold text-white"
              >
                {{
                  (game.status === "PLAYER_ONE_TURN" &&
                    game.position === "P1") ||
                    (game.status === "PLAYER_TWO_TURN" && game.position === "P2")
                    ? "YOUR TURN"
                    : "OPPONENT TURN"
                }}
              </p>
              <p
                v-if="game.isPublic === 'Private'"
                class="font-bold bg-red-500 p-2 rounded ml-5"
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
import api from "@/utils/axios";

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


function goMatch(id: number) {
  navigateTo(`/games/` + id);
}

const deleteMenu = ref(false);
const idToDelete = ref(0);
const deleteMessage = ref("");

function openDeleteMenu(game: any) {
  deleteMenu.value = true;
  idToDelete.value = game.id;
  if (
    game.status === "WAITING" ||
    game.status === "PLAYER_ONE_WIN" ||
    game.status === "PLAYER_TWO_WIN"
  ) {
    deleteMessage.value = "¿Do you want to delete the match?";
  } else {
    deleteMessage.value =
      "The match is not finished. Do you want to abandon the match? Your opponent will win";
  }
}

function closeDeleteMenu() {
  deleteMenu.value = false;
}

async function deleteMatch(id: Number) {
  const jwt = localStorage.getItem("jwt");
  await fetch(`http://localhost:8080/delete/${id}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt,
    },
  }).then(async (response) => {
    if (response.ok) {
      await api
        .get("http://localhost:8080/getPlayer")
        .then(async (response) => {
          return await response.data;
        })
        .then((data) => {
          window.location.href=`http://localhost:3000/profile/${data.playerName}`
        });
    } else {
      navigateTo("error/matchDoesNotExists");
    }
  });
}
</script>
<style>
.delete_button {
  position: relative;
  background-color: darkgray;
  color: white;
  border-radius: 50%;
  display: flex;
  width: 25px;
  height: 25px;
  justify-content: center;
  align-items: center;
  z-index: 99;
}

.delete_button:hover {
  background-color: red;
}

.modal {
  background-color: rgb(0, 0, 0, 0.5);
  color: white;
  width: 100vw;
  height: 100vh;
  position: absolute;
  top: 0;
  z-index: 100;
  display: flex;
  justify-content: center;
  align-items: center;
}

.button {
  color: black;
  border-radius: 25%;
  padding: 0.5em 1em;
}

.confirm_delete:hover {
  background-color: lightcoral;
}

.abort_delete:hover {
  background-color: lightgreen;
}

.button_container {
  display: flex;
  justify-content: center;
}
</style>
