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
    sum: number = 0;
    
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
            this.sum = 0;
            data.forEach( currentValue => {
                this.sum += currentValue.balance;
            });
        });
    }
}
