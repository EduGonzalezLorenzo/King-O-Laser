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
}
