import { Piece } from "./Piece"

// export interface BoardDisposition {
//     route: number[][],
//     response: string,
//     message: string,
//     board: Piece[]
// }

export type BoardDisposition = {
    lastAction: string,
    pieces: Piece[]
}