import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { AlertService, FinanceService } from '../_services/index';

@Component({
    templateUrl: './salary-payroll.component.html'
})
export class FinanceSalaryPayrollComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    from: string;
    to: string;

    totalStavka = 0;
    totalAccural = 0;
    totalAward = 0;
    totalPenalty = 0;
    totalTotal = 0;
    totalRecd = 0;
    totalActual = 0;

    constructor(
        private alertService: AlertService,
        private viewRef: ViewContainerRef,
        private service: FinanceService) { }

    ngOnInit() {
        const date = new Date();
        this.from = (new Date(Date.now() - date.getTimezoneOffset() * 60000))
            .toISOString().slice(0, -14);
        this.to = (new Date(Date.now() - date.getTimezoneOffset() * 60000))
            .toISOString().slice(0, -14);
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }
    
    isValid() {
        return (this.from && this.to && this.to >= this.from);
    }
    
    load() {
        this.loading = true;
        this.data = [];
        this.sub = this.service.getSalaryPayroll(this.from, this.to).subscribe(
            data => {
                this.data = data;
                this.totalStavka = 0;
                this.totalAccural = 0;
                this.totalAward = 0;
                this.totalPenalty = 0;
                this.totalTotal = 0;
                this.totalRecd = 0;
                this.totalActual = 0;
                data.forEach( currentValue => {
                    this.totalStavka += currentValue.stavka;
                    this.totalAccural += currentValue.accural;
                    this.totalAward += currentValue.award;
                    this.totalPenalty += currentValue.penalty;
                    this.totalTotal += currentValue.total;
                    this.totalRecd += currentValue.recd;
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