import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { AuthService, AlertService, FinanceService } from '../_services/index';

import { SetSalaryModalComponent } from './set-salary.modal.component';

@Component({
    templateUrl: './salary.component.html'
})
export class FinanceSalaryComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;

    totalRecd = 0;
    totalSummary = 0;
    totalActual = 0;

    constructor(
        public authService: AuthService,
        private alertService: AlertService,
        private modalService: ModalDialogService,
        private viewRef: ViewContainerRef,
        private service: FinanceService) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    showSetSalaryPopup(doctor: any) {
        const options: any = {
            title: 'Лікар: ' + doctor.name,
            childComponent: SetSalaryModalComponent,
            data: doctor
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(); });
    }

    load() {
        this.loading = true;
        if (this.authService.isSuperadmin()) this.loadOld(); else this.loadNew();
    }
    
    loadOld() {
        this.sub = this.service.getSalaryOld().subscribe(
            data => this.parseData(data),
            error => this.parseError(error)
        );
    }
    
    loadNew() {
        this.sub = this.service.getSalary().subscribe(
            data => this.parseData(data),
            error => this.parseError(error)
        );
    }
    
    parseData(data) {
        this.data = data;
        this.totalSummary = 0;
        this.totalActual = 0;
        data.forEach( currentValue => {
            currentValue.lastName = currentValue.name.split(' ')[0];
            this.totalRecd += currentValue.recd;
            this.totalSummary += currentValue.total;
            this.totalActual += currentValue.actual;
        });
        this.loading = false;
    }
    
    parseError(error) {
        this.alertService.error('Помилка на сервері', false);
        this.loading = false;
        this.data = [];
    }
}
