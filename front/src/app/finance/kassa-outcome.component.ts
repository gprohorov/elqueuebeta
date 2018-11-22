import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { CashType } from '../_storage/index';
import { AlertService, FinanceService } from '../_services/index';

@Component({
    templateUrl: './kassa-outcome.component.html'
})
export class FinanceKassaOutcomeComponent implements OnInit, OnDestroy {

    loading = false;
    cashType = CashType;
    sub: Subscription;
    data: any;

    constructor(private alertService: AlertService, private service: FinanceService) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load() {
        this.data = [];
        this.sub = this.service.getKassaOutcome().subscribe(
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
