import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { AuthService, AlertService, FinanceService } from '../_services/index';

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
        public authService: AuthService,
        private alertService: AlertService,
        private modalService: ModalDialogService,
        private viewRef: ViewContainerRef,
        private service: FinanceService) { }

    ngOnInit() {
        this.from = '2018-10-29';
        this.to = new Date().toISOString().split('T').shift();
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    showSalaryHistoryPopup(doctor: any) {
        const options: any = {
            title: 'Історія виплат',
            childComponent: DoctorSalaryHistoryModalComponent,
            data: doctor
        };
        this.modalService.openDialog(this.viewRef, options);
    }
    
    changeWeek(weeks: number) {
        this.week += weeks; 
        this.load();
    }
    
    load() {
        this.loading = true;
        this.data = [];
        this.sub = this.service.getSalarySummary(this.from, this.to).subscribe(
            data => {
                this.data = data;
                this.totalSummary = 0;
                this.totalRecd = 0;
                this.totalActual = 0;
                if (this.week === null) this.week = this.data[0].week;
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
                this.data = [];
            }
        );
    }
}
