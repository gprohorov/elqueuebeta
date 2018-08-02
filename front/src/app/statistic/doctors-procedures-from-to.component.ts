﻿import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService, AlertService } from '../_services/index';

@Component({
    templateUrl: './doctors-procedures-from-to.component.html'
})

export class DoctorsProceduresFromToComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    sum: number = 0;
    start: string;
    finish: string;

    constructor(private service: StatisticService, private alertService: AlertService) {
        let start = new Date(), finish = new Date();
        start.setDate(-7);
        this.start = start.toISOString().split('T').shift();
        this.finish = finish.toISOString().split('T').shift();
    }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    load() {
        this.sub = this.service.getDoctorsProceduresFromTo(this.start, this.finish).subscribe(data => {
            this.data = data;
            this.sum = 0;
            data.forEach( currentValue => {
                this.sum += currentValue.fee;
            });
        });
    }
}
