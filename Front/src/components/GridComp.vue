<template>
  <canvas
    ref="canvas"
    :width="canvasWidth"
    :height="canvasHeight"
  />
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';

export default defineComponent({
  name: 'CanvasTable',
  setup() {
    const canvas = ref<HTMLCanvasElement | null>(null);
    const canvasWidth = 600;
    const canvasHeight = 800;
    const tableRows = 10;
    const tableColumns = 8;

    const drawTable = (ctx: CanvasRenderingContext2D) => {
      const cellWidth = canvasWidth / tableColumns;
      const cellHeight = canvasHeight / tableRows;

      for (let i = 0; i < tableRows; i++) {
        for (let j = 0; j < tableColumns; j++) {
          ctx.strokeStyle = 'black';
          ctx.strokeRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
        }
      }
    };

    onMounted(() => {
      const ctx = canvas.value?.getContext('2d');
      if (ctx) {
        drawTable(ctx);
      }
    });

    return { canvasWidth, canvasHeight, canvas };
  },
});
</script>
