import { reactive } from 'vue';

export interface Image {
  url: string;
  alt: string;
  title: string;
}

export default function useImages() {
  const images = reactive<Image[]>([
    {
      url: 'https://via.placeholder.com/300',
      alt: 'Placeholder Image 1',
      title: 'LASER_BOARD',
    },
    {
      url: 'https://via.placeholder.com/300',
      alt: 'Placeholder Image 2',
      title: 'Tic Tac Toe',
    },
    {
      url: 'https://via.placeholder.com/300',
      alt: 'Placeholder Image 3',
      title: 'Image 3',
    },
  ]);

  return { images };
}