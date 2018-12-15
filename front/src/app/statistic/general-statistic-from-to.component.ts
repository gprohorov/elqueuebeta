import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService } from '../_services/index';

@Component({
    templateUrl: './general-statistic-from-to.component.html'
})
export class GeneralStatisticFromToComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    start: string;
    finish: string;

    patients = 0;
    doctors = 0;
    cash = 0;
    card = 0;
    bill = 0;
    discount = 0;
    debt = 0;

    constructor(private service: StatisticService) {
        this.start = new Date().toISOString().split('T').shift();
        this.finish = new Date().toISOString().split('T').shift();
    }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    isValid() {
        return (this.start && this.finish && this.finish >= this.start);
    }
    
    load() {
        if (!this.isValid()) return;
        this.loading = true;
        this.sub = this.service.getGeneralStatisticsFromTo(this.start, this.finish).subscribe(data => {
            this.data = data;

            this.patients = 0;
            this.doctors = 0;
            this.cash = 0;
            this.card = 0;
            this.bill = 0;
            this.discount = 0;
            this.debt = 0;

            data.forEach(currentValue => {
                this.patients += currentValue.patients;
                this.doctors += currentValue.doctors;
                this.cash += currentValue.cash;
                this.card += currentValue.card;
                this.bill += currentValue.bill;
                this.discount += currentValue.discount;
                this.debt += currentValue.debt;
            });
            this.loading = false;
        });
    }
}
