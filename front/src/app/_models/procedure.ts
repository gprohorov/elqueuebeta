export class Procedure {
    id: number;
    procedureType: string;
    name: string;
    cabinet: number;
    number: number;
    percent: number;
    zoned: boolean;
    freeChoice: boolean;
    FOREIGN: number;
    VIP: number;
    BUSINESS: number;
    ALL_INCLUSIVE: number;
    SOCIAL: number;
    header: string;
    card: Card;

    constructor() {
        this.card = new Card();
    }
}

export class Card {
    days = 1;
    anytime = false;
    closeAfter: number[]  = new Array();
    activateAfter: number[]  = new Array();
    mustBeDoneBefore: number[]  = new Array();
}
