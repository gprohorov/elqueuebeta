import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { AlertService, FinanceService } from '../_services/index';

@Component({
    templateUrl: './salary-summary-on-day.component.html'
})
export class FinanceSalarySummaryOnDayComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    from: string;
    to: string;

    totalStavka = 0;
    totalBonuses = 0;
    totalTotal = 0;

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
        this.data = [];
        this.sub = this.service.getSalarySummaryOnDay(this.from, this.to).subscribe(
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