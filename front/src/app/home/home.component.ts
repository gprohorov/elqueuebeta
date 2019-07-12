import { Component } from '@angular/core';

@Component({
    templateUrl: './home.component.html'
})
export class HomeComponent {

    constructor() { sessionStorage.setItem('showMenu', 'false'); }
}
