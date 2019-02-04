import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { AlertService, FinanceService } from '../_services/index';

@Component({
    templateUrl: './salary-on-day.component.html'
})
export class FinanceSalaryOnDayComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    date: any;

    totalStavka = 0;
    totalBonuses = 0;
    totalTotal = 0;

    constructor(
        private alertService: AlertService,
        private viewRef: ViewContainerRef,
        private service: FinanceService) { }

    ngOnInit() {
        const date = new Date();
        this.date = (new Date(Date.now() - date.getTimezoneOffset() * 60000))
            .toISOString().slice(0, -14);
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    changeDay(days: number) {
        const date = new Date(this.date);
        this.date = (new Date(date.setDate(date.getDate() + days))).toISOString().slice(0, -14);
        this.load();
    }
    
    load() {
        this.data = [];
        this.sub = this.service.getSalaryOnDay(this.date).subscribe(
            data => {
                this.data = data;
                this.totalStavka = 0;
                this.totalBonuses = 0;
                this.totalTotal = 0;
                data.forEach( currentValue => {
                    this.totalStavka += currentValue.stavka;
                    this.totalBonuses += currentValue.bonuses;
                    this.totalTotal += currentValue.total;
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