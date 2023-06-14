<script setup lang="ts">
const isRotated = ref(true);
const isMobile = ref(false);

const name = computed<string>(() => {
  const nameValue = props.user.name;
  return [nameValue]?.filter(Boolean).splice(0, 2).join(' ') || nameValue;
});

const color = computed<string[]>(() => {
  const nameValue = name.value;
  let hash = 0;
  if (nameValue !== null && Array.from(nameValue)) {
    hash = Array.from(nameValue).reduce((acc, char) => ((acc << 5) - acc + char.charCodeAt(0)) | 0, 0);
  }

  const r = (hash >> 16) & 255;
  const g = (hash >> 8) & 255;
  const b = hash & 255;
  return [`rgb(${r},${g},${b},0.3)`, `rgb(${r},${g},${b},1)`];
});

const props = defineProps({
  user: {
    type: Object,
    required: false,
    default: () => ({
      name: '',
      loggedIn: true,
      profileImg: '',
    }),
  },
});

watchEffect(() => {
  checkMobile();
  window.addEventListener("resize", checkMobile);
  if (window.innerWidth < window.innerHeight) {
    isMobile.value = true;
  } else {
    isMobile.value = false;
  }
});

function checkMobile() {
  isMobile.value = window.innerWidth < window.innerHeight;
}

const emit = defineEmits(['dropdown-click']);
const router = useRouter();

const logout = () => {
  localStorage.setItem('jwt', '');
  localStorage.setItem('jwtExp', '');
  router.push('/login');
};

const dropDownShow = ref(true);

function dropdownClick() {
  isRotated.value = !isRotated.value;
  dropDownShow.value = !dropDownShow.value;
  emit('dropdown-click', dropDownShow.value);
}
</script>

<template>
  <div class="flex justify-between py-4 px-4 pr-15 flex-col w-full">
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
        ]"
      >
        <img
          v-if="props.user.profileImg"
          :src="props.user.profileImg"
          class="w-full h-full rounded-full object-cover"
        >
        <div
          v-else
          :style="{background:color[0],color:color[1],}"
          class="w-full h-full rounded-full items-center justify-center flex font-bold text-4xl"
        >
          {{ name?.split(' ').map((s)=>s[0]?.toUpperCase()).join(' ') }}
        </div>
      </div>
      <div class="flex items-start flex-col">
        <p class="m-2 text-2xl font-bold text-white">
          {{ props.user.name }}
        </p>
        <div class="flex ml-auto items-end flex-row">
          <NuxtLink
            :to="`/profile/${props.user.name}`"
            name="home"
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
              <path
                d="M10 19v-5h4v5c0 .55.45 1 1 1h3c.55 0 1-.45 1-1v-7h1.7c.46 0 .68-.57.33-.87L12.67 3.6c-.38-.34-.96-.34-1.34 0l-8.36 7.53c-.34.3-.13.87.33.87H5v7c0 .55.45 1 1 1h3c.55 0 1-.45 1-1z"
              />
            </svg>
          </NuxtLink>

          <NuxtLink
            to="/select-game"
            name="create-match"
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
              <path
                d="M512 0C229.232 0 0 229.232 0 512c0 282.784 229.232 512 512 512c282.784 0 512-229.216 512-512C1024 229.232 794.784 0 512 0zm0 961.008c-247.024 0-448-201.984-448-449.01c0-247.024 200.976-448 448-448s448 200.977 448 448s-200.976 449.01-448 449.01zM736 480H544V288c0-17.664-14.336-32-32-32s-32 14.336-32 32v192H288c-17.664 0-32 14.336-32 32s14.336 32 32 32h192v192c0 17.664 14.336 32 32 32s32-14.336 32-32V544h192c17.664 0 32-14.336 32-32s-14.336-32-32-32z"
              />
            </svg>
          </NuxtLink>
          <NuxtLink
            to="/search-match"
            name="search-match"
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
              <path
                d="M5 1a4 4 0 1 0 2.248 7.31l2.472 2.47a.75.75 0 1 0 1.06-1.06L8.31 7.248A4 4 0 0 0 5 1ZM2.5 5a2.5 2.5 0 1 1 5 0a2.5 2.5 0 0 1-5 0Z"
              />
            </svg>
          </NuxtLink>

          <NuxtLink
            to="/profile/settings"
            name="settings"
            class="transition duration-300 hover:bg-gray-900 hover:rounded m-1 p-1 hover:text-gray-300 hover:shadow  text-slate-400 hover:cursor-pointer"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
            ><g
              fill="none"
              stroke="currentColor"
              stroke-width="1.5"
            ><circle
              cx="12"
              cy="12"
              r="3"
            /><path d="M13.765 2.152C13.398 2 12.932 2 12 2c-.932 0-1.398 0-1.765.152a2 2 0 0 0-1.083 1.083c-.092.223-.129.484-.143.863a1.617 1.617 0 0 1-.79 1.353a1.617 1.617 0 0 1-1.567.008c-.336-.178-.579-.276-.82-.308a2 2 0 0 0-1.478.396C4.04 5.79 3.806 6.193 3.34 7c-.466.807-.7 1.21-.751 1.605a2 2 0 0 0 .396 1.479c.148.192.355.353.676.555c.473.297.777.803.777 1.361c0 .558-.304 1.064-.777 1.36c-.321.203-.529.364-.676.556a2 2 0 0 0-.396 1.479c.052.394.285.798.75 1.605c.467.807.7 1.21 1.015 1.453a2 2 0 0 0 1.479.396c.24-.032.483-.13.819-.308a1.617 1.617 0 0 1 1.567.008c.483.28.77.795.79 1.353c.014.38.05.64.143.863a2 2 0 0 0 1.083 1.083C10.602 22 11.068 22 12 22c.932 0 1.398 0 1.765-.152a2 2 0 0 0 1.083-1.083c.092-.223.129-.483.143-.863c.02-.558.307-1.074.79-1.353a1.617 1.617 0 0 1 1.567-.008c.336.178.579.276.819.308a2 2 0 0 0 1.479-.396c.315-.242.548-.646 1.014-1.453c.466-.807.7-1.21.751-1.605a2 2 0 0 0-.396-1.479c-.148-.192-.355-.353-.676-.555A1.617 1.617 0 0 1 19.562 12c0-.558.304-1.064.777-1.36c.321-.203.529-.364.676-.556a2 2 0 0 0 .396-1.479c-.052-.394-.285-.798-.75-1.605c-.467-.807-.7-1.21-1.015-1.453a2 2 0 0 0-1.479-.396c-.24.032-.483.13-.82.308a1.617 1.617 0 0 1-1.566-.008a1.617 1.617 0 0 1-.79-1.353c-.014-.38-.05-.64-.143-.863a2 2 0 0 0-1.083-1.083Z" /></g></svg>
          </NuxtLink>
          <NuxtLink
            name="drop-down"
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
              <path
                d="M12 12h7m0 0l-3 3m3-3l-3-3m3-3V5a2 2 0 0 0-2-2H7a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2v-1"
              />
            </svg>
          </NuxtLink>
          <NuxtLink
            v-if="isMobile"
            id="dropDown"
            class="cursor-pointer transition duration-300 hover:bg-gray-900 hover:rounded m-1 p-1 hover:text-gray-300 hover:shadow text-pink-500"
            @click="dropdownClick"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              :class="{ rotated: isRotated }"
            >
              <path
                :fill="isRotated ? 'currentColor' : 'none'"
                :stroke="isRotated ? 'none' : 'currentColor'"
                stroke-width="2"
                :d="isRotated ? 'M3 19h18a1.002 1.002 0 0 0 .823-1.569l-9-13c-.373-.539-1.271-.539-1.645 0l-9 13A.999.999 0 0 0 3 19z' : 'M21 5h-18a1.002 1.002 0 0 0-.823 1.569l9 13c.373.539 1.271.539 1.645 0l9-13A.999.999 0 0 0 21 5z'"
              />
            </svg>
          </NuxtLink>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.rotated path {
  fill: none;
  stroke: currentColor;
}
</style>
