import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { StatisticService, AlertService } from '../_services/index';

@Component({
    templateUrl: './patient-statistics.component.html'
})

export class PatientStatisticsComponent implements OnInit, OnDestroy {
    
    sub: Subscription;
    loading = false;
    patientId: string;
    data: any;
    
    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private service: StatisticService, 
        private alertService: AlertService
    ) { }
    
    ngOnInit() {
        this.patientId = this.route.snapshot.paramMap.get('patientId');
        this.load();
    }
    
    ngOnDestroy() {
        this.sub.unsubscribe();
    }
    
    load() {
        this.sub = this.service.getPatientStatistics(this.patientId).subscribe(data => { 
            this.data = data;
        });
    }
}
