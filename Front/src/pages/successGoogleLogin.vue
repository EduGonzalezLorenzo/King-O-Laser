<template>
    <div>
        <h1>google</h1>
    </div>
</template>
<script>

const values = window.location.search;
console.log(values)
const jwt = values.substring(5)
console.log(jwt)

localStorage.setItem('jwt', jwt);

const parsed = parseJwt(jwt)
console.log(parsed.sub)

// navigateTo("/profile/" + parsed.sub)
window.location.href=`http://localhost:3000/profile/${parsed.sub}`

function parseJwt (token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
}


</script>