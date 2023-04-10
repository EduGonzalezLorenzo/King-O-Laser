<script setup lang="ts">

</script>
<template>
    <div id="home" class="text-white">
      <h1 class="text-7xl text-center mt-28">Login</h1>
      <form action="/login" method="post">
        <label for="username">UserName
            <input type="text" id="username">
        </label>
        <label for="password"> Password
            <input type="password" name="password" id="password">
        </label>
      </form>
      <button class="bg-transparent  font-semibold hover:text-white py-3 px-10 border rounded">Send</button>
    </div>
  </template>