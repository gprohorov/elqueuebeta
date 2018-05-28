import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Subject } from 'rxjs/Subject';
import { ModalDialogService } from 'ngx-modal-dialog';

import { Patient } from '../_models/index';
import { Status, Activity } from '../_storage/index';
import { AlertService, PatientsQueueService } from '../_services/index';

import { PatientIncomeModalComponent } from '../patient/income.modal.component';
import { PatientAssignProcedureModalComponent } from '../patient/assign-procedure.modal.component';

@Component({
    templateUrl: './list.component.html'
})
export class PatientsQueueListComponent implements OnInit, OnDestroy {

    loading = false;
    
    sub: Subscription;
    subTemp: Subscription;
    items: Patient[] = [];
    rows = [];
    Status = Status;
    Statuses = Object.keys(Status);
    Activity = Activity;
    Activities = Object.keys(Activity);

    constructor(
        private viewRef: ViewContainerRef,
        private alertService: AlertService,
        private modalService: ModalDialogService,
        private service: PatientsQueueService
    ) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subTemp) this.subTemp.unsubscribe();
    }

    delete(id: string, name: string) {
        if (confirm('Видалити "' + name + '" ?')) {
            this.service.delete(id).subscribe(() => { this.load(); });
        }
    }

    getProgress(list: any[]) {
        let executed = 0, total = 0;
        list.forEach(function (item) {
            if (item.activity == 'EXECUTED') executed++;
            if (item.activity != 'CANCELED') total++;
        });
        return executed + '/' + total;
    }

    showAssignProcedurePopup(patient: any) {
        this.modalService.openDialog(this.viewRef, {
            title: 'Пацієнт: ' + patient.person.fullName,
            childComponent: PatientAssignProcedureModalComponent,
            data: { patientId: patient.id, patientName: patient.person.fullName }
        });
        this.alertService.subject.subscribe(() => { this.load() });
    }

    showIncomePopup(patient: any) {
        this.modalService.openDialog(this.viewRef, {
            title: 'Пацієнт: ' + patient.person.fullName,
            childComponent: PatientIncomeModalComponent,
            data: { patientId: patient.id, patientName: patient.person.fullName, sum: patient.balance * -1 }
        });
        this.alertService.subject.subscribe(() => { this.load() });
    }
    
    updateActivity(id: string, value: string) {
        if (value === 'CANCELED' && !confirm('Встановити процедурі "' + Activity[value].text + '" ?')) return false;
        this.subTemp = this.service.updateActivity(id, value).subscribe(data => {
            this.load();
        });
    }

    updateActivityAll(id: string, value: string) {
        if (confirm('Встановити всім процедурам "' + Activity[value].text + '" ?')) {
            this.subTemp = this.service.updateActivityAll(id, value).subscribe(data => {
                this.load();
            });
        }
    }

    updateStatus(id: string, value: string, event: any) {
        if (confirm('Встановити статус "' + Status[value].text + '" ?')) {
            this.subTemp = this.service.updateStatus(id, value).subscribe(data => {
                this.load();
            });
        } else {
            this.load();
        }
    }

    updateBalance(id: string, value: string) {
        this.subTemp = this.service.updateBalance(id, value).subscribe(data => { });
    }

    getTimeDiffClass(v: number) {
        return 'text-' + (v > 60 ? 'danger' : v > 30 ? 'success' : 'primary');
    }

    load(search: any = null) {
        this.loading = true;
        this.sub = this.service.getAll().subscribe(data => {
            this.items = data.sort(function (a, b) {
                const x = a.startActivity, y = b.startActivity;
                if (x < y) { return -1; }
                if (x > y) { return 1; }
                return 0;
            });
            this.loading = false;
        });
    }

}
