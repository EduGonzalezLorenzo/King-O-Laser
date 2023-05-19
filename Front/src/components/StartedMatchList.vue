<template>
  <div class="flex flex-col bg-slate-300">
    <ul class="grid grid-cols-1">
      <li
        v-for="(player, index) in user.list"
        :key="index"
        class="border-white border-4 p-4"
      >
        <div class="flex items-center">
          <div
            :class="['flex', 'items-center', 'justify-center', 'w-16', 'h-16', 'rounded-full', 'border-4','mr-4', 
                     'shadow', player.loggedIn ? 'border-green-500' : 'border-red-500']"
          >
            <img
              :src="player.profileImg"
              class="w-full h-full rounded-full object-cover"
            >
          </div>
          <div>
            <p class="font-bold">
              {{ player.gameName }}
            </p>
            <p
              v-if="player.turn"
              class="text-blue-500 font-bold"
            >
              Your Turn!
            </p>
            <p
              v-else
              class="text-orange-600 font-bold"
            >
              {{ player.vsName }} turn!
            </p>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>
  
  <script setup>
  const jwt = ref(localStorage.getItem("jwt"));
  await fetch("http://localhost:8080/match", {
  method: "GET",
  headers: {
    "Content-Type": "application/json",
    Authorization:  "Bearer " + jwt.value,
  },
})
  .then((response) => {
    return response.json();
  })
  .then((data) => {
    user.value = data;
  });
  const user = {
    list: [
      {
        gameName: "Ejemplo",
        vsName:"User 1",
        profileImg: "/img/kingolaser/BlueKing.png",
        loggedIn: true,
        turn: false,
      },
      {
          gameName: "Ejemplo2",
          vsName:"User 2",
          profileImg: "/img/kingolaser/RedKing.png",
          loggedIn: false,
          turn:true
      },
      {
          gameName: "Ejemplo3",
          vsName:"User 3",
          profileImg: "/img/kingolaser/BlueBouncerNS.png",
          loggedIn: false,
          turn:false
      },
      {
          gameName: "Ejemplo4",
          vsName:"User 4",
          profileImg: "/img/kingolaser/RedBouncerNS.png",
          loggedIn: true,
          turn:true
      }
    ],
  };
  </script>
  