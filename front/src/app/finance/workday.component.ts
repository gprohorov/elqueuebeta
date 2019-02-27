import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { AlertService, FinanceService } from '../_services/index';

@Component({
    templateUrl: './workday.component.html'
})
export class FinanceWorkdayComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    date: any;

    constructor(
        private alertService: AlertService,
        private viewRef: ViewContainerRef,
        private service: FinanceService) { }

    ngOnInit() {
        const date = new Date();
        this.date = (new Date(Date.now() - date.getTimezoneOffset() * 60000))
            .toISOString().slice(0, -14);
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    changeDay(days: number) {
        const date = new Date(this.date);
        this.date = (new Date(date.setDate(date.getDate() + days))).toISOString().slice(0, -14);
        this.load();
    }
    
    load() {
        this.loading = true;
        this.data = [];
        this.sub = this.service.getWorkday(this.date).subscribe(
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