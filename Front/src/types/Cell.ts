export class Cell{
    width: number;
    height: number;
    posY: number;
    posX: number;
    selectable: boolean;
    constructor(
        width: number,
        height: number,
        posY: number,
        posX: number,
        selectable: boolean
    ) {
        this.width = width;
        this.height = height;
        this.posY = posY;
        this.posX = posX;
        this.selectable = selectable;
    }
}

