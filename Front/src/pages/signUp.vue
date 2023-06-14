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
    .then(async (response) => {
      if (response.status === 200) {
        navigateTo("/login");
      }
      return{
        response,
        message:await response.json()
      }
    }).then(async (data) =>{
        msg.value = await data.message.message
    })
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
useHead({
  title: 'SignUp',
})
</script>
 
<template>
  <div>
    <div
      id="home"
      class="text-white flex flex-col items-center justify-center min-h-screen"
    >
      <h1 class="text-5xl font-bold mb-8">
        SignUp
      </h1>

      <form
        id="signUp"
        class="bg-gray-900 bg-opacity-75 rounded-lg text-white m-10 p-10 md:grid md:grid-cols-2 md:gap-6"
        @submit="handleSubmit"
      >
        <div>
          <label
            for="firstname"
            class="block mb-2 text-sm font-medium"
          >First Name</label>
          <input
            id="firstname"
            type="text"
            class="bg-gray-800 rounded-lg px-4 py-2 w-full"
            placeholder="John"
            required
          >
        </div>
        <div>
          <label
            for="lastname"
            class="block mb-2 text-sm font-medium"
          >Last Name</label>
          <input
            id="lastname"
            type="text"
            class="bg-gray-800 rounded-lg px-4 py-2 w-full"
            placeholder="Presley"
            required
          >
        </div>
        <div>
          <label
            for="email"
            class="block mb-2 text-sm font-medium"
          >Email</label>
          <input
            id="email"
            type="email"
            class="bg-gray-800 rounded-lg px-4 py-2 w-full"
            placeholder="name@example.com"
            required
          >
        </div>
        <div>
          <label
            for="username"
            class="block mb-2 text-sm font-medium"
          >User Name</label>
          <input
            id="username"
            type="text"
            class="bg-gray-800 rounded-lg px-4 py-2 w-full"
            placeholder="Player1234"
            required
          >
        </div>
        <div>
          <label
            for="password"
            class="block mb-2 text-sm font-medium"
          >Your password</label>
          <input
            id="password"
            type="password"
            class="bg-gray-800 rounded-lg px-4 py-2 w-full"
            required
            placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
            @change="(event:Event) => isStrongPassword((event.target as HTMLInputElement).value)"
          >
        </div>
        <div>
          <label
            for="repeat-password"
            class="block mb-2 text-sm font-medium"
          >Repeat password</label>
          <input
            id="repeat-password"
            type="password"
            class="bg-gray-800 rounded-lg px-4 py-2 w-full"
            required
            placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"
          >
        </div>
        <div class="col-span-2">
          <p
            id="msg"
            class="text-red-500 font-bold mb-4"
          >
            {{ msg }}
          </p>
        </div>
        <div class="flex items-start col-span-2">
          <div class="flex items-center">
            <input
              id="terms"
              type="checkbox"
              value=""
              class="mr-2"
            >
            <label
              for="terms"
              class="text-sm font-medium"
            >I agree with the <a
              href="#"
              class="text-blue-600 hover:underline"
            >terms and conditions</a></label>
          </div>
        </div>
        <div class="col-span-2 flex flex-col">
          <NuxtLink
            to="/login"
            class="text-blue-600 underline hover:text-red-600 mt-4"
          >
            Already have an Account?
          </NuxtLink>
          <button
            id="submit"
            type="submit"
            class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4"
          >
            Sign Up
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
