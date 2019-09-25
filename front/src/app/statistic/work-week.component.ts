import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService } from '../_services/index';

@Component({
    templateUrl: './work-week.component.html'
})
export class WorkWeekComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    year: number;
    finish: string;

    patients = 0;
    cash = 0;
    card = 0;
    bill = 0;
    discount = 0;
    outcome = 0;

    constructor(private service: StatisticService) {
        this.year = 2019;
    }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    change(years: number) {
        this.year = this.year + years;
        this.load();
    }
    
    load() {
        this.loading = true;
        this.sub = this.service.showAllForYear(this.year).subscribe(data => {
            this.data = data;

            this.patients = 0;
            this.cash = 0;
            this.card = 0;
            this.bill = 0;
            this.discount = 0;
            this.outcome = 0;

            data.forEach(currentValue => {
                this.patients += currentValue.patients;
                this.cash += currentValue.cash;
                this.card += currentValue.card;
                this.bill += currentValue.bill;
                this.discount += currentValue.discount;
                this.outcome += currentValue.outcome;
            });
            this.loading = false;
        });
    }
}
