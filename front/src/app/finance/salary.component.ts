import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { FinanceService } from '../_services/index';

@Component({
    templateUrl: './salary.component.html'
})
export class FinanceSalaryComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;

    totalSummary = 0;

    constructor(private service: FinanceService) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load() {
        // this.loading = true;
        this.sub = this.service.getSalary().subscribe(data => {
            this.data = data;
            this.totalSummary = 0;
            data.forEach( currentValue => {
                currentValue.name = currentValue.name.split(' ')[0];
                this.totalSummary += currentValue.total;
            });
            this.loading = false;
        });
    }
}
