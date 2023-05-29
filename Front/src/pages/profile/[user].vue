<template>
  <div class="grid grid-cols-3 gap-24">
    <div class="col-span-1">
      <div class="flex flex-col h-full">
        <UserProfileGameCard />
        <StartedMatchList />
      </div>
    </div>
    <div class="col-span-2">
      <div class="flex justify-center items-center h-screen ">
        <p class="text-3xl flex p-2 px-5 font-semibold text-center bg-slate-200 rounded">
          Crea una partida pulsando
          <img
            src="/img/commonIcon/create.webp"
            class="w-6 h-6 mx-2 my-2"
          >
          o unete a una en
          <img
            src="/img/commonIcon/search.webp"
            class="w-6 h-6 mx-2 my-2"
          >
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import UserProfileGameCard from "~/components/UserProfileGameCard.vue";
import StartedMatchList from "~/components/StartedMatchList.vue";
</script>
<style scoped>
.game_container {
  display: flex;
  align-items: flex-start;
  align-items: center;
  width: 100%;
  height: 100%;
}

.canvas_container {
  position: relative;
}

.send_menu {
  background-color: rgba(211, 211, 211, 0.516);
  padding: 1em;
  display: flex;
  flex-direction: column;
}

button {
  background-color: azure;
  border: 1px solid black;
  padding: 1em;
  margin: 0.5em;
}
</style>
