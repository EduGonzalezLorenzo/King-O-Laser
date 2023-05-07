<template>
  <canvas ref="canvas" :width="canvasWidth" :height="canvasHeight" :mouseX="mouseX" :mouseY="mouseY" @:click="handleClick"/>
</template>

<script setup lang="ts">
import { defineComponent, ref, onMounted, defineEmits } from 'vue';
import { Cell } from '~/types/Cell';
import createCell from '~/utils/createCell';

    const canvas = ref<HTMLCanvasElement | null>(null);
    const canvasWidth = 1200;
    const canvasHeight = 1500;
    const tableRows = 10;
    const tableColumns = 8;
    const mouseX = ref<number>(0);
    const mouseY = ref<number>(0);



    const drawTable = (ctx: CanvasRenderingContext2D) => {
      const cellWidth = canvasWidth / tableColumns;
      const cellHeight = canvasHeight / tableRows;

      const board = new Array(tableColumns)

      for (let x = 0; x < board.length; x++) {
        board[x] = new Array(tableRows)
        for (let y = 0; y < tableRows; y++) {

          board[x][y] = createCell(cellWidth, cellHeight, y, x, "#fff")
          show(board[x][y])
        }
      }


      function show(cell: Cell) {
        ctx.beginPath();
        ctx.rect(cell.posX * cellHeight, cell.posY * cellWidth, cell.height, cell.width);
        ctx.stroke();
        ctx.font = "30px Arial";
        ctx.fillText(`${cell.posY}:${cell.posX}`, cell.posX * cellHeight + 50, cell.posY * cellWidth + 100)
        ctx.fillStyle = 'rgba(150,150,255, 0.5)';
        ctx.fill();
      }


      console.log({ board })
    };

    onMounted(() => {
      const ctx = canvas.value?.getContext('2d');
      if (ctx) {
        drawTable(ctx);
      }

    });

    const emit = defineEmits<{
      (e: 'sendPosition', posY: number, posX: number): void
    }>()

    // const emit = defineEmits(['sendPosition'])

    const handleClick = (event: MouseEvent) => {
      mouseX.value = Math.floor(event.offsetX / (canvasWidth / tableColumns));
      mouseY.value = Math.floor(event.offsetY / (canvasHeight / tableRows));
      console.log(mouseY.value, mouseX.value)
      emit('sendPosition', mouseY.value, mouseX.value)

    };

</script>
