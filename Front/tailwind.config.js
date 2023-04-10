/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'media',
  content: [
    // Example content paths...
    './public/**/*.html',
    './src/**/*.{js,jsx,ts,tsx,vue}',
  ],
  theme: {
    extend: {},
    colors: {
      transparent: 'transparent',
      current: 'currentColor',
      white: '#ffffff',
      purple: '#3f3cbb',
      midnight: '#121063',
      metal: '#565584',
      tahiti: '#3ab7bf',
      silver: '#ecebff',
      bubble: '#ff77e9',
      bermuda: '#78dcca',
    },
  },
  variants: {
    extend: {},
  },
  plugins: [],
}