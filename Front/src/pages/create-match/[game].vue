<script setup lang="ts">
import { ref } from "vue";
import { useRoute } from "vue-router";

const msg = ref("");
const route = useRoute();
const game_type = route.params.game as string;
const isPublic = ref<boolean>(false);
const boardDisposition = ref<string>("");
const matchName = ref<string>("");
const password = ref<string>("");
const imgPath = ref<string>("");
const jwt = ref<String>("")

function isChecked(checked: boolean) {
  isPublic.value = checked;
}

onMounted(async () => {
  if (!["TicTacToe", "LASER_BOARD"].includes(game_type)) {
    await navigateTo(`/select-game`);
  }
});

async function createMatch(event:Event) {
  event.preventDefault();
  const localStore = localStorage.getItem("jwt")
  jwt.value = localStore as String;
  const content = {
    password: password.value,
    isPublic: !isPublic.value,
    metadata: boardDisposition.value,
    game: game_type,
    matchName: matchName.value,
  };

  await fetch("http://localhost:8080/match", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt.value,
    },
    body: JSON.stringify(content),
  }).then((response) => {
    if (response.ok) {
      navigateTo(`/profile/`+"hola");
    } else {
      msg.value = "Error creating match.";
    }
  });
}
function showBoard() {
  console.log(boardDisposition.value);
  switch (boardDisposition.value) {
    case "ace":
      imgPath.value = "/img/kingolaser/AceBoard.webp";
      break;
    case "std":
      imgPath.value = "/img/kingolaser/StandardBoard.webp";
      break;
    case "cur":
      imgPath.value = "/img/kingolaser/CuriosityBoard.webp";
      break;
    case "gr":
      imgPath.value = "/img/kingolaser/GrailBoard.webp";
      break;
    case "sh":
      imgPath.value = "/img/kingolaser/SophieBoard.webp";
      break;
  }
}
</script>
<template>
  <div>
    <div
      id="home"
      class="text-black grid grid-cols-2 h-screen"
    >
      <div>
        <h1 class="text-7xl text-center mt-28 text-white">
          Match
        </h1>

        <form
          id="match"
          class="bg-white rounded-lg m-10 p-10 h-[45%]"
          @submit="createMatch"
        >
          <div class="mb-6">
            <label
              for="matchName"
              class="block mb-2 text-sm font-medium text-black"
            >Match Name</label>
            <input
              id="matchName"
              v-model="matchName"
              type="text"
              class="shadow-sm bg-gray-50 border border-gray-300 text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
              placeholder="My Game"
              required
            >
          </div>

          <div class="mb-6">
            <select
              v-if="game_type === 'LASER_BOARD'"
              id="boardDisposition"
              v-model="boardDisposition"
              name="boardDisposition"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg p-2 mb-10"
              required
              @change="() => showBoard()"
            >
              <option
                value=""
                disabled
                selected
              >
                Select Board Disposition
              </option>
              <option value="std">
                Standard
              </option>
              <option value="ace">
                Ace
              </option>
              <option value="cur">
                Curiosity
              </option>
              <option value="gr">
                Grail
              </option>
              <option value="sh">
                Sophie
              </option>
            </select>
            <div class="mb-6">
              <input
                id="isPrivate"
                type="checkbox"
                name="isPrivate"
                @change="(event:Event) => isChecked((event.target as HTMLInputElement).checked)"
              >
              <label
                for="isPrivate"
                class="inline-block ml-2 text-sm font-medium text-black"
              >Private Match</label>
            </div>
            <div v-if="isPublic">
              <div class="mb-6">
                <label
                  for="password"
                  class="block mb-2 text-sm font-medium text-gray-900"
                >Your password</label>
                <input
                  id="password"
                  v-model="password"
                  type="password"
                  class="shadow-sm bg-gray-50 border border-gray-300 text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
                  required
                  placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
                >
              </div>
            </div>
            <p
              class="text-red-600 font-bold text-lg m-4 bg-gray-700 px-3 py-1 ml-0 rounded"
            >
              {{ msg }}
            </p>
            <div class="mb-6">
              <button
                id="submit"
                type="submit"
                class="signup font-bold py-2 px-4 rounded"
                @click="createMatch"
              >
                Create Match
              </button>
            </div>
          </div>
        </form>
      </div>
      <div class="m-10 overflow-y-hidden justify-self-center">
        <img
          :src="imgPath"
          class="max-h-[100%]"
        >
      </div>
    </div>
  </div>
</template>
