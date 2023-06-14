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
        class="custom-menu-item cursor-pointer"
      >
        <img
          id="arrow_1"
          src="/img/commonIcon/arrowRight.webp"
        >
      </li>
      <li
        id="menu-item-2"
        class="custom-menu-item cursor-pointer"
      >
        <img
          id="arrow_2"
          src="/img/commonIcon/arrowLeft.webp"
          class="rotate-240"
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
const imagesArr = ref<HTMLImageElement[]>([])
const boardDisposition = reactive<BoardDisposition>({
  lastAction: "",
  pieces: [],
  status: "",
  position: ""
});

const imagePaths = [
  "/img/kingolaser/UnknownPiece.webp",
  "/img/kingolaser/BlueKing.webp",
  "/img/kingolaser/BlueBlockN.webp",
  "/img/kingolaser/BlueBlockS.webp",
  "/img/kingolaser/BlueBlockE.webp",
  "/img/kingolaser/BlueBlockW.webp",
  "/img/kingolaser/BlueBouncerNS.webp",
  "/img/kingolaser/BlueBouncerEW.webp",
  "/img/kingolaser/BlueDeflectorN.webp",
  "/img/kingolaser/BlueDeflectorS.webp",
  "/img/kingolaser/BlueDeflectorE.webp",
  "/img/kingolaser/BlueDeflectorW.webp",
  "/img/kingolaser/BlueLaserN.webp",
  "/img/kingolaser/BlueLaserS.webp",
  "/img/kingolaser/BlueLaserE.webp",
  "/img/kingolaser/BlueLaserW.webp",
  "/img/kingolaser/RedKing.webp",
  "/img/kingolaser/RedBlockN.webp",
  "/img/kingolaser/RedBlockS.webp",
  "/img/kingolaser/RedBlockE.webp",
  "/img/kingolaser/RedBlockW.webp",
  "/img/kingolaser/RedBouncerNS.webp",
  "/img/kingolaser/RedBouncerEW.webp",
  "/img/kingolaser/RedDeflectorN.webp",
  "/img/kingolaser/RedDeflectorS.webp",
  "/img/kingolaser/RedDeflectorE.webp",
  "/img/kingolaser/RedDeflectorW.webp",
  "/img/kingolaser/RedLaserN.webp",
  "/img/kingolaser/RedLaserS.webp",
  "/img/kingolaser/RedLaserE.webp",
  "/img/kingolaser/RedLaserW.webp",
  "/img/kingolaser/laser_flare.webp",
];

const route = useRoute();
const id = ref(route.params.id);
const jwt = ref<String>("");

const updateCanvasSize = () => {
  const ctx = canvas.value?.getContext("2d");
  if (window.innerWidth > 1200) {
    canvasWidth.value = 560;
    canvasHeight.value = 700;
    cellWidth.value = 70;
    cellHeight.value = 70;
    if (ctx) {
      chargeImages(imagesArr.value).then((images) => {
        if (imagesLoaded(images)) {
          drawBoard(ctx, boardDisposition, images);
          drawLaser(ctx, boardDisposition, images);
        }
      });
    }
  }
  if (window.innerWidth > 1000 && window.innerWidth <= 1200) {
    canvasWidth.value = 480 * 2;
    canvasHeight.value = 600 * 2;
    cellWidth.value = 60 * 2;
    cellHeight.value = 60 * 2;
    if (ctx) {
      chargeImages(imagesArr.value).then((images) => {
        if (imagesLoaded(images)) {
          drawBoard(ctx, boardDisposition, images);
          drawLaser(ctx, boardDisposition, images);
        }
      });
    }
  }
  if (window.innerWidth > 800 && window.innerWidth <= 1000) {
    canvasWidth.value = 320 * 2;
    canvasHeight.value = 400 * 2;
    cellWidth.value = 40 * 2;
    cellHeight.value = 40 * 2;
    if (ctx) {
      chargeImages(imagesArr.value).then((images) => {
        if (imagesLoaded(images)) {
          drawBoard(ctx, boardDisposition, images);
          drawLaser(ctx, boardDisposition, images);
        }
      });
    }
  }
  if (window.innerWidth < 750) {
    canvasWidth.value = 280;
    canvasHeight.value = 350;
    cellWidth.value = 35;
    cellHeight.value = 35;
    if (ctx) {
      chargeImages(imagesArr.value).then((images) => {
        if (imagesLoaded(images)) {
          drawBoard(ctx, boardDisposition, images);
          drawLaser(ctx, boardDisposition, images);
        }
      });
    }
  }
};

const board = ref(new Array(tableRows));

for (let x = 0; x < tableRows; x++) {
  board.value[x] = new Array(tableColumns);
}

for (let x = 0; x < tableRows; x++) {
  for (let y = 0; y < tableColumns; y++) {
    board.value[x][y] = new Cell(
      cellWidth.value,
      cellHeight.value,
      x,
      y,
      false
    );
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

const checkValidTurn = () => {
  if ((boardDisposition.status === "PLAYER_ONE_TURN" && boardDisposition.position === "P1") || (boardDisposition.status === "PLAYER_TWO_TURN" && boardDisposition.position === "P2")) {
    return true;
  } else {
    return false;
  }
}

const checkValidPiece = (piece: Piece) => {
  if ((piece.owner === "PLAYER_ONE" && boardDisposition.status === "PLAYER_ONE_TURN") || (piece.owner === "PLAYER_TWO" && boardDisposition.status === "PLAYER_TWO_TURN")) {
    return true;
  } else {
    return false;
  }
}

const handleClick = (event: MouseEvent) => {
  if (checkValidTurn()) {
    const menu = document.getElementById("custom-menu") as HTMLElement;
    const menuItem1 = document.getElementById("menu-item-1") as HTMLElement;
    const menuItem2 = document.getElementById("menu-item-2") as HTMLElement;
    const arrow1 = document.getElementById("arrow_1") as HTMLElement;
    const arrow2 = document.getElementById("arrow_2") as HTMLElement;

    mouseX.value = Math.floor(event.offsetX / (canvasWidth.value / tableColumns));
    mouseY.value = Math.floor(event.offsetY / (canvasHeight.value / tableRows));

    const ctx = canvas.value?.getContext("2d");

    if (ctx) {
      if (board.value[mouseY.value][mouseX.value] instanceof Piece) {
        const piece = board.value[mouseY.value][mouseX.value]
        if (checkValidPiece(piece)) {
          menu.style.top = (mouseY.value * cellHeight.value) + ((window.innerHeight - canvasHeight.value) / 2) + (cellHeight.value / 8) + "px";
          menu.style.left = (mouseX.value * cellHeight.value) + "px";

          menuItem1.style.height = (cellHeight.value / 2) + "px"
          menuItem2.style.height = (cellHeight.value / 2) + "px"
          arrow1.style.height = (cellHeight.value / 2) + "px"
          arrow2.style.height = (cellHeight.value / 2) + "px"
          arrow1.style.width = (cellWidth.value / 2) + "px"
          arrow2.style.width = (cellWidth.value / 2) + "px"
          menu.classList.add("show");

          selectedPieceY.value = mouseY.value;
          selectedPieceX.value = mouseX.value;

          ctx.clearRect(0, 0, canvasWidth.value, canvasHeight.value);
          drawGrid(ctx);
          chargeImages(imagesArr.value).then((images) => {
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
  }
};

const drawBoard = (
  ctx: CanvasRenderingContext2D,
  boardDisposition: BoardDisposition,
  images: HTMLImageElement[]
) => {
  const pieceList: Piece[] = boardDisposition.pieces;
  pieceList.forEach((piece) => {
    board.value[piece.y][piece.x].empty = false;
    board.value[piece.y][piece.x] = new Piece(
      piece.owner,
      piece.y,
      piece.x,
      piece.rotation,
      piece.type
    );
    const image = getPieceImage(piece, images);
    ctx.drawImage(
      image,
      piece.x * cellHeight.value,
      piece.y * cellWidth.value,
      cellHeight.value,
      cellWidth.value
    );
  });
};

function getPieceImage(piece: Piece, images: HTMLImageElement[]) {
  if (piece.owner == "PLAYER_ONE") {
    switch (piece.type) {
      case "com.telegame.code.models.games.laserboard.pieces.King":
        return images[1];
        break;
      case "com.telegame.code.models.games.laserboard.pieces.Defender":
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
      case "com.telegame.code.models.games.laserboard.pieces.Bouncer":
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
      case "com.telegame.code.models.games.laserboard.pieces.Deflector":
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
      case "com.telegame.code.models.games.laserboard.pieces.Laser":
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
    switch (piece.type) {
      case "com.telegame.code.models.games.laserboard.pieces.King":
        return images[16];
        break;
      case "com.telegame.code.models.games.laserboard.pieces.Defender":
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
      case "com.telegame.code.models.games.laserboard.pieces.Bouncer":
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
      case "com.telegame.code.models.games.laserboard.pieces.Deflector":
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
      case "com.telegame.code.models.games.laserboard.pieces.Laser":
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
  boardDisposition: BoardDisposition,
  images: HTMLImageElement[]
) {
  const trimmed = boardDisposition.lastAction;
  const steps = trimmed.split("*");
  const route: number[][] = steps.map((step) => {

    const coordinates = step.split(',')
    return coordinates.map(Number)
  })

  ctx.fillStyle = "rgb(100, 255, 100)";
  route.forEach((target: number[]) => {
    ctx.drawImage(
      images[31],
      target[1] * cellHeight.value,
      target[0] * cellWidth.value,
      cellHeight.value,
      cellWidth.value
    );
  });
}

onMounted(async () => {
  const localStore = localStorage.getItem("jwt");
  jwt.value = localStore as String;
  await fetch("http://localhost:8080/match/" + id.value, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + jwt.value,
    },
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      (boardDisposition.lastAction = data.lastAction),
        (boardDisposition.pieces = data.pieces),
        (boardDisposition.status = data.status),
        (boardDisposition.position = data.position);
    });

  const ctx = canvas.value?.getContext("2d");
  imagesArr.value = imagePaths.map((src) => {
    const image = new Image();
    image.src = src;
    return image;
  });
  if (ctx) {
    drawGrid(ctx);
    await chargeImages(imagesArr.value).then((images) => {
      if (imagesLoaded(images)) {
        drawBoard(ctx, boardDisposition, images);
      }
      drawLaser(ctx, boardDisposition, images);
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

  menuItem1?.addEventListener("click", function () {
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

  menuItem2?.addEventListener("click", function () {
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
  window.addEventListener("resize", updateCanvasSize);
  updateCanvasSize();
});

function chargeImages(imagePaths: HTMLImageElement[]): Promise<HTMLImageElement[]> {
  return Promise.all(
    imagePaths.map((path) => {
      return new Promise<HTMLImageElement>((resolve, reject) => {
        const img = new Image();
        img.src = path.src;

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
  background-image: url(../public/img/kingolaser/Board.webp);
  background-size: contain;
}

.custom-menu {
  display: none;
}

.custom-menu-item:hover {
  background-color: lightgreen;
  border-radius: 10px;
}

.show {
  display: block;
  position: absolute;
  background-color: rgb(173, 216, 230, 0.5);
  padding-top: 5px;
  padding-bottom: 5px;
  border-radius: 10px;
}
</style>
