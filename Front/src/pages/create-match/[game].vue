<script setup lang="ts">
import { ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const game_type = route.params.game as string;
const isPublic = ref<boolean>(false);
const boardDisposition = ref<string>("");
const matchName = ref<string>("");
const password = ref<string>("");
const imgPath = ref<string>("");

function isChecked(checked: boolean) {
  isPublic.value = checked;
}

onBeforeMount(async () => {
  if (!["TicTacToe", "LASER_BOARD"].includes(game_type)) {
    await navigateTo(`/games/${game_type}`);
  }
});

async function createMatch() {
  const content = {
    isPublic: !isPublic.value,
    metadata: boardDisposition.value,
    game: game_type,
    password:password.value,
    matchName: matchName.value,
  };
  const response = await fetch("http://localhost:8080/match", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(content),
  });
  if (response.ok) {
    console.log("Match created!");
    return await navigateTo({ path: "/select-game" });
  } else {
    console.error("Error creating match.");
  }
}
function showBoard(){
  console.log(boardDisposition.value)
  switch(boardDisposition.value){
    case 'ace':
      imgPath.value = "/img/kingolaser/AceBoard.jpg"
      break;
    
    case 'std':
      imgPath.value = "/img/kingolaser/StandardBoard.jpg"
    break
    case 'cur':
      imgPath.value = "/img/kingolaser/CuriosityBoard.jpg"
      break
      case 'gr':
      imgPath.value = "/img/kingolaser/GrailBoard.jpg"
      break
      case 'sh':
      imgPath.value = "/img/kingolaser/SophieBoard.jpg"
      break
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
              v-if="game_type === 'King-0-Las3r'"
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
