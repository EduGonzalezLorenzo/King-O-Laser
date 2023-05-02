<script setup lang="ts">
const is_email = ref<boolean>(true);
function isEmail(value: string) {
  if (/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
    is_email.value = true;
  } else {
    is_email.value = false;
  }
}

async function LogUser(event:Event) {
  event.preventDefault()
  const password = (document.getElementById("password") as HTMLInputElement)
    .value;
  let userName = "";
  let email = "";
  if (is_email) {
    email = (document.getElementById("email") as HTMLInputElement).value;
  } else {
    userName = (document.getElementById("email") as HTMLInputElement).value;
  }
  const response = await fetch("http://localhost:8080/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      userName,
      email,
      password,
    }),
  });

  if (response.ok) {
    console.log("User Loged!");
     return await navigateTo({ path: '/select-game' })

  } else {
    console.error("Error logging user.");
  }
}
function isStrongPassword(value: string) {
  const myButton = document.querySelector("#submit") as HTMLButtonElement;
  const msg = document.querySelector("#msg") as HTMLSpanElement;
  msg.textContent = "Password is not Strong";
  myButton.disabled = true;
  if (
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])([A-Za-z\d$@$!%*?&]|[^ ]){8,15}$/.test(
      value
    )
  ) {
    msg.textContent = "";
    myButton.disabled = false;
  }
}
</script>

<template>
  <div>
    <a href="/">Home</a>
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
            class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
            placeholder="name@email.com"
            required
            @change="(event:Event) => isEmail((event.target as HTMLInputElement).value)"
          >
        </div>
        <div class="mb-6">
          <label
            for="password"
            class="block mb-2 text-sm font-medium text-gray-900"
          >Your password</label>
          <input
            id="password"
            type="password"
            class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light"
            required
            placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
            @change="(event:Event) => isStrongPassword((event.target as HTMLInputElement).value)"
          >
          <span id="msg" />
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
            class="ml-2 text-sm font-medium text-gray-900 dark:text-gray-300"
          >I agree with the
            <a
              href="#"
              class="text-blue-600 hover:underline dark:text-blue-500"
            >terms and conditions</a></label>
        </div>
        <button
          id="submit"
          type="submit"
          class="signup font-bold py-2 px-4 rounded"
          disabled="true"
        >
          Sign Up
        </button>
      </form>
    </div>
  </div>
</template>
