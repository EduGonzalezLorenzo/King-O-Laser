import { defineComponent } from "vue";

async function api<T>(url: string): Promise<T> {
  return fetch(url)
    .then((response) => {
      if (!response.ok) {
        throw new Error(response.statusText);
      }
      return response.json();
    })
    .then((data) => {
      return data;
    });
}
 
const pokemon:any = await api("https://pokeapi.co/api/v2/pokemon/ditto");
  export default defineComponent({
    data() {
      let pokemonObject = pokemon
        return {
          id:pokemonObject.id,
            name: pokemonObject.name

            
        }
    }
  })