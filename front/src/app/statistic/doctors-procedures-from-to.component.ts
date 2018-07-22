import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService, AlertService } from '../_services/index';

@Component({
    templateUrl: './doctors-procedures-from-to.component.html'
})

export class DoctorsProceduresFromToComponent implements OnInit, OnDestroy {
    
    sub: Subscription;
    data: any;
    
    constructor(private service: StatisticService, private alertService: AlertService) { }
    
    ngOnInit() {
        this.sub = this.service.getDoctorsProceduresFromTo(new Date(), new Date()).subscribe(data => { this.data = data; });
    }
    
    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}
