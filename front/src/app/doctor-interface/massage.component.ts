import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { Patient } from '../_models/index';
import { AlertService, DoctorInterfaceService } from '../_services/index';
import { Status, Activity } from '../_storage';

@Component({
    templateUrl: './massage.component.html'
})
export class DoctorInterfaceMassageComponent implements OnInit, OnDestroy {
    
    loading = false;

    sub: Subscription;
    item: any = {};
    procedureStarted = false;

    constructor(
        private alertService: AlertService,
        private service: DoctorInterfaceService
    ) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load() {
        this.loading = true;
        this.sub = this.service.getPatient(6).subscribe(data => {
            this.item = data;
            this.loading = false;
            if (this.item && this.item.active === 'ON_PROCEDURE') this.procedureStarted = true;
        });
    }
/*
    startProcedure() {
        this.sub = this.service.startProcedure(this.item.id, 6).subscribe(data => {
            this.alertService.success('Процедуру розпочато.');
            this.procedureStarted = true;
            this.load();
        });
    }

    cancelProcedure() {
        this.sub = this.service.cancelProcedure(this.item.id, 6).subscribe(data => {
            this.alertService.success('Процедуру скасовано.');
            this.procedureStarted = false;
            this.load();
        });
    }

    executeProcedure() {
        this.sub = this.service.executeProcedure(this.item.id, 6).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.procedureStarted = false;
            this.load();
        });
    }
*/
}
