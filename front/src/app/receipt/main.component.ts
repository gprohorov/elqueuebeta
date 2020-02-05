import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { config } from '../../config';

import { PatientService } from '../_services/index';

@Component({
    templateUrl: './main.component.html'
})
export class ReceiptComponent implements OnInit, OnDestroy {

    sub: Subscription;
    data: any;
    loading = false;
    address_info: any;

    patientId: string;
    start: string;
    finish: string;

    date = new Date();

    constructor(private service: PatientService, private route: ActivatedRoute) {
        sessionStorage.setItem('showMenu', 'false');
        this.address_info = config.address_info;
    }

    ngOnInit() {
        this.loading = true;
        this.start = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
        this.finish = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
        this.patientId = this.route.snapshot.paramMap.get('patientId');
        this.sub = this.service.getReceipt(this.patientId, this.start, this.finish)
            .subscribe(data => { this.data = data; this.loading = false; });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}
