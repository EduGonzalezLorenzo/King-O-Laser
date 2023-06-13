<script setup lang="ts">
import { useRoute } from "vue-router";
import api from "@/utils/axios.ts";

const msg = ref("");
const route = useRoute();
const game_type = route.params.game as string;
const isPublic = ref<boolean>(false);
const boardDisposition = ref<string>("");
const matchName = ref<string>("");
const password = ref<string>("");
const imgPath = ref<string>("");

function isChecked(checked: boolean) {
  password.value = "";
  isPublic.value = checked;
}

onMounted(async () => {
  if (!["TicTacToe", "LASER_BOARD"].includes(game_type)) {
    await navigateTo(`/select-game`);
  }
});

async function createMatch(event: Event) {
  event.preventDefault();

  const content = {
    password: password.value,
    isPublic: !isPublic.value,
    metadata: boardDisposition.value,
    game: game_type,
    matchName: matchName.value,
  };

  try {
    const response = await api.post("/match", content, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("jwt"),
      },
    });

    if (response.ok) {
      await api
        .get("/getPlayer")
        .then((response) => {
          return response.data;
        })
        .then((data) => {
          navigateTo("/profile/" + data.playerName);
        });
    }
  } catch (error) {
    console.error(error);
    msg.value = "Error creating match.";
  }
}

function showBoard() {
  switch (boardDisposition.value) {
    case "ace":
      imgPath.value = "/img/kingolaser/AceBoard.webp";
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

definePageMeta({
  layout: "game-layout",
});
</script>

<template>
  <div>
    <div
      id="home"
      class="text-black grid md:grid-cols-2 h-screen"
    >
      <div class="flex flex-col justify-center items-center">
        <h1 class="text-7xl text-center text-white">
          Match
        </h1>
        <form
          id="match"
          class="bg-white rounded-lg m-10 p-10 md:m-20 md:w-[80%]"
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
              class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
              placeholder="My Game"
              required
            >
          </div>

          <div>
            <select
              v-if="game_type === 'LASER_BOARD'"
              id="boardDisposition"
              v-model="boardDisposition"
              name="boardDisposition"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg p-2 mb-5"
              required
              @change="showBoard"
            >
              <option
                value=""
                disabled
                selected
              >
                Select Board Disposition
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
            <div class="mb-4">
              <input
                id="isPrivate"
                type="checkbox"
                name="isPrivate"
                class="hidden"
                @change="(event:Event) => isChecked((event.target as HTMLInputElement).checked)"
              >
              <p class="flex items-center">
                {{ isPublic ? "Private" : "Public" }}
                <label
                  for="isPrivate"
                  class="inline-flex items-center ml-2 mr-2 text-sm font-medium text-black rounded-full cursor-pointer select-none transition-colors duration-200"
                  :class="{
                    'bg-gray-300': !isPublic,
                    'bg-red-500': isPublic,
                  }"
                >
                  <span
                    class="relative inline-block w-16 h-5 m-1 rounded-md transition-all duration-200"
                  >
                    <span
                      class="absolute inset-0 transform translate-x-0 transition-transform duration-200"
                      :class="{
                        'translate-x-0': !isPublic,
                        'translate-x-5': isPublic,
                      }"
                    >
                      <span
                        class="absolute left-0 w-5 h-5 rounded-full bg-white shadow transform transition-transform duration-200"
                        :class="{
                          'translate-x-0': !isPublic,
                          'translate-x-5': isPublic,
                        }"
                      />
                    </span>
                  </span>
                </label>
              </p>
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
                  class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
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
                class="signup font-bold py-2 px-4 rounded bg-blue-500 text-white hover:bg-blue-600 transition-colors duration-200"
                @click="createMatch"
              >
                Create Match
              </button>
            </div>
          </div>
        </form>
      </div>

      <div
        class="flex justify-center items-center m-5 lg:m-10 md:overflow-y-hidden"
      >
        <img
          :src="imgPath"
          class="max-h-[80%]"
        >
      </div>
    </div>
  </div>
</template>
