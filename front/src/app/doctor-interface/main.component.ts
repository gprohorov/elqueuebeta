import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { switchMap } from 'rxjs/operators';

import { Patient } from '../_models/index';
import { AlertService, DoctorInterfaceService } from '../_services/index';

@Component({
    templateUrl: './main.component.html'
})
export class DoctorInterfaceMainComponent implements OnInit, OnDestroy {

    loading = false;
    item: any;
    sub: Subscription;
    procedureStarted = false;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: DoctorInterfaceService
    ) { }

    ngOnInit() {
        this.route.params.subscribe(params => { 
            if (+params.id > 0) this.load(+params.id);
        });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load(id: number) {
        this.loading = true;
        this.sub = this.service.getPatient(id).subscribe(data => {
            this.item = data;
            this.loading = false;
            if (this.item && this.item.active === 'ON_PROCEDURE') this.procedureStarted = true;
        });
    }
}
