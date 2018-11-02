import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { AuthService, UtilService, AlertService } from '../_services/index';

import { GiveSalaryModalComponent } from '../finance/give-salary.modal.component';
import { KassaTozeroModalComponent } from '../finance/kassa-tozero.modal.component';

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
        private modalService: ModalDialogService,
        private viewRef: ViewContainerRef,
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

    showGiveSalaryPopup() {
        this.modalService.openDialog(this.viewRef, {
            title: 'Видача зарплати лікарю',
            childComponent: GiveSalaryModalComponent
        });
    }

    showKassaTozeroPopup() {
        this.modalService.openDialog(this.viewRef, {
            title: 'Здача каси',
            childComponent: KassaTozeroModalComponent
        });
    }

    resetDB() {
        if (confirm('Reset DB?')) {
            this.utilService.resetDB().subscribe(() => {
                this.alertService.success('DB has been reseted.', true);
                this.router.navigate(['']);
            });
        }
    }
    
    Task1() {
        if (confirm('Do Task1?')) {
            this.utilService.Task1().subscribe(() => {
                this.alertService.success('Task1 passed.', true);
                this.router.navigate(['']);
            });
        }
    }
    
    Task2() {
        if (confirm('Do Task2?')) {
            this.utilService.Task2().subscribe(() => {
                this.alertService.success('Task2 passed.', true);
                this.router.navigate(['']);
            });
        }
    }
}
