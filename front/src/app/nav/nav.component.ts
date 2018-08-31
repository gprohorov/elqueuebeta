import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { AuthService, UtilService, AlertService } from '../_services/index';

@Component({
    selector: 'app-nav',
    templateUrl: './nav.component.html'
})
export class NavComponent implements OnInit, OnDestroy {

    user: any;
    navbarOpen = false;
    sub: Subscription;

    constructor(
        public authService: AuthService,
        private utilService: UtilService,
        private alertService: AlertService,
        private router: Router
    ) { }

    ngOnInit() {
        if (this.authService.isAuth()) {
            this.user = this.authService.getUserInfo();
        }
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }
   
    toggleNavbar() {
      this.navbarOpen = !this.navbarOpen;
    }

    resetDB() {
        if (confirm('Reset DB?')) {
            this.utilService.resetDB().subscribe(() => {
                this.alertService.success('DB has been reseted.', true);
                this.router.navigate(['']);
            });
        }
    }
}
