<script setup lang="ts">
import api from '@/utils/axios';

const msg = ref<string>("");
const is_email = ref<boolean>(true);

function isEmail(value: string) {
  if (/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
    is_email.value = true;
  } else {
    is_email.value = false;
  }
}

async function LogUser(event: Event) {
  event.preventDefault();
  const password = (document.getElementById('password') as HTMLInputElement).value;

  let content = {};
  if (is_email.value) {
    const email = (document.getElementById('email') as HTMLInputElement).value;
    content = { email, password };
  } else {
    const playerName = (document.getElementById('email') as HTMLInputElement).value;
    content = { playerName, password };
  }
  await fetch('http://localhost:8080/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(content),
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        msg.value = 'Error Logging User';
      }
    })
    .then((data) => {
      localStorage.setItem('jwt', data.message);
      
    });
    await api.get("http://localhost:8080/getPlayer")
  .then((response) => {
    return response.data;
  })
  .then((data) => {
    navigateTo("/profile/" + data.playerName);
  });


}
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
        @submit="(event:Event) => LogUser(event)"
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
            @change="(event:Event) => isEmail((event.target as HTMLInputElement).value)"
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
        <div class="col-span-2 flex flex-col">
          <NuxtLink
            to="/signup"
            class="text-blue-500 hover:underline col-start-1"
          >
            Don't have an Account?
          </NuxtLink>
          <button
            type="submit"
            class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4"
          >
            Login
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
