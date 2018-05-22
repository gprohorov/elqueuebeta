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
    procedureId: number;
    procedureStarted = false;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: DoctorInterfaceService
    ) { }

    ngOnInit() {
        this.route.params.subscribe(params => {
            if (+params.id > 0) {
                this.procedureId = +params.id;
                this.load();
            }
        });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load() {
        this.loading = true;
        this.sub = this.service.getPatient(this.procedureId).subscribe(data => {
            this.item = data;
            this.loading = false;
            if (this.item && this.item.activity === 'ON_PROCEDURE') {
                this.procedureStarted = true;
            } else {
                this.procedureStarted = false;
            }
        });
    }

    setReady() {
        this.service.setReady(this.procedureId).subscribe(data => {
            this.load();
        });
    }

    startProcedure() {
        this.sub = this.service.startProcedure(this.item.id, this.procedureId)
            .subscribe(data => {
                this.alertService.success('Процедуру розпочато.');
                this.load();
            });
    }

    cancelProcedure() {
        this.sub = this.service.cancelProcedure(this.item.id).subscribe(data => {
            this.alertService.success('Процедуру скасовано.');
            this.procedureStarted = false;
            this.load();
        });
    }

    executeProcedure() {
        this.sub = this.service.executeProcedure(this.item.id).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.procedureStarted = false;
            this.load();
        });
    }

}
