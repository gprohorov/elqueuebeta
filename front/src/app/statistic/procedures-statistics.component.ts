import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService, AlertService } from '../_services/index';

@Component({
    templateUrl: './procedures-statistics.component.html'
})

export class ProceduresStatisticsComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    start: Date = new Date();
    finish: Date = new Date();
    
    assigned: number = 0;
    executed: number = 0;
    cancelled: number = 0;
    expired: number = 0;
    zones: number = 0;
    sum: number = 0;

    constructor(private service: StatisticService, private alertService: AlertService) {
        this.start.setDate(1);
    }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    load() {
        this.sub = this.service.getProceduresStatistics(this.start, this.finish).subscribe(data => {
            this.data = data;
            this.assigned = 0;
            this.executed = 0;
            this.cancelled = 0;
            this.expired = 0;
            this.zones = 0;
            this.sum = 0;
            data.reduce( (accumulator, currentValue) => {
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
