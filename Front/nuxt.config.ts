// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  srcDir:"./src",
  modules: [
    "@nuxtjs/tailwindcss",
    // With options
    ["@nuxtjs/eslint-module", { lintOnStart: false }],
  ],
});
