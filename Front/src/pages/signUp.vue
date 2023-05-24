<script setup lang="ts">
const msg = ref<string>("");

async function createUser() {
  const firstName = (document.getElementById("firstname") as HTMLInputElement)
    .value;
  const lastName = (document.getElementById("lastname") as HTMLInputElement)
    .value;
  const email = (document.getElementById("email") as HTMLInputElement).value;
  const playerName = (document.getElementById("username") as HTMLInputElement)
    .value;
  const password = (document.getElementById("password") as HTMLInputElement)
    .value;

  await fetch("http://localhost:8080/signUp", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      firstName,
      lastName,
      email,
      playerName,
      password,
    }),
  })
    .then((response) => {
      if (response.ok) {
        navigateTo("/login");
      } else {
        msg.value = "Error Creating User";
      }
    })
    .catch((error) => {
      if(error)
      navigateTo("/signup")
    });
}

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
    createUser();
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
    <div
      id="home"
      class="text-black flex flex-col items-center justify-center h-screen"
    >
      <h1 class="text-7xl text-center mt-28 text-white">
        SignUp
      </h1>

      <form
        id="signUp"
        class="bg-white rounded-lg text-black m-10 p-10 grid grid-cols-1 gap-6 md:grid-cols-2"
        @submit="handleSubmit"
      >
        <div>
          <label
            for="firstname"
            class="block mb-2 text-sm font-medium text-black"
          >First Name</label>
          <input
            id="firstname"
            type="text"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="Jhon"
            required
          >
        </div>
        <div>
          <label
            for="lastname"
            class="block mb-2 text-sm font-medium text-black"
          >Last Name</label>
          <input
            id="lastname"
            type="text"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="Presley"
            required
          >
        </div>
        <div>
          <label
            for="email"
            class="block mb-2 text-sm font-medium text-black"
          >Email</label>
          <input
            id="email"
            type="email"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="name@email.com"
            required
          >
        </div>
        <div>
          <label
            for="username"
            class="block mb-2 text-sm font-medium text-black"
          >User Name</label>
          <input
            id="username"
            type="text"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            placeholder="Player1234"
            required
          >
        </div>
        <div>
          <label
            for="password"
            class="block mb-2 text-sm font-medium text-black"
          >Your password</label>
          <input
            id="password"
            type="password"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            required
            placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
            @change="(event:Event) => isStrongPassword((event.target as HTMLInputElement).value)"
          >
          <span
            id="msg"
            class="text-red-700 font-semibold"
          />
        </div>
        <div>
          <label
            for="repeat-password"
            class="block mb-2 text-sm font-medium text-black"
          >Repeat password</label>
          <input
            id="repeat-password"
            type="password"
            class="shadow-sm text-white text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-gray-700 border-gray-600 placeholder-gray-400 shadow-sm-light"
            required
            placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
          >
        </div>
        <div class="flex items-start">
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
            class="ml-2 text-sm font-medium text-white dark:text-gray-300"
          >
            I agree with the
            <a
              href="#"
              class="text-blue-600 hover:underline dark:text-blue-500"
            >
              terms and conditions
            </a>
          </label>
        </div>
        <p
          class="text-red-600 font-bold text-lg m-4 bg-gray-700 px-3 py-1 ml-0 rounded col-span-2 text-center"
        >
          {{ msg }}
        </p>
        <div class="col-span-2">
          <NuxtLink
            to="/login"
            class="text-blue-600 underline hover:text-red-600"
          >
            <p class="mb-4">
              Already have an Account?
            </p>
          </NuxtLink>
          <button
            id="submit"
            type="submit"
            class="signup font-bold py-2 px-4 rounded"
          >
            Sign Up
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
