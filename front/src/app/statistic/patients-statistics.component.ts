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
    totalBalance = 0;
    totalBill = 0;
    totalCash = 0;
    totalCard = 0;
    totalDiscount = 0;
    totalDonation = 0;
    totalDebt = 0;
    
    totalPatients = 0;
    totalDiscounters = 0;
    totalDebitors = 0;
    
    filters: any = 'all'; // possible values: 'all', 'discount', 'debit'

    constructor(private service: StatisticService) {
        this.start = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
        this.finish = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
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

    isHiddenRow(item: any) {
        return (item.discount === 0 && this.filters === 'discount')
            || (item.debt === 0 && this.filters === 'debit');
    }
    
    load() {
        if (!this.isValid()) return;
        this.loading = true;
        this.sub = this.service.getPatientsStatistics(this.start, this.finish).subscribe(data => {
            this.data = data;
            
            this.totalPatients = data.length;
            this.totalDiscounters = data.filter( x => x.discount ).length;
            this.totalDebitors = data.filter( x => x.debt ).length;
            
            this.totalBalance = 0;
            this.totalBill = 0;
            this.totalCash = 0;
            this.totalCard = 0;
            this.totalDiscount = 0;
            this.totalDonation = 0;
            this.totalDebt = 0;
            data.forEach( currentValue => {
                this.totalBalance += currentValue.balance;
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
