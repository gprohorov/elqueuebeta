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
    talonId: string;
    zones: number = 1;
    patientId: string;
    procedureStarted: boolean = false;
    model: any = {comment: ''};

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: DoctorInterfaceService
    ) { }

    ngOnInit() {
        this.talonId = this.route.snapshot.paramMap.get('talonId');
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subPatient) this.subPatient.unsubscribe();
        if (this.subProcedure) this.subProcedure.unsubscribe();
    }

    load() {
        this.loading = true;
        this.subPatient = this.service.getPatient(this.talonId).subscribe(data => {
            this.item = data;
            this.loading = false;
            this.procedureStarted = ('ON_PROCEDURE' == this.item.talons[0].activity) 
        });
    }

    startProcedure() {
        this.subProcedure = this.service.startProcedure(this.talonId).subscribe(data => {
            this.alertService.success('Процедуру розпочато.');
            this.load();
        });
    }
    
    cancelProcedure() {
        if (confirm('Скасувати процедуру ?')) {
            this.subProcedure = this.service.cancelProcedure(this.talonId).subscribe(data => {
                this.alertService.success('Процедуру скасовано.');
                this.router.navigate(['/doctor-interface']);
            });
        }
    }

    executeProcedure() {
        this.subProcedure = this.service.executeProcedure(this.talonId, this.zones).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.router.navigate(['/doctor-interface']);
        });
    }

    comment() {
        this.loading = true;
        this.service.commentProcedure(this.talonId, this.model.comment).subscribe(
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
