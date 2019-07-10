import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService } from '../_services/index';

@Component({
    templateUrl: './cash-summary.component.html'
})
export class CashSummaryComponent implements OnInit, OnDestroy {

    sub: Subscription;
    data: any;
    loading = false;

    constructor(private service: StatisticService) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
    
    load() {
        this.loading = true;
        this.sub = this.service.getCashSummary().subscribe(data => {
            this.data = data;
            this.loading = false;
        });
    }
}
