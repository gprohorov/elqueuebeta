﻿import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService } from '../_services/index';

@Component({
    templateUrl: './procedures-statistics.component.html'
})
export class ProceduresStatisticsComponent implements OnInit, OnDestroy {

    sub: Subscription;
    subTemp: Subscription;
    loading = false;
    data: any;
    start: string;
    finish: string;

    procedures: any = {};

    assigned = 0;
    executed = 0;
    cancelled = 0;
    expired = 0;
    zones = 0;
    sum = 0;

    constructor(private service: StatisticService) {
        this.start = new Date().toISOString().split('T').shift();
        this.finish = new Date().toISOString().split('T').shift();
    }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subTemp) this.subTemp.unsubscribe();
    }

    getInfo(procedureId: number) {
        this.sub = this.service.getProceduresStatisticsByDoctors(this.start, this.finish, procedureId)
            .subscribe(data => {
                this.procedures[procedureId] = data;
            });
    }

    load() {
        this.sub = this.service.getProceduresStatistics(this.start, this.finish).subscribe(data => {
            this.data = data;
            this.procedures = {};
            this.assigned = 0;
            this.executed = 0;
            this.cancelled = 0;
            this.expired = 0;
            this.zones = 0;
            this.sum = 0;
            data.forEach( currentValue => {
                this.assigned += currentValue.assigned;
                this.executed += currentValue.executed;
                this.cancelled += currentValue.cancelled;
                this.expired += currentValue.expired;
                this.zones += currentValue.zones;
                this.sum += currentValue.fee;
            });
        });
    }
}
