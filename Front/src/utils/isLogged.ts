export default
onBeforeMount(() =>{
  watch(
    () => localStorage.getItem('jwt'),
    (newValue) => {
      const token = newValue || ''
      const tokenExp = localStorage.getItem('jwtExp') || ''
  
      if (!token || token === '' || new Date(tokenExp) <= new Date()) {
        localStorage.setItem('jwtExp', '')
        localStorage.setItem('jwt', '')
        window.alert('No Logged User or Your Session Expired. Log In Again')

        nextTick()
        navigateTo('/login')
      }
    },
    { immediate: true } 
  )
  
})