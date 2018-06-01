﻿import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { switchMap } from 'rxjs/operators';

import { Patient, Procedure } from '../_models/index';
import { AlertService, ProcedureService, WorkplaceDiagnosticService } from '../_services/index';

@Component({
    templateUrl: './diagnostic.component.html'
})
export class WorkplaceDiagnosticComponent implements OnInit, OnDestroy {

    loading = false;
    sub: Subscription;
    subPatient: Subscription;
    subProcedure: Subscription;
    subProcedures: Subscription;
    procedures: Procedure[];

    item: any;
    patientId: string;
    procedureId: number;
    procedureStarted: boolean = false;
    model: any = {
        codeDiag: 'AF 358',
        diag: 'Діагноз пацієнта...',
        notes: 'Нотатки про пацієнта...'
    };

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private procedureService: ProcedureService,
        private service: WorkplaceDiagnosticService
    ) { }

    ngOnInit() {
        this.patientId = this.route.snapshot.paramMap.get('patientId');
        this.procedureId = +this.route.snapshot.paramMap.get('procedureId');
        this.sub = this.procedureService.getAll().subscribe(data => {
            this.procedures = data;
        });
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subPatient) this.subPatient.unsubscribe();
        if (this.subProcedure) this.subProcedure.unsubscribe();
        if (this.subProcedures) this.subProcedures.unsubscribe();
    }

    load() {
        this.loading = true;
        this.subPatient = this.service.getPatient(this.patientId, this.procedureId).subscribe(data => {
            this.item = data;
            this.loading = false;
            this.procedureStarted = ('ON_PROCEDURE' == this.item.talon.activity)
        });
    }

    startProcedure() {
        this.subProcedure = this.service.startProcedure(this.item.talon.id).subscribe(data => {
            this.alertService.success('Процедуру розпочато.');
            this.load();
        });
    }

    cancelProcedure() {
        if (confirm('Скасувати процедуру ?')) {
            this.subProcedure = this.service.cancelProcedure(this.item.talon.id).subscribe(data => {
                this.alertService.success('Процедуру скасовано.');
                this.router.navigate(['workplace']);
            });
        }
    }

    executeProcedure() {
        this.subProcedure = this.service.executeProcedure(this.item.talon.id).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.router.navigate(['workplace']);
        });
    }
}
