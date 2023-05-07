<template>
  <canvas ref="canvas" :width="canvasWidth" :height="canvasHeight" :mouseX="mouseX" :mouseY="mouseY"
    @:click="handleClick" />
</template>

<script setup lang="ts">
import { defineComponent, ref, onMounted, defineEmits } from 'vue';
import { Cell } from '~/types/Cell';
// import { Piece } from '~/types/Cell'
import createCell from '~/utils/createCell';
import { BoardDisposition } from '~/types/BoardDisposition'

const canvas = ref<HTMLCanvasElement | null>(null);
const canvasWidth = 1200;
const canvasHeight = 1500;
const tableRows = 10;
const tableColumns = 8;
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
      "class": "com.telegame.code.models.kingolaser.pieces.King"
    },
    {
      "owner": "PLAYER_TWO",
      "posX": 2,
      "posY": 2,
      "rotation": "NORTH",
      "class": "com.telegame.code.models.kingolaser.pieces.King"
    },
    {
      "owner": "PLAYER_ONE",
      "posX": 7,
      "posY": 2,
      "rotation": "NORTH",
      "class": "com.telegame.code.models.kingolaser.pieces.Defender"
    },
    {
      "owner": "PLAYER_ONE",
      "posX": 7,
      "posY": 9,
      "rotation": "NORTH",
      "class": "com.telegame.code.models.kingolaser.pieces.Laser"
    },
    {
      "owner": "PLAYER_TWO",
      "posX": 0,
      "posY": 0,
      "rotation": "SOUTH",
      "class": "com.telegame.code.models.kingolaser.pieces.Laser"
    },
    {
      "owner": "PLAYER_ONE",
      "posX": 6,
      "posY": 5,
      "rotation": "NORTH",
      "class": "com.telegame.code.models.kingolaser.pieces.Deflector"
    }
  ]
}



const drawGrid = (ctx: CanvasRenderingContext2D) => {
  const cellWidth = canvasWidth / tableColumns;
  const cellHeight = canvasHeight / tableRows;

  const board = new Array(tableColumns)
  for (let x = 0; x < board.length; x++) {
    board[x] = new Array(tableRows)
    for (let y = 0; y < tableRows; y++) {

      board[x][y] = createCell(cellWidth, cellHeight, y, x, "#fff")
      showCell(board[x][y])
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

onMounted(() => {
  const ctx = canvas.value?.getContext('2d');
  if (ctx) {
    drawGrid(ctx);
    drawBoard(ctx, boardDisposition)
  }

});

const emit = defineEmits<{
  (e: 'sendPosition', posY: number, posX: number): void
}>()


const handleClick = (event: MouseEvent) => {
  mouseX.value = Math.floor(event.offsetX / (canvasWidth / tableColumns));
  mouseY.value = Math.floor(event.offsetY / (canvasHeight / tableRows));


  emit('sendPosition', mouseY.value, mouseX.value)
};

const drawBoard = (ctx: CanvasRenderingContext2D, boardDisposition: BoardDisposition) => {
    for (const piece of boardDisposition.board) {
      ctx.fillStyle = piece.owner === 'PLAYER_ONE' ? 'darkred' : 'darkblue';
      ctx.fillRect(piece.posX * 150, piece.posY * 150, 150, 150)
      ctx.font = "30px Arial";
      ctx.fillStyle = '#fff'
    ctx.fillText(`Y: ${piece.posY}/ X: ${piece.posX}`, (piece.posX * 150) + 10, (piece.posY * 150) +50)
    ctx.fillText(`rotation: ${piece.rotation}`, (piece.posX * 150) + 10, (piece.posY * 150) +70)
    }
  }

</script>

<style>
canvas {
  margin-top: -250px;
  scale: 0.6;
}
</style>
