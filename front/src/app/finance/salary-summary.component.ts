import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { AlertService, FinanceService } from '../_services/index';

import { DoctorSalaryHistoryModalComponent } from './doctor-salary-history.modal.component';

@Component({
    templateUrl: './salary-summary.component.html'
})
export class FinanceSalarySummaryComponent implements OnInit, OnDestroy {

    loading = false;
    sub: Subscription;
    data: any;
    from: string;
    to: string;

    totalRecd = 0;
    totalSummary = 0;
    totalActual = 0;

    constructor(
        private alertService: AlertService,
        private modalService: ModalDialogService,
        private viewRef: ViewContainerRef,
        private service: FinanceService) { }

    ngOnInit() {
        const today = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000))
            .toISOString().slice(0, -14); 
        this.from = today;
        this.to = today;
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    editDoctor(id) {
        window.open('/#/doctor-form;id=' + id, '_blank');
    }
    
    showSalaryHistoryPopup(doctor: any) {
        const options: any = {
            title: 'Історія виплат',
            childComponent: DoctorSalaryHistoryModalComponent,
            data: doctor
        };
        this.modalService.openDialog(this.viewRef, options);
    }
    
    isValid() {
        return (this.from && this.to && this.to >= this.from);
    }

    load() {
        if (!this.isValid()) return;
        this.loading = true;
        this.data = [];
        this.sub = this.service.getSalarySummary(this.from, this.to).subscribe(
            data => {
                this.data = data;
                this.totalSummary = 0;
                this.totalRecd = 0;
                this.totalActual = 0;
                data.forEach( currentValue => {
                    currentValue.lastName = currentValue.name.split(' ')[0];
                    this.totalRecd += currentValue.recd;
                    this.totalSummary += currentValue.total;
                    this.totalActual += currentValue.actual;
                });
                this.loading = false;
            },
            error => {
                this.alertService.error('Помилка на сервері', false);
                this.loading = false;
            }
        );
    }
}
