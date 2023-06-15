// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  app: {
    head: {
      charset: 'utf-8',
      viewport: 'width=device-width, initial-scale=1',
      title: 'Telegame',
      htmlAttrs: {
        lang: 'en'
      },
    }
  },
  srcDir:"./src",
  ssr:false,
  modules: [
    "@nuxtjs/tailwindcss",
    // With options
    ["@nuxtjs/eslint-module", { lintOnStart: false }],
  ],
});
