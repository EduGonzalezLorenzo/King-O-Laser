import { Cell } from "~/types/Cell";

export default function createCell(width: number, height: number, posY: number, posX: number, color: string) {
    const cell: Cell = {
        width: width,
        height: height,
        posY: posY,
        posX: posX,
        color: color,
        empty: true
    } 
    return cell;
}