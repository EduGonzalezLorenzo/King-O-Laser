

export interface Piece{
    posY: Number;
    posX: Number;
    token: string
}



export interface LaserPiece extends Piece{
    type: string;
    rotation: string;
}