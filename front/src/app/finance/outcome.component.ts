import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { CashType } from '../_storage/index';
import { AlertService, OutcomeService } from '../_services/index';

@Component({
    templateUrl: './outcome.component.html'
})
export class FinanceOutcomeComponent implements OnInit, OnDestroy {

    loading = false;
    cashType = CashType;
    sub: Subscription;
    data: any;
    from: string;
    to: string;

    constructor(private alertService: AlertService, private service: OutcomeService) { }

    ngOnInit() {
        this.from = new Date().toISOString().split('T').shift();
        this.to = new Date().toISOString().split('T').shift();
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load() {
        this.data = [];
        this.sub = this.service.getOutcomeTree().subscribe(
            data => {
                this.data = data;
                this.loading = false;
            },
            error => {
                this.alertService.error('Помилка на сервері', false);
                this.loading = false;
            }
        );
    }
}
