import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService } from '../_services/index';

@Component({
    templateUrl: './patients-debetors.component.html'
})
export class PatientsDebetorsComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;
    sum = 0;

    constructor(private service: StatisticService) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    load() {
        this.sub = this.service.getPatientsDebetors().subscribe(data => {
            this.data = data;
            this.sum = 0;
            data.forEach( currentValue => {
                this.sum += currentValue.balance;
            });
        });
    }
}
