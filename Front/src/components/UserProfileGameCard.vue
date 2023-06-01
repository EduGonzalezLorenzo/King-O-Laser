<script setup lang="ts">
const jwt = ref<String>("")

const user = ref({
  name:String,
  loggedIn:Boolean,
  profileImg:String
})

onMounted(async () =>{
  const localStore = localStorage.getItem("jwt")
  jwt.value = localStore as String;
  
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
function dropDown(){

}
const logout = () => {
  localStorage.setItem("jwt", "");
  localStorage.setItem("jwtExp", "");
  return navigateTo("/");
};
</script>

<template>
  <div class="flex justify-between py-4 px-8 pr-20 flex-col w-full ">
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
      <div class="flex items-start flex-col">
        <p class="m-2 text-2xl font-bold text-white">
          {{ user.name }}
        </p>
        <div class="flex ml-auto items-end flex-row">
          <NuxtLink
            :to="`/profile/${user.name}`"
            class="transition duration-300 hover:bg-gray-900 hover:rounded m-1 p-1 hover:text-gray-300 hover:shadow text-yellow-300"
          >
            <svg
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <path d="M10 19v-5h4v5c0 .55.45 1 1 1h3c.55 0 1-.45 1-1v-7h1.7c.46 0 .68-.57.33-.87L12.67 3.6c-.38-.34-.96-.34-1.34 0l-8.36 7.53c-.34.3-.13.87.33.87H5v7c0 .55.45 1 1 1h3c.55 0 1-.45 1-1z" />
            </svg>
          </NuxtLink>

          <NuxtLink
            to="/select-game"
            class="transition duration-300 hover:bg-gray-900 hover:rounded m-1 p-1 hover:text-gray-300 hover:shadow text-purple-500"
          >
            <svg
              width="24"
              height="24"
              viewBox="0 0 1024 1024"
              fill="currentColor"
              stroke="none"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <path d="M512 0C229.232 0 0 229.232 0 512c0 282.784 229.232 512 512 512c282.784 0 512-229.216 512-512C1024 229.232 794.784 0 512 0zm0 961.008c-247.024 0-448-201.984-448-449.01c0-247.024 200.976-448 448-448s448 200.977 448 448s-200.976 449.01-448 449.01zM736 480H544V288c0-17.664-14.336-32-32-32s-32 14.336-32 32v192H288c-17.664 0-32 14.336-32 32s14.336 32 32 32h192v192c0 17.664 14.336 32 32 32s32-14.336 32-32V544h192c17.664 0 32-14.336 32-32s-14.336-32-32-32z" />
            </svg>
          </NuxtLink>
          <NuxtLink
            to="/search-match"
            class="transition duration-300 hover:bg-gray-900 hover:rounded m-1 p-1 hover:text-gray-300 hover:shadow text-blue-500"
          >
            <svg
              width="24"
              height="24"
              viewBox="0 0 12 12"
              fill="none"
              stroke="currentColor"
              stroke-width="1"
              stroke-linecap="round"
              stroke-linejoin="round"
              transform="rotate(90 0 0)"
            >
              <path d="M5 1a4 4 0 1 0 2.248 7.31l2.472 2.47a.75.75 0 1 0 1.06-1.06L8.31 7.248A4 4 0 0 0 5 1ZM2.5 5a2.5 2.5 0 1 1 5 0a2.5 2.5 0 0 1-5 0Z" />
            </svg>
          </NuxtLink>
          <NuxtLink
            class="transition duration-300 hover:bg-gray-900 hover:rounded m-1 p-1 hover:text-gray-300 hover:shadow text-pink-500"
            @click="dropDown"
          >
            <svg
              width="24"
              height="24"
              viewBox="0 0 12 12"
              fill="none"
              stroke="currentColor"
              stroke-width="1"
              stroke-linecap="round"
              stroke-linejoin="round"
              transform="rotate(90 0 0)"
            >
              <path d="M5 1a4 4 0 1 0 2.248 7.31l2.472 2.47a.75.75 0 1 0 1.06-1.06L8.31 7.248A4 4 0 0 0 5 1ZM2.5 5a2.5 2.5 0 1 1 5 0a2.5 2.5 0 0 1-5 0Z" />
            </svg>
          </NuxtLink>
          <NuxtLink
            class="transition duration-300 hover:bg-gray-900 hover:rounded m-1 p-1 hover:text-gray-300 hover:shadow text-red-500 hover:cursor-pointer"
            @click="logout"
          >
            <svg
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <path d="M12 12h7m0 0l-3 3m3-3l-3-3m3-3V5a2 2 0 0 0-2-2H7a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2v-1" />
            </svg>
          </NuxtLink>
        </div>
      </div>
    </div>
  </div>
</template>
