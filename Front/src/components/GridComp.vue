<template>
  <canvas
    ref="canvas"
    :width="canvasWidth"
    :height="canvasHeight"
    :mouseX="mouseX"
    :mouseY="mouseY"
    @:click="handleClick"
  />
</template>

<script setup lang="ts">
import { ref, onMounted, defineEmits, reactive } from 'vue';
import { Cell } from '~/types/Cell';
import { Piece } from '~/types/Piece'
import createCell from '~/utils/createCell';
import { BoardDisposition } from '~/types/BoardDisposition'

const canvas = ref<HTMLCanvasElement | null>(null);
const canvasWidth = 960;
const canvasHeight = 1200;
const tableRows = 10;
const tableColumns = 8;
const cellWidth = canvasWidth / tableColumns;
const cellHeight = canvasHeight / tableRows;
const mouseX = ref<number>(0);
const mouseY = ref<number>(0);

const boardDisposition = {
  "route": [
    [
      1,
      0
    ],
    [
      2,
      0
    ],
    [
      3,
      0
    ],
    [
      4,
      0
    ],
    [
      5,
      0
    ],
    [
      6,
      0
    ],
    [
      7,
      0
    ],
    [
      8,
      0
    ],
    [
      9,
      0
    ]
  ],
  "response": "OK",
  "message": "OUT",
  "board": [
    {
      "owner": "PLAYER_ONE",
      "posX": 2,
      "posY": 1,
      "rotation": "NORTH",
      "pieceClass": "com.telegame.code.models.kingolaser.pieces.King"
    },
    {
      "owner": "PLAYER_TWO",
      "posX": 2,
      "posY": 2,
      "rotation": "NORTH",
      "pieceClass": "com.telegame.code.models.kingolaser.pieces.King"
    },
    {
      "owner": "PLAYER_ONE",
      "posX": 7,
      "posY": 2,
      "rotation": "NORTH",
      "pieceClass": "com.telegame.code.models.kingolaser.pieces.Defender"
    },
    {
      "owner": "PLAYER_ONE",
      "posX": 7,
      "posY": 9,
      "rotation": "NORTH",
      "pieceClass": "com.telegame.code.models.kingolaser.pieces.Laser"
    },
    {
      "owner": "PLAYER_TWO",
      "posX": 0,
      "posY": 0,
      "rotation": "SOUTH",
      "pieceClass": "com.telegame.code.models.kingolaser.pieces.Laser"
    },
    {
      "owner": "PLAYER_ONE",
      "posX": 6,
      "posY": 5,
      "rotation": "NORTH",
      "pieceClass": "com.telegame.code.models.kingolaser.pieces.Deflector"
    }
  ]
}




// const srcs: string[] = ['_nuxt/static/img/kingolaser/Board.jpg'];
// const images = srcs.map((src) => {
//   // eslint-disable-next-line
//   debugger
//     const image = new Image();
//     image.src = src;
//     return image;
// });
// const imagesLoaded = () => images.every((image) => image.complete);




const board = ref(new Array(tableRows))
for (let x = 0; x < tableRows; x++) {
  board.value[x] = new Array(tableColumns)
}

for (let x = 0; x < tableRows; x++) {
  for (let y = 0; y < tableColumns; y++) {
    board.value[x][y] = new Cell(cellWidth, cellHeight, x, y, false)
  }
}




const drawGrid = (ctx: CanvasRenderingContext2D) => {

  for (let x = 0; x < tableRows; x++) {
    for (let y = 0; y < tableColumns; y++) {
      showCell(board.value[x][y])
    }
  }

  function showCell(cell: Cell) {
    ctx.beginPath();
    ctx.rect(cell.posX * cellHeight, cell.posY * cellWidth, cell.height, cell.width);
    ctx.stroke();
    ctx.font = "30px Arial";
    ctx.fillText(`${cell.posY}:${cell.posX}`, cell.posX * cellHeight + 50, cell.posY * cellWidth + 100)
    ctx.fillStyle = 'rgba(150,150,255, 0.5)';
    ctx.fill();
  }

};

const emit = defineEmits<{
  (e: 'sendPosition', posY: number, posX: number): void
}>()

const handleClick = (event: MouseEvent) => {
  mouseX.value = Math.floor(event.offsetX / (canvasWidth / tableColumns));
  mouseY.value = Math.floor(event.offsetY / (canvasHeight / tableRows));

  const ctx = canvas.value?.getContext('2d');

  console.log(board.value[mouseY.value][mouseX.value])

  if (ctx) {
    if (board.value[mouseY.value][mouseX.value] instanceof Piece) {
      ctx.clearRect(0, 0, canvasWidth, canvasHeight)
      drawGrid(ctx);
      drawBoard(ctx, boardDisposition)
      for (let x = 0; x < tableRows; x++) {
        for (let y = 0; y < tableColumns; y++) {
          if (board.value[x][y] instanceof Cell) {
            board.value[x][y].selectable = false;
          }
        }
      }

      for (let i = mouseX.value - 1; i <= mouseX.value + 1; i++) {
        for (let j = mouseY.value - 1; j <= mouseY.value + 1; j++) {
          if (i >= 0 && i < board.value.length && j >= 0 && j < board.value[i].length) {
            if (board.value[j][i] instanceof Piece) {
              ctx.fillStyle = 'rgb(250,10,10, 0.5)';
              ctx.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth)
            } else if (board.value[j][i] instanceof Cell) {
              ctx.fillStyle = 'rgb(10,250,10, 0.5)';
              ctx.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth)
              board.value[j][i].selectable = true;
            }
          }
        }
      }
    }
  }

  emit('sendPosition', mouseY.value, mouseX.value)
};

const drawBoard = (ctx: CanvasRenderingContext2D, boardDisposition: BoardDisposition) => {
  const pieceList: Piece[] = boardDisposition.board
  pieceList.forEach(piece => {
    piece = new Piece(piece.owner, piece.posY, piece.posX, piece.rotation, piece.pieceClass)
    board.value[piece.posY][piece.posX].empty = false;
    board.value[piece.posY][piece.posX] = new Piece(piece.owner, piece.posY, piece.posX, piece.rotation, piece.pieceClass)
    ctx.fillStyle = piece.owner === 'PLAYER_ONE' ? 'darkred' : 'darkblue';
    ctx.fillRect(piece.posX * cellHeight, piece.posY * cellWidth, cellHeight, cellWidth)
    ctx.font = "20px Arial";
    ctx.fillStyle = '#fff'
    ctx.fillText(`Y${piece.posY}:X${piece.posX}`, (piece.posX * cellHeight) + 10, (piece.posY * cellWidth) + 50)
    ctx.fillText(`rotation: ${piece.rotation}`, (piece.posX * cellHeight) + 10, (piece.posY * cellWidth) + 70)
  });
}

onMounted(() => {
  const ctx = canvas.value?.getContext('2d');

  if (ctx) {
    drawGrid(ctx);
    drawBoard(ctx, boardDisposition)
    // if(imagesLoaded()) {
    //   ctx.drawImage(images[0], 0, 0)
    // }
  }
});

</script>

<style>
canvas {
  margin-top: -100px;
  scale: 0.8;
  /* background-image: url(../static/img/kingolaser/Board.jpg); */
}
</style>