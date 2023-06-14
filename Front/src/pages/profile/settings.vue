<template>
  <div>
    <div class="text-black flex flex-col items-center justify-center h-screen">
      <h1 class="text-5xl text-center text-white">
        User Settings
      </h1>
      <form
        id="updateUser"
        class="bg-white rounded-lg text-black  m-10 p-10 w-80"
        @submit="(event:Event) => handleSubmit(event)"
      >
        <div class="mb-6">
          <label
            for="username"
            class="block mb-2 text-sm font-medium text-black"
          >User Name</label>
          <input
            id="username"
            type="text"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="Player1234"
          >
        </div>
        <div class="mb-6">
          <label
            for="password"
            class="block mb-2 text-sm font-medium text-black"
          >Your password</label>
          <input
            id="password"
            type="password"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
          >
        </div>
        <div class="mb-6">
          <label
            for="repeat-password"
            class="block mb-2 text-sm font-medium text-black"
          >Repeat Your password</label>
          <input
            id="repeat-password"
            type="password"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
          >
        </div>
        <p
          class="text-red-600 font-bold text-lg m-4 bg-gray-700 px-3 py-1 ml-0 rounded"
        >
          {{ msg }}
        </p>
        <button
          id="submit"
          type="submit"
          class="signup font-bold py-2 px-4 rounded"
        >
          Login
        </button>
      </form>
    </div>
  </div>
</template>
<script setup lang="ts">
const msg = ref("")

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
function updateUser(){
    console.log("Updated")
}
definePageMeta({
  layout: "game-layout",
});
useHead({
  title: `Settings` ,
})
</script>
