import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { switchMap } from 'rxjs/operators';

import { Patient } from '../_models/index';
import { AlertService, DoctorInterfaceService } from '../_services/index';

@Component({
    templateUrl: './procedure.component.html'
})
export class DoctorInterfaceProcedureComponent implements OnInit, OnDestroy {

    loading = false;
    sub: Subscription;
    subPatient: Subscription;
    subProcedure: Subscription;

    item: any;
    procedureId: number;
    patientId: string;
    procedureName: string;
    model: any = {comment: ''};

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: DoctorInterfaceService
    ) { }

    ngOnInit() {
        const procedureId = this.route.snapshot.paramMap.get('procedureId');
        const patientId = this.route.snapshot.paramMap.get('patientId');
        if (+procedureId > 0 && !!patientId) {
            this.procedureId = +procedureId;
            this.patientId = patientId;
            this.load();
        }
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subPatient) this.subPatient.unsubscribe();
        if (this.subProcedure) this.subProcedure.unsubscribe();
    }

    load() {
        this.loading = true;
        this.subPatient = this.service.getPatient(this.procedureId, this.patientId).subscribe(data => {
            this.item = data;
            this.loading = false;
        });
    }

    cancelProcedure(data) {
        if (confirm('Скасувати процедуру ?')) {
            this.subProcedure = this.service.cancelProcedure(this.item.id, data).subscribe(data => {
                this.alertService.success('Процедуру скасовано.');
                this.router.navigate(['/doctor-interface']);
            });
        }
    }

    executeProcedure(data) {
        this.subProcedure = this.service.executeProcedure(this.item.id, data).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.router.navigate(['/doctor-interface']);
        });
    }

    submit() {
        this.loading = true;
        this.service.updateProcedure(this.item.talons[0].id, this.model.comment).subscribe(
            data => {
                this.alertService.success('Зміни збережено.', true);
                this.model.comment = '';
                this.load();
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}
