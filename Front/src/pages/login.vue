<script setup lang="ts">
import api from '@/utils/axios';
const msg = ref<string>("");
const is_email = ref<boolean>(true);
const loginResponse = ref<boolean>(false);

function isEmail(value: string) {
  if (/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
    is_email.value = true;
  } else {
    is_email.value = false;
  }
}

async function googleLogin() {
  await fetch("http://localhost:8080/logingoogle", {
    method: "GET",
    headers: {
      "Content-Type": "application/json"
    }
  }).then((response) => { 
    return response.json()
  }).then((data) => {
    console.log(data)
    navigateTo(`${data.message}`, { external: true }) })
}
async function LogUser(event: Event) {
  event.preventDefault();
  const password = (document.getElementById("password") as HTMLInputElement)
    .value;

  let content = {};
  if (is_email.value) {
    const email = (document.getElementById("email") as HTMLInputElement).value;
    content = { email, password };
  } else {
    const playerName = (document.getElementById("email") as HTMLInputElement)
      .value;
    content = { playerName, password };
  }
  await fetch("http://localhost:8080/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
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
        loginResponse.value = true;
        localStorage.setItem("jwt", await data.message.message);
      }else{
        msg.value = await data.message.message;
      }
    });

  if (loginResponse.value) {
    await api.get("http://localhost:8080/getPlayer")
  .then((response) => {
    return response.data;
  })
  .then((data) => {
    navigateTo("/profile/" + data.playerName);
  });
  }
}
useHead({
  title: 'Login',
})

</script>

<template>
  <div>
    <div
      id="home"
      class="text-white flex flex-col items-center justify-center h-screen"
    >
      <h1 class="text-5xl font-bold mb-8">
        Login
      </h1>

      <form
        id="login"
        class="min-w-[25%] bg-gray-900 bg-opacity-75 rounded-lg text-white m-10 p-10 grid gap-6 md:grid-cols-2"
        @submit="(event: Event) => LogUser(event)"
      >
        <div class="mb-4 col-span-2">
          <label
            for="email"
            class="block mb-2"
          >Your Username or Email</label>
          <input
            id="email"
            type="text"
            class="bg-gray-800 rounded-lg px-4 py-2 w-full"
            placeholder="name@email.com"
            required
            @change="(event: Event) => isEmail((event.target as HTMLInputElement).value)"
          >
        </div>
        <div class="mb-4 col-span-2">
          <label
            for="password"
            class="block mb-2"
          >Your Password</label>
          <input
            id="password"
            type="password"
            class="bg-gray-800 rounded-lg px-4 py-2 w-full"
            required
            placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
          >
        </div>
        <p
          v-if="msg"
          class="transition text-red-500 font-bold mb-4"
        >
          {{ msg }}
        </p>
        <NuxtLink
          to="/signup"
          class="text-blue-600 underline hover:text-red-600"
        >
          <p class="mb-4">
            Don't have an Account?
          </p>
        </NuxtLink>
        <button
          id="submit"
          type="submit"
          class="signup font-bold py-2 px-4 rounded"
        >
          Login
        </button>
      </form>
      <button
        class="flex items-center justify-center bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded-md transition-colors duration-300 ease-in-out"
        @click="googleLogin"
      >
        <svg
          class="w-6 h-6 mr-2"
          viewBox="0 0 24 24"
          fill="currentColor"
        >
          <path
            d="M20.6 11.5h-9.1v3.7h5.3c-0.2 1.4-1.4 4.1-5.3 4.1-3.2 0-5.9-2.7-5.9-6s2.7-6 5.9-6c1.8 0 3.1 0.8 3.9 1.5l2.7-2.6c-1.7-1.5-3.9-2.4-6.6-2.4-5.5 0-10 4.5-10 10s4.5 10 10 10c6.1 0 9.5-4.3 9.5-9.8 0-0.7-0.1-1.3-0.3-1.9z"
          />
        </svg>
        Google Login
      </button>
    </div>
  </div>
</template>
