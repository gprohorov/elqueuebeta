import { Component } from '@angular/core';
import { AuthService } from './_services/index';

@Component({
    moduleId: module.id,
    selector: 'app-root',
    templateUrl: 'app.component.html'
})

export class AppComponent {

  constructor(public authService: AuthService) {}

}
