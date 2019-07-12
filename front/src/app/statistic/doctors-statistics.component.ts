import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService } from '../_services/index';

@Component({
    templateUrl: './doctors-statistics.component.html'
})
export class DoctorsStatisticsComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    date: string;
    data: any;

    constructor(private route: ActivatedRoute, private service: StatisticService) { }

    ngOnInit() {
        this.date = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000))
            .toISOString().slice(0, -14);
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    getCount(map: any) {
        if (!map || !map.length) return '';
        return map.reduce(function(acc, curr) { return acc + curr.count; }, 0);
    }

    load() {
        this.loading = true;
        this.sub = this.service.getDoctorsCurrentStatistics(this.date).subscribe(data => {
            this.data = data;
            this.loading = false;
        });
    }
}
