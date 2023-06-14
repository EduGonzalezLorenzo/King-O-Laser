<template>
  <div />
</template>
<script>

const values = window.location.search;
const jwt = values.substring(5)
localStorage.setItem('jwt', jwt);

const parsed = parseJwt(jwt)

window.location.href=`http://localhost:3000/profile/${parsed.sub}`

function parseJwt (token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
}


</script>