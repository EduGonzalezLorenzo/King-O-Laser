export default
onBeforeMount(async () =>{
  watch(
    () => localStorage.getItem('jwt'),
    async (newValue) => {
      const token = newValue || ''
      const tokenExp = localStorage.getItem('jwtExp') || ''
  
      if (!token || token === '' || new Date(tokenExp) <= new Date()) {
        localStorage.setItem('jwtExp', '')
        localStorage.setItem('jwt', '')
        window.alert('No Logged User or Your Session Expired. Log In Again')

        await nextTick()
        navigateTo('/login')
      }
    },
    { immediate: true } 
  )
  
})