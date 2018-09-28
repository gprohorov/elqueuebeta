import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService, AlertService } from '../_services/index';

@Component({
    templateUrl: './patients-statistics.component.html'
})

export class PatientsStatisticsComponent implements OnInit, OnDestroy {
    
    sub: Subscription;
    loading = false;
    data: any;
    start: string;
    finish: string;
    totalBill: number = 0;
    totalCash: number = 0;
    totalCard: number = 0;
    totalDiscount: number = 0;
    totalDonation: number = 0;
    totalDebt: number = 0;
    
    constructor(private service: StatisticService, private alertService: AlertService) {
        this.start = new Date().toISOString().split('T').shift();
        this.finish = new Date().toISOString().split('T').shift();
    }
    
    ngOnInit() {
        this.load();
    }
    
    ngOnDestroy() {
        this.sub.unsubscribe();
    }
    
    load() {
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
        });
    }
}
