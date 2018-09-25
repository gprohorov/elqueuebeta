import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService, AlertService } from '../_services/index';

@Component({
    templateUrl: './patients-debetors-ext.component.html'
})
export class PatientsDebetorsExtComponent implements OnInit, OnDestroy {
    
    sub: Subscription;
    loading = false;
    data: any;
    totalBill: number = 0;
    totalPayment: number = 0;
    totalDebt: number = 0;
    
    constructor(private service: StatisticService, private alertService: AlertService) { }
    
    ngOnInit() {
        this.load();
    }
    
    ngOnDestroy() {
        this.sub.unsubscribe();
    }
    
    load() {
        this.sub = this.service.getPatientsDebetorsExt().subscribe(data => { 
            this.data = data;
            this.totalBill = 0;
            this.totalPayment = 0;
            this.totalDebt = 0;
            data.forEach( currentValue => {
                this.totalBill += currentValue.bill;
                this.totalPayment += currentValue.payment;
                this.totalDebt += currentValue.debt;
            });
        });
    }
}
