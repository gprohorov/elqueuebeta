import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { PatientService } from '../_services/index';

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

    date = new Date();

    constructor(private service: PatientService, private route: ActivatedRoute) {
        sessionStorage.setItem('showMenu', 'false');
    }

    ngOnInit() {
        this.loading = true;
        this.start = new Date().toISOString().split('T').shift();
        this.finish = new Date().toISOString().split('T').shift();
        this.patientId = this.route.snapshot.paramMap.get('patientId');
        this.sub = this.service.getReceipt(this.patientId, this.start, this.finish)
            .subscribe(data => { this.data = data; this.loading = false; });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}
