import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService } from '../_services/index';

@Component({
    templateUrl: './patients-debetors-ext.component.html'
})
export class PatientsDebetorsExtComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    start: string;
    finish: string;
    totalBill = 0;
    totalPayment = 0;
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

    load() {
        this.loading = true;
        this.sub = this.service.getPatientsDebetorsExt(this.start, this.finish).subscribe(data => {
            this.data = data;
            this.totalBill = 0;
            this.totalPayment = 0;
            this.totalDebt = 0;
            data.forEach( currentValue => {
                this.totalBill += currentValue.bill;
                this.totalPayment += currentValue.payment;
                this.totalDebt += currentValue.debt;
            });
            this.loading = false;
        });
    }
}
