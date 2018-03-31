import { Component } from '@angular/core';
import { AuthenticationService } from './_services/authentication.service';

@Component({
    moduleId: module.id,
    selector: 'app',
    templateUrl: 'app.component.html'
})

export class AppComponent { 

  constructor(public authenticationService: AuthenticationService) {}

}