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

LogUser(parsed.sub)

function parseJwt (token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
}

async function LogUser(parsed) {
  const playerName = parsed;
  const content = {playerName};
  await fetch('http://localhost:8080/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(content),
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        msg.value = 'Error Logging User';
      }
    })
    .then((data) => {
      localStorage.setItem('jwt', data.message);
    });

  await fetch("http://localhost:8080/getPlayer", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt,
    },
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      navigateTo("/profile/" + data.playerName)
    });

}

</script>