<template>
  <div
    class="bg-white flex justify-between py-4 px-8 pr-20 flex-col border-b-8 border-black"
  >
    <div class="flex items-center">
      <div
        :class="[
          'flex',
          'items-center',
          'justify-center',
          'w-20',
          'h-20',
          'rounded-full',
          'border-4',
          'shadow',
          user.loggedIn ? 'border-green-500' : 'border-red-500',
        ]"
      >
        <img
          :src="user.profileImg"
          class="w-full h-full rounded-full object-cover"
        >
      </div>
      <div class="flex ml-auto items-start flex-col">
        <p class="m-2 text-2xl font-bold">
          {{ user.name }}
        </p>
        <div class="flex ml-auto items-end flex-row">
          <NuxtLink
            to="/"
            class="transition duration-300 hover:bg-gray-200 hover:rounded m-1 p-1 hover:text-gray-600 hover:shadow text-slate-500"
          >
            <img
              src="/img/commonIcon/home.png"
              class="w-6 h-6 mx-2"
            >
          </NuxtLink>
          <NuxtLink
            to="/select-game"
            class="transition duration-300 hover:bg-gray-200 hover:rounded m-1 p-1 hover:text-gray-600 hover:shadow text-slate-500"
          >
            <img
              src="/img/commonIcon/create.png"
              class="w-6 h-6 mx-2"
            >
          </NuxtLink>
          <NuxtLink
            class="transition duration-300 hover:bg-gray-200 hover:rounded m-1 p-1 hover:text-gray-600 hover:shadow text-slate-500 hover:cursor-pointer"
            @click="logout"
          >
            <img
              src="/img/commonIcon/logOut.png"
              class="w-6 h-6 mx-2"
            >
          </NuxtLink>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const jwt = ref(localStorage.getItem("jwt"));
const user = ref({
  name:String,
  loggedIn:Boolean,
  profileImg:String
})

onBeforeMount(async () =>{
  await fetch("http://localhost:8080/getPlayer", {
  method: "GET",
  headers: {
    "Content-Type": "application/json",
    Authorization:  "Bearer " + jwt.value,
  }
})
  .then((response) => {
    return response.json();
  })
  .then((data) => {
    user.value.name = data.playerName;
    user.value.loggedIn = data.loggedIn;
    user.value.profileImg = data.profileImg;
  });
})

const logout = () => {
  localStorage.setItem("jwt", "");
  localStorage.setItem("jwtExp", "");
  return navigateTo("/");
};
</script>
