import { reactive } from 'vue';

export interface Image {
  url: string;
  alt: string;
  title: string;
}

export default function useImages() {
  const images = reactive<Image[]>([
    {
      url: '/img/gamesSelectorImages/kingolaser.jpg',
      alt: 'Placeholder Image 1',
      title: 'LASER_BOARD',
    },
    {
      url: '/img/gamesSelectorImages/tictactoe.jpg',
      alt: 'Placeholder Image 2',
      title: 'Tic Tac Toe',
    },
    {
      url: '/img/gamesSelectorImages/PPTLS.jpg',
      alt: 'Placeholder Image 3',
      title: 'PPTLS',
    },
  ]);

  return { images };
}