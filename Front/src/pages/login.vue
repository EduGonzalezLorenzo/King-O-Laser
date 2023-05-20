<script setup lang="ts">
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
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        msg.value = "Error Logging User";
      }
    })
    .then((data) => {
      localStorage.setItem("jwt", data.message);
      navigateTo(`/profile/hola`);
    });
}
</script>

<template>
  <div>
    <div
      id="home"
      class="text-black flex flex-col items-center justify-center h-screen"
    >
      <h1 class="text-7xl text-center mt-28 text-white">
        Login
      </h1>

      <form
        id="login"
        class="bg-white rounded-lg text-black m-10 p-10"
        @submit="(event:Event) => LogUser(event)"
      >
        <div class="mb-6">
          <label
            for="email"
            class="block mb-2 text-sm font-medium text-black"
          >Your Username or Email</label>
          <input
            id="email"
            type="text"
            class="shadow-sm bg-gray-50 border border-gray-300 text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
            placeholder="name@email.com"
            required
            @change="(event:Event) => isEmail((event.target as HTMLInputElement).value)"
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
            class="shadow-sm bg-gray-50 border border-gray-300 text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
            required
            placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
          >
        </div>
        <div class="flex items-start mb-6">
          <div class="flex items-center h-5">
            <input
              id="terms"
              type="checkbox"
              value=""
              class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800"
              required
            >
          </div>
          <label
            for="terms"
            class="ml-2 text-sm font-medium text-black dark:text-black"
          >I agree with the
            <a
              href="#"
              class="text-blue-600 hover:underline dark:text-blue-500"
            >terms and conditions</a></label>
        </div>
        <p
          class="text-red-600 font-bold text-lg m-4 bg-gray-700 px-3 py-1 ml-0 rounded"
        >
          {{ msg }}
        </p>
        <NuxtLink
          to="/signup"
          class="text-blue-600 underline hover:text-red-600"
        >
          <p class="mb-4">
            Dont have an Account?
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
    </div>
  </div>
</template>
