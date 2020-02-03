import { Component } from '@angular/core';
import { config } from '../../config';

@Component({
    templateUrl: './home.component.html'
})
export class HomeComponent {

    address_info: any;
    
    constructor() {
        sessionStorage.setItem('showMenu', 'false');
        this.address_info = config.address_info;
    }
}
