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
    @click="handleClick"
  />
  <div
    id="custom-menu"
    class="custom-menu"
  >
    <ul class="custom-menu-list">
      <li
        id="menu-item-1"
        class="cursor-pointer hover:bg-blue-700"
      >
        Rotar Izquierda
      </li>
      <li
        id="menu-item-2"
        class="custom-menu-item"
      >
        Rotar Derecha
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
const canvasWidth = 960;
const canvasHeight = 1200;
const tableRows = 10;
const tableColumns = 8;
const cellWidth = canvasWidth / tableColumns;
const cellHeight = canvasHeight / tableRows;
const mouseX = ref<number>(0);
const mouseY = ref<number>(0);
const selectedPieceY = ref<number>(0)
const selectedPieceX = ref<number>(0)
const selectedMovementY = ref<number>(0)
const selectedMovementX = ref<number>(0)

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
    {
      owner: "PLAYER_ONE",
      posX: 2,
      posY: 1,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.King",
    },
    {
      owner: "PLAYER_TWO",
      posX: 2,
      posY: 2,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.King",
    },
    {
      owner: "PLAYER_ONE",
      posX: 7,
      posY: 2,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Defender",
    },
    {
      owner: "PLAYER_ONE",
      posX: 7,
      posY: 9,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Laser",
    },
    {
      owner: "PLAYER_TWO",
      posX: 0,
      posY: 0,
      rotation: "SOUTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Laser",
    },
    {
      owner: "PLAYER_ONE",
      posX: 6,
      posY: 5,
      rotation: "NORTH",
      pieceClass: "com.telegame.code.models.kingolaser.pieces.Deflector",
    },
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
  "/img/kingolaser/BlueLaserN.jpg",
  "/img/kingolaser/BlueLaserS.jpg",
  "/img/kingolaser/BlueLaserE.jpg",
  "/img/kingolaser/BlueLaserW.jpg",
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
  "/img/kingolaser/RedLaserN.jpg",
  "/img/kingolaser/RedLaserS.jpg",
  "/img/kingolaser/RedLaserE.jpg",
  "/img/kingolaser/RedLaserW.jpg",
];

const board = ref(new Array(tableRows));
for (let x = 0; x < tableRows; x++) {
  board.value[x] = new Array(tableColumns);
}

for (let x = 0; x < tableRows; x++) {
  for (let y = 0; y < tableColumns; y++) {
    board.value[x][y] = new Cell(cellWidth, cellHeight, x, y, false);
  }
}

const drawGrid = (ctx: CanvasRenderingContext2D) => {
  for (let x = 0; x < tableRows; x++) {
    for (let y = 0; y < tableColumns; y++) {
      showCell(board.value[x][y]);
    }
  }

  function showCell(cell: Cell) {
    ctx.beginPath();
    ctx.rect(
      cell.posX * cellHeight,
      cell.posY * cellWidth,
      cell.height,
      cell.width
    );
    ctx.stroke();
    ctx.font = "30px Arial";
    ctx.fillText(
      `${cell.posY}:${cell.posX}`,
      cell.posX * cellHeight + 50,
      cell.posY * cellWidth + 100
    );
    // ctx.fillStyle = "rgba(150,150,255, 0.5)";
    // ctx.fill();
  }
};

const emit = defineEmits<{
  (e: "sendMovement", selectedPieceY: number, selectedPieceX: number, selectedMovementY: number, selectedMovementX: number): void;
}>();



const handleClick = (event: MouseEvent) => {
  mouseX.value = Math.floor(event.offsetX / (canvasWidth / tableColumns));
  mouseY.value = Math.floor(event.offsetY / (canvasHeight / tableRows));

  const ctx = canvas.value?.getContext("2d");

  if (ctx) {
    if (board.value[mouseY.value][mouseX.value] instanceof Piece) {

      selectedPieceY.value = mouseY.value
      selectedPieceX.value = mouseX.value

      ctx.clearRect(0, 0, canvasWidth, canvasHeight);
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
                i * cellHeight,
                j * cellWidth,
                cellHeight,
                cellWidth
              );
            } else if (board.value[j][i] instanceof Cell) {
              ctx.fillStyle = "rgb(10,250,10, 0.5)";
              ctx.fillRect(
                i * cellHeight,
                j * cellWidth,
                cellHeight,
                cellWidth
              );
              board.value[j][i].selectable = true;
            }
          }
        }
      }
    } else if (board.value[mouseY.value][mouseX.value] instanceof Cell && board.value[mouseY.value][mouseX.value].selectable == true) {
      emit("sendMovement", selectedPieceY.value, selectedPieceX.value, mouseY.value, mouseX.value)
    }
  }
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
    const image = getPieceImage(piece, images)
    ctx.drawImage(image, piece.posX * cellHeight, piece.posY * cellWidth, cellHeight, cellWidth)
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
            return images[2]
          case "SOUTH":
            return images[3]
          case "EAST":
            return images[4]
          case "WEST":
            return images[5]
        }
        return images[0]
      case "com.telegame.code.models.kingolaser.pieces.Bouncer":
        switch (piece.rotation) {
          case "NORTH":
            return images[6]
          case "SOUTH":
            return images[6]
          case "EAST":
            return images[7]
          case "WEST":
            return images[7]
        }
        return images[0]
      case "com.telegame.code.models.kingolaser.pieces.Deflector":
        switch (piece.rotation) {
          case "NORTH":
            return images[8]
          case "SOUTH":
            return images[9]
          case "EAST":
            return images[10]
          case "WEST":
            return images[11]
        }
        return images[0]
      case "com.telegame.code.models.kingolaser.pieces.Laser":
        switch (piece.rotation) {
          case "NORTH":
            return images[12]
          case "SOUTH":
            return images[13]
          case "EAST":
            return images[14]
          case "WEST":
            return images[15]
        }
        return images[0]
      default:
        return images[0]
    }
  } else {
    switch (piece.pieceClass) {
      case "com.telegame.code.models.kingolaser.pieces.King":
        return images[16];
        break;
      case "com.telegame.code.models.kingolaser.pieces.Defender":
        switch (piece.rotation) {
          case "NORTH":
            return images[17]
          case "SOUTH":
            return images[18]
          case "EAST":
            return images[19]
          case "WEST":
            return images[20]
        }
        return images[0]
      case "com.telegame.code.models.kingolaser.pieces.Bouncer":
        switch (piece.rotation) {
          case "NORTH":
            return images[21]
          case "SOUTH":
            return images[21]
          case "EAST":
            return images[22]
          case "WEST":
            return images[22]
        }
        return images[0]
      case "com.telegame.code.models.kingolaser.pieces.Deflector":
        switch (piece.rotation) {
          case "NORTH":
            return images[23]
          case "SOUTH":
            return images[24]
          case "EAST":
            return images[25]
          case "WEST":
            return images[26]
        }
        return images[0]
      case "com.telegame.code.models.kingolaser.pieces.Laser":
        switch (piece.rotation) {
          case "NORTH":
            return images[27]
          case "SOUTH":
            return images[28]
          case "EAST":
            return images[29]
          case "WEST":
            return images[30]
        }
        return images[0]
      default:
        return images[0]
    }
  }
}

function drawLaser(ctx: CanvasRenderingContext2D, boardDisposition: BoardDisposition,) {

  const thickness = 20
  ctx.fillStyle = "rgb(100, 255, 100)"

  boardDisposition.route.forEach(target => {
    ctx.beginPath()
    ctx.arc(target[1] * cellWidth + (cellWidth / 2), target[0] * cellHeight + (cellWidth / 2), thickness, 0, 2 * Math.PI)
    ctx.fill()
  });
}

onMounted(() => {
  const ctx = canvas.value?.getContext("2d");

  if (ctx) {
    drawGrid(ctx);
    chargeImages(imagePaths).then((images) => {
      if (imagesLoaded(images)) {
        drawBoard(ctx, boardDisposition, images);

      }
      drawLaser(ctx, boardDisposition)
    });
  }
  const menu = document.getElementById("custom-menu") as HTMLElement;
  canvas.value?.addEventListener("contextmenu", function (event) {
    if (event.button === 2) {
      event.preventDefault();
      menu.style.top = event.offsetY + "px";
      menu.style.left = event.offsetX + "px";
      menu.classList.add("show");
    }
  });
  const menuItem1 = document.getElementById("menu-item-1") as HTMLElement;
  const menuItem2 = document.getElementById("menu-item-2") as HTMLElement;

  menuItem1.addEventListener("click", function () {
    console.log("Rotar Izquierda");
    menu.classList.remove("show");
  });

  menuItem2.addEventListener("click", function () {
    console.log("Rotar Derecha");
    menu.classList.remove("show");
  });
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
  /* margin-top: -100px; */
  /* scale: 0.8;  */
  background-image: url(../public/img/kingolaser/Board.jpg);
  width: 100%;
  height: 100%;
}
.custom-menu{
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