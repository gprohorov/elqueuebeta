import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { Patient } from '../_models/index';
import { PatientsQueueService, AlertService } from '../_services/index';
import { Statuses, Activity } from '../_storage';

@Component({
    templateUrl: './massage.component.html'
})
export class DoctorInterfaceMassageComponent implements OnInit {

    sub: Subscription;
    item: any = {};
    loading = false;
    procedureStarted = false;

    constructor(private service: PatientsQueueService, private alertService: AlertService) {
    }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    getFullName(item: Patient) {
        return [item.person.lastName, item.person.firstName, item.person.patronymic].join(' ');
    }

    load() {
        this.loading = true;
        this.sub = this.service.getDoctorPatient(6).subscribe(data => {
            this.item = data;
            this.loading = false;
            if (this.item && this.item.active === 'ON_PROCEDURE') this.procedureStarted = true;
        });
    }

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

}
