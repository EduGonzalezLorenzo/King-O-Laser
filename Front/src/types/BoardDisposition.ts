import { Piece } from "./Piece"

export type BoardDisposition = {
    lastAction: string,
    pieces: Piece[],
    status: string,
    position: string,
}