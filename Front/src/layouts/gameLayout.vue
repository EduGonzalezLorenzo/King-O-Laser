<template>
  <div>
    <div v-if="loading">
      <LoadingComponent />
    </div>
    <UserProfileGameCard
      :user="user"
      @dropdown-click="showStartedMatchList = !showStartedMatchList"
    />
    <StartedMatchList
      v-if="showStartedMatchList"
      :users="users"
    />

    <slot />
  </div>
</template>
<script setup lang="ts">
import LoadingComponent from "~/components/LodingComp.vue";
import api from '@/utils/axios';

const loading = ref(false);
const showStartedMatchList = ref(false);
const users = ref([]);

const user = ref({
  name: String,
  loggedIn: Boolean,
  profileImg: String,
});
interface UserData {
  id: Number;
  name: String;
  isPublic: Boolean;
  currentPlayers: Number;
  matchCreation: String;
  status: String;
  position: String;
}

onMounted(async () => {
  loading.value = true;
  try {
    const response = await api.get('getPlayer');

    const data = response.data;

    user.value.name = data.playerName;
    user.value.loggedIn = data.loggedIn;
    user.value.profileImg = data.profileImg;
  } catch (error) {
    console.error(error);
  }
  try {
    const response = await api.get('/match');

    if (response.status !== 200) {
      throw new Error("Error en la solicitud al servidor");
    }

    const data = response.data;

    users.value = data.map((userData: UserData) => ({
      id: userData.id,
      name: userData.name,
      isPublic: userData.isPublic ? "Public" : "Private",
      currPlayers: userData.currentPlayers,
      matchCreation: userData.matchCreation,
      status: userData.status,
      position: userData.position,
    }));
  } catch (error) {
    console.error(error);
  }
  loading.value = false;
});
</script>
