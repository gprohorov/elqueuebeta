import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { switchMap } from 'rxjs/operators';

import { Patient } from '../_models/index';
import { AlertService, WorkplaceCommonService } from '../_services/index';

@Component({
    templateUrl: './common.component.html'
})
export class WorkplaceCommonComponent implements OnInit, OnDestroy {

    loading = false;
    sub: Subscription;
    subPatient: Subscription;
    subProcedure: Subscription;

    item: any;
    talonId: string;
    zones: number = 1;
    patientId: string;
    procedureId: number;
    procedureStarted: boolean = false;
    model: any = {comment: ''};

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: WorkplaceCommonService
    ) { }

    ngOnInit() {
        this.patientId = this.route.snapshot.paramMap.get('patientId');
        this.procedureId = +this.route.snapshot.paramMap.get('procedureId');
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subPatient) this.subPatient.unsubscribe();
        if (this.subProcedure) this.subProcedure.unsubscribe();
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
        this.subProcedure = this.service.executeProcedure(this.item.talon.id, this.zones).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.router.navigate(['workplace']);
        });
    }

    comment() {
        this.loading = true;
        this.service.commentProcedure(this.item.talon.id, this.model.comment).subscribe(
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
