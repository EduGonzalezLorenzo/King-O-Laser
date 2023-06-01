<script setup lang="ts">
async function googleLogin() {
  await fetch("http://localhost:8080/logingoogle", {
    method: "GET",
    headers: {
      "Content-Type": "application/json"
    }
  }) .then((response) => {return response.json()}).then((data) => {navigateTo(`${data.message}`, {external: true})})
}
</script>
<template>
  <div
    id="home"
    class="text-white flex flex-col justify-between h-screen"
  >
    <h1 class="text-7xl text-center mt-52">
      Telegame
    </h1>
    <div class="text-center mt-auto mb-40">
      <button
        class="bg-transparent font-semibold hover:text-black hover:bg-white hover:shadow-xl hover:shadow-white hover:mb-3 py-5 px-16 border rounded text-2xl transition duration-300 ease-in-out"
        @click="googleLogin"
      >
        Play
      </button>
    </div>
  </div>
</template>


