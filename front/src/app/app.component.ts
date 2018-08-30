import { Component, OnInit } from '@angular/core';
import { AuthService } from './_services/index';
import { NgxPermissionsService } from 'ngx-permissions';

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html'
})
export class AppComponent implements OnInit {

    constructor(private permissionsService: NgxPermissionsService, public authService: AuthService) {
        sessionStorage.setItem('showMenu', 'true');
    }

    showMenu():boolean {
        return sessionStorage.getItem('showMenu') == 'false' ? false : true;
    }
    
    ngOnInit(): void {
        const user: any = this.authService.getUserInfo();
        if (user && user.authorities) {
            this.permissionsService.loadPermissions(user.authorities);
        }
    }
}
