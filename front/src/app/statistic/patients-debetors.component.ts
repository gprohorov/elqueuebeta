import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService, AlertService } from '../_services/index';

@Component({
    templateUrl: './patients-debetors.component.html'
})

export class PatientsDebetorsComponent implements OnInit, OnDestroy {
    
    sub: Subscription;  
    data: any;
    
    constructor(private service: StatisticService, private alertService: AlertService) { }
    
    ngOnInit() {
        this.sub = this.service.getPatientsDebetors().subscribe(data => { this.data = data; });
    }
    
    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}
