import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { PatientService} from '../_services/index';

@Component({
    templateUrl: './main.component.html'
})
export class CheckComponent implements OnInit, OnDestroy {

    sub: Subscription;
    timeOut: any;
    data: any;
    loading = false;

    patientId: string;
    date: string;

    constructor(
        private service: PatientService,
        private router: Router,
        private route: ActivatedRoute
    ) {
        sessionStorage.setItem('showMenu', 'false');
    }

    ngOnInit() {
        this.date = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
        this.patientId = this.route.snapshot.paramMap.get('patientId');
        this.loading = true;
        this.sub = this.service.getCheck(this.patientId).subscribe(data => {
            this.data = data;
            if (this.data.balance < 0) {
                this.data.discountSum = Math.ceil((this.data.balance / 100) * this.data.discount);
                this.data.totalSum = this.data.balance - this.data.discountSum;
            }
            this.setTimer();
            this.loading = false;
        });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.timeOut) clearTimeout(this.timeOut); 
    }

    setTimer() {
        if (this.timeOut) clearTimeout(this.timeOut);
        this.timeOut = setTimeout(() => { this.router.navigate(['home']); }, 3 * 60 * 1000);
    }
}
