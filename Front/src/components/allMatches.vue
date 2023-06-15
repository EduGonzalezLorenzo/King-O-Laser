<template>
  <div>
    <div class="flex flex-col bg-slate-300 jusify-start">
      <ul class="grid grid-cols-1">
        <li
          v-for="(game, index) in props.matches"
          :key="index"
          class="border-white border-4 p-4"
        >
          <div
            class="flex items-start"
            @click="inputGamePassword(game)"
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
    <div
      class="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
    >
      <div
        v-if="displayForm"
        class="text-white flex flex-col items-center justify-center min-w-fit"
      >
        <form
          id="goMatch"
          class="bg-gray-900 rounded-lg text-white lg:m-10 p-10 md:grid md:grid-cols-1 md:gap-6 w-full flex flex-col items-center justify-center"
          @submit="goMatch"
        >
          <div class="mb-6">
            <label
              for="password"
              class="flex w-full mb-2 text-sm font-medium"
            >Game Password</label>
            <input
              id="password"
              v-model="password"
              type="password"
              class="flex w-full shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
              placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
            >
          </div>
          <div
            v-if="msg"
            class="col-span-2"
          >
            <p
              id="msg"
              class="text-red-500 font-bold mb-4"
            >
              {{ msg }}
            </p>
          </div>
          <button
            id="submit"
            type="submit"
            class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4 w-full col-span-2"
          >
            Send Password
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const jwt = ref<String>("");
const displayForm = ref(false);
const msg = ref("");
const password = ref("");
const gameId = ref();

onMounted(() => {
  const localStore = localStorage.getItem("jwt");
  jwt.value = localStore as String;
});
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
async function inputGamePassword(game: any) {
  gameId.value = game.id;
  if (game.isPublic === "Private") {
    displayForm.value = displayForm.value === true ? false : true;
  } else {
    publicMatch();
  }
}

async function publicMatch() { 
  const content = {
    password: "",
  };  await fetch("http://localhost:8080/match/" + gameId.value, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt.value,
    },
    body: JSON.stringify(content),
  })
    .then(async (response) => {
      return {
        response,
        message: await response.json(),
      };
    })
    .then(async (data) => {
      if (data.response.ok) {
        navigateTo(`/games/` + gameId.value);
      } else {
        msg.value = data.message.message;
      }
    });
}

async function goMatch() {
  const content = {
    password: password.value,
  };
  await fetch("http://localhost:8080/match/" + gameId.value, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt.value,
    },
    body: JSON.stringify(content),
  })
    .then(async (response) => {
      return {
        response,
        message: await response.json(),
      };
    })
    .then(async (data) => {
      if (data.response.ok) {
        navigateTo(`/games/` + gameId.value);
      } else {
        msg.value = data.message.message;
      }
    });
}
</script>
