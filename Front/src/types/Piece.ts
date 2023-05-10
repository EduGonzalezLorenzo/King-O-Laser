// export interface Piece{
//     owner: string;
//     posY: number;
//     posX: number;
//     rotation: string;
//     class: string;
// }

export class Piece {
  owner: string;
  posY: number;
  posX: number;
  rotation: string;
  pieceClass: string;

  constructor(
    owner: string,
    posY: number,
    posX: number,
    rotation: string,
    pieceClass: string
  ) {
    this.owner = owner;
    this.posY = posY;
    this.posX = posX;
    this.rotation = rotation;
    this.pieceClass = pieceClass;
  }
}
