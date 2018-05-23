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
    sub: Subscription;
    subProcedure: Subscription;
    subPatient: Subscription;

    item: any;
    procedureId: number;
    procedureStarted = false;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: DoctorInterfaceService
    ) { }

    ngOnInit() {
        this.sub = this.route.params.subscribe(params => {
            if (+params.id > 0) {
                this.procedureId = +params.id;
                this.load();
            }
        });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subProcedure) this.subProcedure.unsubscribe();
        if (this.subPatient) this.subPatient.unsubscribe();
    }

    load() {
        this.loading = true;
        this.subPatient = this.service.getPatient(this.procedureId).subscribe(data => {
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
        this.subProcedure = this.service.setReady(this.procedureId).subscribe(data => {
            this.load();
        });
    }

    startProcedure() {
        this.subProcedure = this.service.startProcedure(this.item.id, this.procedureId).subscribe(data => {
            this.alertService.success('Процедуру розпочато.');
            this.load();
        });
    }

    cancelProcedure(data) {
        if (confirm('Скасувати процедуру ?')) {
            this.subProcedure = this.service.cancelProcedure(this.item.id, data).subscribe(data => {
                this.alertService.success('Процедуру скасовано.');
                this.procedureStarted = false;
                this.load();
            });
        }
    }

    executeProcedure(data) {
        this.subProcedure = this.service.executeProcedure(this.item.id, data).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.procedureStarted = false;
            this.load();
        });
    }

}
