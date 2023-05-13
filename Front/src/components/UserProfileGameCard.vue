<template>
  <div class="bg-white flex justify-between py-4 px-8 pr-20 flex-col border-b-8 border-black">
    <div class="flex items-center">
      <div
        :class="['flex', 'items-center', 'justify-center', 'w-20', 'h-20', 'rounded-full', 'border-4', 
                 'shadow', user.loggedIn ? 'border-green-500' : 'border-red-500']"
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
            class="transition duration-300 hover:bg-gray-200 hover:rounded hover:p-1 hover:text-gray-600 hover:shadow text-slate-500"
          >
            <img
              src="/img/commonIcon/home.png"
              class="w-6 h-6 mx-2"
            >
          </NuxtLink>
          <NuxtLink
            to="/create-match"
            class="transition duration-300 hover:bg-gray-200 hover:rounded hover:p-1 hover:text-gray-600 hover:shadow text-slate-500"
          >
            <img
              src="/img/commonIcon/create.png"
              class="w-6 h-6 mx-2"
            >
          </NuxtLink>
          <NuxtLink
            class="transition duration-300 hover:bg-gray-200 hover:rounded hover:p-1 hover:text-gray-600 hover:shadow text-slate-500 hover:cursor-pointer"
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
    const user = {
      name: "Ejemplo",
      profileImg: "/img/kingolaser/BlueKing.png",
      loggedIn: true
    };
    const logout = () => {
      console.log("LoggedOut")
    };
  </script>
  