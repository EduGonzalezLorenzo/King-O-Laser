<template>
  <canvas
    ref="canvas"
    :width="canvasWidth"
    :height="canvasHeight"
    :mouseX="mouseX"
    :mouseY="mouseY"
    :selectedPieceY="selectedPieceY"
    :selectedPieceX="selectedPieceX"
    :selectedMovementY="selectedMovementY"
    :selectedMovementX="selectedMovementX"
    :rotationValue="rotationValue"
    @click="handleClick"
  />
  <div
    id="custom-menu"
    class="custom-menu"
  >
    <ul class="custom-menu-list flex flex-row">
      <li
        id="menu-item-1"
        class="cursor-pointer hover:bg-blue-700"
      >
        <img
          src="/img/commonIcon/arrowRight.png"
          class="w-12 min-w-[48px]"
        >
      </li>
      <li
        id="menu-item-2"
        class="custom-menu-item cursor-pointer"
      >
        <img
          src="/img/commonIcon/arrowLeft.png"
          class="w-12 rotate-240 min-w-[48px]"
        >
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, defineEmits } from "vue";
import { Cell } from "~/types/Cell";
import { Piece } from "~/types/Piece";
import { BoardDisposition } from "~/types/BoardDisposition";

const canvas = ref<HTMLCanvasElement | null>(null);

const canvasWidth = ref(560);
const canvasHeight = ref(700);

const updateCanvasSize = () => {
      console.log("height: "+window.innerHeight)
      console.log("width"+window.innerWidth)
      console.log("cellWidth: " + cellWidth.value)

      if(window.innerWidth > 1200) {
        canvasWidth.value = 560
        canvasHeight.value = 700
        cellWidth.value = 70
        cellHeight.value = 70
      }
      if(window.innerWidth > 1000 && window.innerWidth <= 1200) {
        canvasWidth.value = 480
        canvasHeight.value = 600
        cellWidth.value = 60
        cellHeight.value = 60
      }
      if(window.innerWidth > 800 && window.innerWidth <= 1000) {
        canvasWidth.value = 320
        canvasHeight.value = 400
        cellWidth.value = 40
        cellHeight.value = 40
      }
      if(window.innerWidth < 800) {
        canvasWidth.value = 280
        canvasHeight.value = 350
        cellWidth.value = 35
        cellHeight.value = 35
      }
    };

const tableRows = 10;
const tableColumns = 8;
const cellWidth = ref<number>(canvasWidth.value / tableColumns);
const cellHeight = ref<number>(canvasHeight.value / tableRows);
const mouseX = ref<number>(0);
const mouseY = ref<number>(0);
const selectedPieceY = ref<number>(0);
const selectedPieceX = ref<number>(0);
const selectedMovementY = ref<number>(0);
const selectedMovementX = ref<number>(0);
const rotationValue = ref<string>("");

function aux() {
  console.log(canvas.value)
}

const boardDisposition = {
  route: [
    [1, 0],
    [2, 0],
    [3, 0],
    [4, 1],
    [4, 2],
    [4, 3],
    [4, 4],
    [4, 5],
    [4, 6],
  ],
  response: "OK",
  message: "OUT",
  board: [
    // ACE DISPOSITION
    {
      owner: "PLAYER_ONE",
      posY: 3,
      posX: 7,
      rotation: "EAST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Defender",
    },
    {
      owner: "PLAYER_ONE",
      posY: 5,
      posX: 7,
      rotation: "EAST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Defender",
    },
    {
      owner: "PLAYER_ONE",
      posY: 4,
      posX: 4,
      rotation: "EAST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Bouncer",
    },
    {
      owner: "PLAYER_ONE",
      posY: 5,
      posX: 4,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Bouncer",
    },
    {
      owner: "PLAYER_ONE",
      posY: 9,
      posX: 4,
      rotation: "SOUTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_ONE",
      posY: 9,
      posX: 3,
      rotation: "EAST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_ONE",
      posY: 2,
      posX: 7,
      rotation: "EAST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_ONE",
      posY: 2,
      posX: 4,
      rotation: "EAST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_ONE",
      posY: 2,
      posX: 3,
      rotation: "SOUTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_ONE",
      posY: 3,
      posX: 2,
      rotation: "EAST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_ONE",
      posY: 7,
      posX: 6,
      rotation: "SOUTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_ONE",
      posY: 4,
      posX: 7,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.King",
    },
    {
      owner: "PLAYER_ONE",
      posY: 9,
      posX: 7,
      rotation: "WEST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Laser",
    },
    {
      owner: "PLAYER_TWO",
      posY: 6,
      posX: 0,
      rotation: "WEST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Defender",
    },
    {
      owner: "PLAYER_TWO",
      posY: 4,
      posX: 0,
      rotation: "WEST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Defender",
    },
    {
      owner: "PLAYER_TWO",
      posY: 4,
      posX: 3,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Bouncer",
    },
    {
      owner: "PLAYER_TWO",
      posY: 5,
      posX: 3,
      rotation: "EAST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Bouncer",
    },
    {
      owner: "PLAYER_TWO",
      posY: 0,
      posX: 3,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_TWO",
      posY: 0,
      posX: 4,
      rotation: "WEST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_TWO",
      posY: 7,
      posX: 3,
      rotation: "WEST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_TWO",
      posY: 7,
      posX: 4,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_TWO",
      posY: 6,
      posX: 5,
      rotation: "WEST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_TWO",
      posY: 2,
      posX: 1,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_TWO",
      posY: 7,
      posX: 0,
      rotation: "WEST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
    {
      owner: "PLAYER_TWO",
      posY: 5,
      posX: 0,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.King",
    },
    {
      owner: "PLAYER_TWO",
      posY: 0,
      posX: 0,
      rotation: "EAST",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Laser",
    },

    // END ACE DISPOSITION

  ],
};

const imagePaths = [
  "/img/kingolaser/UnknownPiece.jpg",
  "/img/kingolaser/BlueKing.png",
  "/img/kingolaser/BlueBlockN.png",
  "/img/kingolaser/BlueBlockS.png",
  "/img/kingolaser/BlueBlockE.png",
  "/img/kingolaser/BlueBlockW.png",
  "/img/kingolaser/BlueBouncerNS.png",
  "/img/kingolaser/BlueBouncerEW.png",
  "/img/kingolaser/BlueDeflectorN.png",
  "/img/kingolaser/BlueDeflectorS.png",
  "/img/kingolaser/BlueDeflectorE.png",
  "/img/kingolaser/BlueDeflectorW.png",
  "/img/kingolaser/BlueLaserN.png",
  "/img/kingolaser/BlueLaserS.png",
  "/img/kingolaser/BlueLaserE.png",
  "/img/kingolaser/BlueLaserW.png",
  "/img/kingolaser/RedKing.png",
  "/img/kingolaser/RedBlockN.png",
  "/img/kingolaser/RedBlockS.png",
  "/img/kingolaser/RedBlockE.png",
  "/img/kingolaser/RedBlockW.png",
  "/img/kingolaser/RedBouncerNS.png",
  "/img/kingolaser/RedBouncerEW.png",
  "/img/kingolaser/RedDeflectorN.png",
  "/img/kingolaser/RedDeflectorS.png",
  "/img/kingolaser/RedDeflectorE.png",
  "/img/kingolaser/RedDeflectorW.png",
  "/img/kingolaser/RedLaserN.png",
  "/img/kingolaser/RedLaserS.png",
  "/img/kingolaser/RedLaserE.png",
  "/img/kingolaser/RedLaserW.png",
];

const board = ref(new Array(tableRows));
for (let x = 0; x < tableRows; x++) {
  board.value[x] = new Array(tableColumns);
}

for (let x = 0; x < tableRows; x++) {
  for (let y = 0; y < tableColumns; y++) {
    board.value[x][y] = new Cell(cellWidth.value, cellHeight.value, x, y, false);
  }
}

const drawGrid = (ctx: CanvasRenderingContext2D) => {
  for (let x = 0; x < tableRows; x++) {
    for (let y = 0; y < tableColumns; y++) {
      showCell(board.value[x][y]);
    }
  }

  function showCell(cell: Cell) {
    ctx.fillStyle = "rgba(0,0,0, 0.5)";
    ctx.beginPath();
    ctx.rect(
      cell.posX * cellHeight.value,
      cell.posY * cellWidth.value,
      cellHeight.value,
      cellWidth.value
    );
    // ctx.stroke();
    // ctx.font = "30px Arial";
    // ctx.fillText(
    //   `${cell.posY}:${cell.posX}`,
    //   cell.posX * cellHeight + 50,
    //   cell.posY * cellWidth + 100
    // );
  }
};

const emit = defineEmits<{
  (
    e: "sendMovement",
    selectedPieceY: number,
    selectedPieceX: number,
    selectedMovementY: number,
    selectedMovementX: number,
    rotationValue: string
  ): void;
}>();

const handleClick = (event: MouseEvent) => {
  const menu = document.getElementById("custom-menu") as HTMLElement;

  mouseX.value = Math.floor(event.offsetX / (canvasWidth.value / tableColumns));
  mouseY.value = Math.floor(event.offsetY / (canvasHeight.value / tableRows));

  const ctx = canvas.value?.getContext("2d");

  if (ctx) {
    if (board.value[mouseY.value][mouseX.value] instanceof Piece) {
      menu.style.top = event.offsetY + "px";
      menu.style.left = event.offsetX + "px";
      menu.classList.add("show");

      selectedPieceY.value = mouseY.value;
      selectedPieceX.value = mouseX.value;

      ctx.clearRect(0, 0, canvasWidth.value, canvasHeight.value);
      drawGrid(ctx);
      chargeImages(imagePaths).then((images) => {
        if (imagesLoaded(images)) {
          drawBoard(ctx, boardDisposition, images);
        }
      });
      for (let x = 0; x < tableRows; x++) {
        for (let y = 0; y < tableColumns; y++) {
          if (board.value[x][y] instanceof Cell) {
            board.value[x][y].selectable = false;
          }
        }
      }

      for (let i = mouseX.value - 1; i <= mouseX.value + 1; i++) {
        for (let j = mouseY.value - 1; j <= mouseY.value + 1; j++) {
          if (
            i >= 0 &&
            i < board.value.length &&
            j >= 0 &&
            j < board.value.length
          ) {
            if (board.value[j][i] instanceof Piece) {
              ctx.fillStyle = "rgb(250,10,10, 0.5)";
              ctx.fillRect(
                i * cellHeight.value,
                j * cellWidth.value,
                cellHeight.value,
                cellWidth.value
              );
            } else if (board.value[j][i] instanceof Cell) {
              ctx.fillStyle = "rgb(10,250,10, 0.5)";
              ctx.fillRect(
                i * cellHeight.value,
                j * cellWidth.value,
                cellHeight.value,
                cellWidth.value
              );
              board.value[j][i].selectable = true;
            }
          }
        }
      }
    } else if (
      board.value[mouseY.value][mouseX.value] instanceof Cell &&
      board.value[mouseY.value][mouseX.value].selectable == true
    ) {
      emit(
        "sendMovement",
        selectedPieceY.value,
        selectedPieceX.value,
        mouseY.value,
        mouseX.value,
        rotationValue.value
      );
    }
  }
  aux()
};

const drawBoard = (
  ctx: CanvasRenderingContext2D,
  boardDisposition: BoardDisposition,
  images: HTMLImageElement[]
) => {
  const pieceList: Piece[] = boardDisposition.board;
  pieceList.forEach((piece) => {
    board.value[piece.posY][piece.posX].empty = false;
    board.value[piece.posY][piece.posX] = new Piece(
      piece.owner,
      piece.posY,
      piece.posX,
      piece.rotation,
      piece.pieceClass
    );
    const image = getPieceImage(piece, images);
    ctx.drawImage(
      image,
      piece.posX * cellHeight.value,
      piece.posY * cellWidth.value,
      cellHeight.value,
      cellWidth.value
    );
  });
};

function getPieceImage(piece: Piece, images: HTMLImageElement[]) {
  if (piece.owner == "PLAYER_ONE") {
    switch (piece.pieceClass) {
      case "com.telegame.code.models.kingolaser.pieces.King":
        return images[1];
        break;
      case "com.telegame.code.models.kingolaser.pieces.Defender":
        switch (piece.rotation) {
          case "NORTH":
            return images[2];
          case "SOUTH":
            return images[3];
          case "EAST":
            return images[4];
          case "WEST":
            return images[5];
        }
        return images[0];
      case "com.telegame.code.models.kingolaser.pieces.Bouncer":
        switch (piece.rotation) {
          case "NORTH":
            return images[6];
          case "SOUTH":
            return images[6];
          case "EAST":
            return images[7];
          case "WEST":
            return images[7];
        }
        return images[0];
      case "com.telegame.code.models.kingolaser.pieces.Deflector":
        switch (piece.rotation) {
          case "NORTH":
            return images[8];
          case "SOUTH":
            return images[9];
          case "EAST":
            return images[10];
          case "WEST":
            return images[11];
        }
        return images[0];
      case "com.telegame.code.models.kingolaser.pieces.Laser":
        switch (piece.rotation) {
          case "NORTH":
            return images[12];
          case "SOUTH":
            return images[13];
          case "EAST":
            return images[14];
          case "WEST":
            return images[15];
        }
        return images[0];
      default:
        return images[0];
    }
  } else {
    switch (piece.pieceClass) {
      case "com.telegame.code.models.kingolaser.pieces.King":
        return images[16];
        break;
      case "com.telegame.code.models.kingolaser.pieces.Defender":
        switch (piece.rotation) {
          case "NORTH":
            return images[17];
          case "SOUTH":
            return images[18];
          case "EAST":
            return images[19];
          case "WEST":
            return images[20];
        }
        return images[0];
      case "com.telegame.code.models.kingolaser.pieces.Bouncer":
        switch (piece.rotation) {
          case "NORTH":
            return images[21];
          case "SOUTH":
            return images[21];
          case "EAST":
            return images[22];
          case "WEST":
            return images[22];
        }
        return images[0];
      case "com.telegame.code.models.kingolaser.pieces.Deflector":
        switch (piece.rotation) {
          case "NORTH":
            return images[23];
          case "SOUTH":
            return images[24];
          case "EAST":
            return images[25];
          case "WEST":
            return images[26];
        }
        return images[0];
      case "com.telegame.code.models.kingolaser.pieces.Laser":
        switch (piece.rotation) {
          case "NORTH":
            return images[27];
          case "SOUTH":
            return images[28];
          case "EAST":
            return images[29];
          case "WEST":
            return images[30];
        }
        return images[0];
      default:
        return images[0];
    }
  }
}

function drawLaser(
  ctx: CanvasRenderingContext2D,
  boardDisposition: BoardDisposition
) {
  const thickness = 20;
  ctx.fillStyle = "rgb(100, 255, 100)";

  boardDisposition.route.forEach((target) => {
    ctx.beginPath();
    ctx.arc(
      target[1] * cellWidth.value + cellWidth.value / 2,
      target[0] * cellHeight.value + cellWidth.value / 2,
      thickness,
      0,
      2 * Math.PI
    );
    ctx.fill();
  });
}

onMounted(() => {
  updateCanvasSize();
  const ctx = canvas.value?.getContext("2d");

  if (ctx) {
    drawGrid(ctx);
    chargeImages(imagePaths).then((images) => {
      if (imagesLoaded(images)) {
        drawBoard(ctx, boardDisposition, images);
      }

      // drawLaser(ctx, boardDisposition);

    });
  }
  const menu = document.getElementById("custom-menu") as HTMLElement;
  canvas.value?.addEventListener("contextmenu", function (event) {
    if (event.button === 2) {
      handleClick(event);
      event.preventDefault();
    }
  });
  const menuItem1 = document.getElementById("menu-item-1") as HTMLElement;
  const menuItem2 = document.getElementById("menu-item-2") as HTMLElement;

  menuItem1.addEventListener("click", function () {
    rotationValue.value = "R";
    emit(
      "sendMovement",
      selectedPieceY.value,
      selectedPieceX.value,
      mouseY.value,
      mouseX.value,
      rotationValue.value
    );
    rotationValue.value = "";
    menu.classList.remove("show");
  });

  menuItem2.addEventListener("click", function () {
    rotationValue.value = "L";
    emit(
      "sendMovement",
      selectedPieceY.value,
      selectedPieceX.value,
      mouseY.value,
      mouseX.value,
      rotationValue.value
    );
    rotationValue.value = "";
    menu.classList.remove("show");
  });
  window.addEventListener('resize', updateCanvasSize)
});

function chargeImages(imagePaths: string[]): Promise<HTMLImageElement[]> {
  return Promise.all(
    imagePaths.map((path) => {
      return new Promise<HTMLImageElement>((resolve, reject) => {
        const img = new Image();
        img.src = path;

        img.onload = () => {
          resolve(img);
        };

        img.onerror = (err) => {
          reject(err);
        };
      });
    })
  );
}

function imagesLoaded(images: HTMLImageElement[]): boolean {
  for (let i = 0; i < images.length; i++) {
    if (!images[i].complete) {
      return false;
    }
  }
  return true;
}
</script>

<style>

canvas {
  background-image: url(../public/img/kingolaser/Board.jpg);
  background-size: contain;
}
.custom-menu {
  display: none;
}
.show {
  display: block;
  position: absolute;
  background-color: beige;
  padding: 10px;
  border-radius: 10px;
}
</style>
