import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Subscription } from 'rxjs/Subscription';
import { Subject } from 'rxjs/Subject';
import { ModalDialogService } from 'ngx-modal-dialog';
import * as moment from 'moment';

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
    date: Date = new Date(); 
    filters: any = 'all'; // possible values: 'all', 'active' 
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
            data: patient
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
    
    updateOutOfTurn(id: string, value: boolean) {
        this.subTemp = this.service.updateOutOfTurn(id, value).subscribe(data => {
            this.load();
        });
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

    getTalonInfo(talon: any) {
        let out = '', start, end, diff;
        if (talon.start) {
            start = moment(talon.start, 'YYYY-MM-DDTHH:mm:ss.SSS');
            out += start.format('HH:mm');
        }
        if (talon.start && talon.executionTime) {
            end = moment(talon.executionTime, 'YYYY-MM-DDTHH:mm:ss.SSS');
            diff = Math.floor(moment.duration(end.diff(start)).asMinutes());
            out += ' - ' + end.format('HH:mm') + ' (' + diff + ' хв.)';
        } else if (talon.start) {
            end = moment({});
            diff = Math.floor(moment.duration(end.diff(start)).asMinutes());
            out += ' (' + diff + ' хв.)';
        }
        if (talon.doctor) {
            out += ', ' + talon.doctor.fullName + ' (зон: ' + talon.zones + ')';
        }
        
        return out;
    }
    
    load(search: any = null) {
        this.loading = true;
        this.sub = this.service.getAll().subscribe(data => {
            this.items = data.sort(function (a, b) {
                // Sort by startActivity
                // const x = a.startActivity, y = b.startActivity;
                
                // Sort by name
                const x = a.person.fullName, y = b.person.fullName;
                
                if (x < y) { return -1; }
                if (x > y) { return 1; }
                return 0;
            });
            this.loading = false;
        });
    }

}
