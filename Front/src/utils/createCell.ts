import { Cell } from "~/types/Cell";

export default function createCell(width: number, height: number, posY: number, posX: number, selectable: boolean) {
    const cell: Cell = {
        width: width,
        height: height,
        posY: posY,
        posX: posX,
        selectable: selectable
    } 
    return cell;
}