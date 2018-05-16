﻿import { Component, ViewContainerRef, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Subject } from 'rxjs/Subject';
import { ModalDialogService } from 'ngx-modal-dialog';

import { Patient } from '../_models/index';
import { Status, Activity } from '../_storage/index';
import { PatientsQueueService, AlertService } from '../_services/index';

import { PatientAssignProcedureModalComponent } from '../patient/assign-procedure.modal.component';

@Component({
    templateUrl: './list.component.html'
})
export class PatientsQueueListComponent implements OnInit {

    sub: Subscription;
    subTemp: Subscription;
    items: any[] = [];
    loading = false;
    rows = [];
    Status = Status;
    Statuses = Object.keys(Status);
    Activity = Activity;
    Activities = Object.keys(Activity);

    constructor(
        private modalService: ModalDialogService,
        private viewRef: ViewContainerRef,
        private service: PatientsQueueService,
        private alertService: AlertService
    ) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
        if (this.subTemp) this.subTemp.unsubscribe();
    }

    delete(id: string, name: string) {
        if (confirm('Видалити "' + name + '" ?')) {
            this.service.delete(id).subscribe(() => { this.load(); });
        }
    }

    getFullName(item: Patient) {
        return [item.person.lastName, item.person.firstName, item.person.patronymic].join(' ');
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
            title: 'Пацієнт: ' + this.getFullName(patient),
            childComponent: PatientAssignProcedureModalComponent,
            data: { patientId: patient.id, patientName: this.getFullName(patient) }
        });
        this.alertService.subject.subscribe(() => { this.load() });
    }

    updateActivity(id: string, value: string) {
        if (value === 'CANCELED') {
          if (confirm('Встановити процедурі "' + Activity[value].text + '" ?')) {
            this.subTemp = this.service.updateActivity(id, value).subscribe(data => {
              this.load();
            });
          }
        } else {
          this.subTemp = this.service.updateActivity(id, value).subscribe(data => {
            this.load();
          });
        }

    }

    updateActivityAll(id: string, value: string) {
        if (confirm('Встановити всім процедурам "' + Activity[value].text + '" ?')) {
          this.subTemp = this.service.updateActivityAll(id, value).subscribe(data => {
            this.load();
          });
        }
    }

    updateStatus(id: string, value: string) {
        this.subTemp = this.service.updateStatus(id, value).subscribe(data => { });
    }

    updateBalance(id: string, value: string) {
        this.subTemp = this.service.updateBalance(id, value).subscribe(data => { });
    }

    getTimeDiffClass(v: number) {
        return 'badge badge-pill badge-' + (v > 60 ? 'danger' : v > 30 ? 'success' : 'primary');
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
