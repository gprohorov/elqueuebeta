import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { Patient, Procedure } from '../_models/index';
import { PatientsQueueService, AlertService, ProcedureService } from '../_services/index';
import { Status, Activity } from '../_storage';

@Component({
    templateUrl: './diagnose.component.html'
})
export class DoctorInterfaceDiagnoseComponent implements OnInit, OnDestroy {

    sub: Subscription;
    subProc: Subscription;
    item: any = {};
    procedures: Procedure[] = [];
    loading = false;
    procedureStarted = false;

    constructor(
        private service: PatientsQueueService,
        private procedureService: ProcedureService,
        private alertService: AlertService
    ) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subProc) this.subProc.unsubscribe();
    }

    load() {
        this.loading = true;
        this.subProc = this.procedureService.getAll().subscribe(data => {
            this.procedures = data;
        });
        this.sub = this.service.getDoctorPatient(2).subscribe(data => {
            this.item = data;
            this.loading = false;
            if (this.item && this.item.active === 'ON_PROCEDURE') this.procedureStarted = true;
        });
    }

    startProcedure() {
        this.sub = this.service.startProcedure(this.item.id, 2).subscribe(data => {
            this.alertService.success('Процедуру розпочато.');
            this.procedureStarted = true;
            this.load();
        });
    }

    cancelProcedure() {
        this.sub = this.service.cancelProcedure(this.item.id, 2).subscribe(data => {
            this.alertService.success('Процедуру скасовано.');
            this.procedureStarted = false;
            this.load();
        });
    }

    executeProcedure() {
        this.sub = this.service.executeProcedure(this.item.id, 2).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.procedureStarted = false;
            this.load();
        });
    }

}
