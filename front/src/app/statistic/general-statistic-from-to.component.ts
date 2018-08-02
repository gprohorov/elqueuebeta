import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService, AlertService } from '../_services/index';

@Component({
    templateUrl: './general-statistic-from-to.component.html'
})

export class GeneralStatisticFromToComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    start: Date = new Date();
    finish: Date = new Date();
    
    patients: number = 0;
    doctors: number = 0;
    cash: number = 0;
    card: number = 0;
    bill: number = 0;
    discount: number = 0;
    debt: number = 0;

    constructor(private service: StatisticService, private alertService: AlertService) {
        this.start.setDate(this.start.getDate()-7);
    }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    load() {
        this.sub = this.service.getGeneralStatisticsFromTo(this.start, this.finish).subscribe(data => {
            this.data = data;
            
            this.patients = 0;
            this.doctors = 0;
            this.cash = 0;
            this.card = 0;
            this.bill = 0;
            this.discount = 0;
            this.debt = 0;
            
            data.forEach( currentValue => {
                this.patients += currentValue.patients;
                this.doctors += currentValue.doctors;
                this.cash += currentValue.cash;
                this.card += currentValue.card;
                this.bill += currentValue.bill;
                this.discount += currentValue.discount;
                this.debt += currentValue.debt;
            });
        });
    }
}
