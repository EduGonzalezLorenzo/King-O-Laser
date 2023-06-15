<template>
  <div>
    <div class="text-white flex flex-col items-center justify-center mt-12">
      <h1 class="text-5xl font-bold mb-8">
        User Settings
      </h1>
      <form
        id="updateUser"
        class="bg-gray-900 bg-opacity-75 rounded-lg text-white m-10 p-10 md:grid md:grid-cols-2 md:gap-6"
      >
        <div class="mb-6">
          <label
            for="username"
            class="block mb-2 text-sm font-medium"
          >User Name</label>
          <input
            id="username"
            v-model="userName"
            type="text"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="Player1234"
          >
        </div>
        <div class="mb-6">
          <label
            for="fistName"
            class="block mb-2 text-sm font-medium"
          >FirstName</label>
          <input
            id="fistName"
            v-model="firstName"
            type="text"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="Jhon"
          >
        </div>
        <div class="mb-6">
          <label
            for="lastName"
            class="block mb-2 text-sm font-medium"
          >LastName</label>
          <input
            id="lastName"
            v-model="lastName"
            type="text"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="Gonzalez"
          >
        </div>
        <div class="mb-6">
          <label
            for="password"
            class="block mb-2 text-sm font-medium"
          >Your password</label>
          <input
            id="password"
            v-model="password"
            type="password"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
          >
        </div>
        <div class="mb-6">
          <label
            for="repeat-password"
            class="block mb-2 text-sm font-medium"
          >Repeat Your password</label>
          <input
            id="repeat-password"
            v-model="repeatPassword"
            type="password"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
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
          @click="(event:Event) => handleSubmit(event)"
        >
          Update User
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import api from "@/utils/axios";

const msg = ref("");
const matchResponse = ref<boolean>(false);
const password = ref<string>("");
const userName = ref<string>("");
const firstName = ref<string>("");
const lastName = ref<string>("");
const repeatPassword = ref<string>("");

function checkPasswordsMatch() {
  const password1 = (document.getElementById("password") as HTMLInputElement)
    .value;
  const password2 = (
    document.getElementById("repeat-password") as HTMLInputElement
  ).value;
  return password1 === password2;
}

function handleSubmit(event: any) {
  event.preventDefault();
  if (!checkPasswordsMatch()) {
    msg.value = "Passwords do not match";
  } else {
    msg.value = "";
    updateUser();
  }
}
async function updateUser() {
  const content = {
    password: password.value,
    playerName: userName.value,
    firstName: firstName.value,
    lastName: lastName.value,
  };
  try {
    const response = await api.post("/settings", content);

    const data = {
      response,
      message: response.data,
    };

    if (data.response.status === 200) {
      matchResponse.value = true;
    } else {
      msg.value = data.message.message;
    }
  } catch (error) {
    console.error(error);
  }
}
definePageMeta({
  layout: "game-layout",
});
useHead({
  title: `Settings`,
});
</script>
