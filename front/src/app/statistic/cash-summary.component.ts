import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService, AlertService } from '../_services/index';

@Component({
    templateUrl: './cash-summary.component.html'
})

export class CashSummaryComponent implements OnInit, OnDestroy {
    
    sub: Subscription;
    data: any;
    loading = false;
    
    constructor(private service: StatisticService, private alertService: AlertService) { }
    
    ngOnInit() {
        this.sub = this.service.getCashSummary().subscribe(data => { this.data = data; });
    }
    
    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}
