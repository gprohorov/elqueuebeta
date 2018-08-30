import { Component, OnInit, OnDestroy, Output } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { AppComponent } from '../app.component';
import { PatientService, AlertService } from '../_services/index';

@Component({
    templateUrl: './main.component.html'
})

export class ReceiptComponent implements OnInit, OnDestroy {
    
    sub: Subscription;
    data: any;
    loading = false;
    
    patientId: string;
    start: string;
    finish: string;
    
    constructor(
        private service: PatientService,
        private alertService: AlertService,
        private router: Router,
        private route: ActivatedRoute
    ) { 
        sessionStorage.setItem('showMenu', 'false');
    }
    
    ngOnInit() {
        this.start = new Date().toISOString().split('T').shift();
        this.finish = new Date().toISOString().split('T').shift();
        this.patientId = this.route.snapshot.paramMap.get('patientId');
        this.sub = this.service.getReceipt(this.patientId, this.start, this.finish)
            .subscribe(data => { this.data = data; });
    }
    
    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}
