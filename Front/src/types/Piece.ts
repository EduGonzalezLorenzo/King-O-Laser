// export interface Piece{
//     owner: string;
//     posY: number;
//     posX: number;
//     rotation: string;
//     class: string;
// }

export class Piece {
  owner: string;
  y: number;
  x: number;
  rotation: string;
  type: string;

  constructor(
    owner: string,
    y: number,
    x: number,
    rotation: string,
    type: string
  ) {
    this.owner = owner;
    this.y = y;
    this.x = x;
    this.rotation = rotation;
    this.type = type;
  }

//   contains(mouseX: number, mouseY: number, cellWidth: number) {
//     return this.posX < mouseX && this.posX + cellWidth > mouseX && this.posY < mouseY && this.posY + cellWidth > mouseY
// }
}
