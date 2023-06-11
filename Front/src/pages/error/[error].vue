<template>
  <div class="flex flex-col items-center justify-center min-h-screen bg-primary">
    <div class="character items-center justify-center flex text-pink-400">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="400"
        height="400"
        viewBox="0 0 24 24"
      ><path
        fill="currentColor"
        d="M7.97 16L5 19c-.33.3-.77.5-1.25.5A1.75 1.75 0 0 1 2 17.75v-.25l1-7.38A4.524 4.524 0 0 1 7.5 6h9c2.36 0 4.29 1.81 4.5 4.12l1 7.38v.25a1.75 1.75 0 0 1-1.75 1.75c-.48 0-.92-.2-1.25-.5l-2.97-3H7.97M7 8v2H5v1h2v2h1v-2h2v-1H8V8H7m9.5 0a.75.75 0 0 0-.75.75a.75.75 0 0 0 .75.75a.75.75 0 0 0 .75-.75a.75.75 0 0 0-.75-.75m-1.75 1.75a.75.75 0 0 0-.75.75a.75.75 0 0 0 .75.75a.75.75 0 0 0 .75-.75a.75.75 0 0 0-.75-.75m3.5 0a.75.75 0 0 0-.75.75a.75.75 0 0 0 .75.75a.75.75 0 0 0 .75-.75a.75.75 0 0 0-.75-.75M16.5 11.5a.75.75 0 0 0-.75.75a.75.75 0 0 0 .75.75a.75.75 0 0 0 .75-.75a.75.75 0 0 0-.75-.75Z"
      /></svg>
    </div>
    <div class="bg-slate-900 p-20 rounded-lg">
      <h1 class="text-4xl font-bold mb-2 text-secondary text-white">
        ¡Oops! Algo salió mal.
      </h1>
      <p class="text-lg text-tertiary mb-8 text-white">
        Lo sentimos, parece que ha ocurrido un error.
      </p>  
      <a
        href="/"
        class="justify-center items-center flex bg-blue-950 btn-primary bg-accent hover:bg-accent-dark text-white font-semibold py-2 px-4 rounded-md transition-colors duration-300 ease-in-out"
      >
        Volver a la página principal
      </a>
    </div>
  </div>
</template>
  
  <style>
  .character {
    animation: bounce 2s infinite;
  }

  
  @keyframes bounce {
    0% {
      transform: translateY(0);
    }
    50% {
      transform: translateY(-20px);
    }
    100% {
      transform: translateY(0);
    }
  }
  </style>
  