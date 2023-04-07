<script setup lang="ts">
import getPokedex from '@/components/1'
</script>
<template>
  <div>
    <h1>Home View</h1>
    <p>This is a Home View</p>
    Pokedex: {{ getPokedex.data()}}
  </div>
</template>
