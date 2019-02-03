import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService } from '../_services/index';

@Component({
    templateUrl: './doctors-procedures-from-to.component.html'
})
export class DoctorsProceduresFromToComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    sum = 0;
    start: string;
    finish: string;

    constructor(private service: StatisticService) {
        this.start = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
        this.finish = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
    }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    isValid() {
        return (this.start && this.finish && this.finish >= this.start);
    }

    load() {
        if (!this.isValid()) return;
        this.loading = true;
        this.sub = this.service.getDoctorsProceduresFromTo(this.start, this.finish)
            .subscribe(data => {
                this.data = data;
                this.sum = 0;
                data.forEach( currentValue => { this.sum += currentValue.fee; });
                this.loading = false;
            });
    }
}
