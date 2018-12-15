import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService } from '../_services/index';

@Component({
    templateUrl: './patients-statistics.component.html'
})
export class PatientsStatisticsComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    start: string;
    finish: string;
    totalBill = 0;
    totalCash = 0;
    totalCard = 0;
    totalDiscount = 0;
    totalDonation = 0;
    totalDebt = 0;

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
        this.sub = this.service.getPatientsStatistics(this.start, this.finish).subscribe(data => {
            this.data = data;
            this.totalBill = 0;
            this.totalCash = 0;
            this.totalCard = 0;
            this.totalDiscount = 0;
            this.totalDonation = 0;
            this.totalDebt = 0;
            data.forEach( currentValue => {
                this.totalBill += currentValue.bill;
                this.totalCash += currentValue.cash;
                this.totalCard += currentValue.card;
                this.totalDiscount += currentValue.discount;
                this.totalDonation += currentValue.donation;
                this.totalDebt += currentValue.debt;
            });
            this.loading = false;
        });
    }
}
