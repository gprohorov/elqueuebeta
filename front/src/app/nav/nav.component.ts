import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { AuthService, AlertService } from '../_services/index';

import { GiveSalaryModalComponent } from '../finance/give-salary.modal.component';
import { KassaTozeroModalComponent } from '../finance/kassa-tozero.modal.component';
import { KassaAddOutcomeModalComponent } from '../finance/kassa-add-outcome.modal.component';

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

    showKassaAddOutcomePopupSA() {
        this.modalService.openDialog(this.viewRef, {
            title: 'Видача з каси',
            childComponent: KassaAddOutcomeModalComponent,
            data: { SA: true }
        });
    }

    showGiveSalaryPopupSA() {
        this.modalService.openDialog(this.viewRef, {
            title: 'Видача зарплати лікарю',
            childComponent: GiveSalaryModalComponent,
            data: { SA: true }
        });
    }

    showKassaAddOutcomePopup() {
        this.modalService.openDialog(this.viewRef, {
            title: 'Видача з каси',
            childComponent: KassaAddOutcomeModalComponent,
            data: { SA: false }
        });
    }
    
    showGiveSalaryPopup() {
        this.modalService.openDialog(this.viewRef, {
            title: 'Видача зарплати лікарю',
            childComponent: GiveSalaryModalComponent,
            data: { SA: false }
        });
    }

    showKassaTozeroPopup() {
        this.modalService.openDialog(this.viewRef, {
            title: 'Здача каси',
            childComponent: KassaTozeroModalComponent
        });
    }
}
