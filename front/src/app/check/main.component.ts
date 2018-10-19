import { Component, OnInit, OnDestroy, Output } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { AppComponent } from '../app.component';
import { PatientService, AlertService } from '../_services/index';

@Component({
    templateUrl: './main.component.html'
})

export class CheckComponent implements OnInit, OnDestroy {

    sub: Subscription;
    data: any;
    loading = false;

    patientId: string;
    start: string;
    finish: string;

    date = new Date();

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
        this.sub = this.service.getCheck(this.patientId, this.start, this.finish)
            .subscribe(data => {
                this.data = data;
                this.setTimer();
            });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    setTimer() {
        setTimeout(() => { this.router.navigate(['home']) }, 3 * 60 * 1000);
    }
}
