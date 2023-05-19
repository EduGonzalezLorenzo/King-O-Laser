<template>
  <div class="flex flex-row">
    <div class="w-2/3">
      <div class="grid grid-cols-2 gap-24 game_container">
        <div class="mt-0 mb-auto">
          <UserProfileGameCard />
          <StartedMatchList />
        </div>
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
  /* position: relative; */
  display: flex;
  align-items: flex-start;
  align-items: center;
  width: 100%;
  height: 100%;
}

.canvas_container {
  position: relative;
}

/* h1 {
    font-size: 35px;
  } */

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
  /* font-size: 25px; */
}
</style>
