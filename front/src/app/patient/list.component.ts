import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { Patient } from '../_models/index';
import { PatientService, PatientsQueueService, PatientSearchCriteria } from '../_services/index';

import { PatientIncomeModalComponent } from './income.modal.component';
import { PatientAssignHotelModalComponent } from './assign-hotel.modal.component';
import { PatientAssignProcedureModalComponent } from './assign-procedure.modal.component';

@Component({
    templateUrl: './list.component.html'
})
export class PatientListComponent implements OnInit, OnDestroy {

    sub: Subscription;
    subToday: Subscription;
    subDelete: Subscription;
    date: string = (new Date()).toISOString().split('T')[0];
    items: Patient[];
    todayItems: any[] = [];
    loading = false;
    rows = [];

    constructor(
        private modalService: ModalDialogService,
        private viewRef: ViewContainerRef,
        private patientService: PatientService,
        private patientsQueueService: PatientsQueueService
        ) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subToday) this.subToday.unsubscribe();
        if (this.subDelete) this.subDelete.unsubscribe();
    }

    onSorted($event: PatientSearchCriteria) {
        this.items = this.patientService.sortBy($event, this.items);
    }

    showAssignProcedurePopup(patientId: string) {
        let patient = this.items.filter(x => ('' + patientId === '' + x.id))[0];
        if (!patient) patient = this.todayItems.filter(x => ('' + patientId === '' + x.id))[0];
        this.modalService.openDialog(this.viewRef, {
            title: 'Пацієнт: ' + patient.person.fullName,
            childComponent: PatientAssignProcedureModalComponent,
            data: { patientId: patientId, patientName: patient.person.fullName }
        });
    }

    showAssignHotelPopup(patientId: string) {
        let patient = this.items.filter(x => ('' + patientId === '' + x.id))[0];
        if (!patient) patient = this.todayItems.filter(x => ('' + patientId === '' + x.id))[0];
        const options: any = {
            title: 'Пацієнт: ' + patient.person.fullName,
            childComponent: PatientAssignHotelModalComponent,
            data: { patientId: patientId, patientName: patient.person.fullName }
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(); });
    }

    showIncomePopup(patient: any) {
        this.modalService.openDialog(this.viewRef, {
            title: 'Пацієнт: ' + patient.person.fullName,
            childComponent: PatientIncomeModalComponent,
            data: patient
        });
    }

    delete(id: number, name: string) {
        if (confirm('Видалити "' + name + '" ?')) {
            this.subDelete = this.patientService.delete(id).subscribe(() => { this.load(); });
        }
    }

    load(search: string = '') {
        this.loading = true;
        this.subToday = this.patientsQueueService.getAll(this.date).subscribe(data => {
            this.todayItems = data.filter(x => {
                return x.person.fullName.toLowerCase().indexOf(search) > -1;
            }).sort((a, b) => {
                const x = a.person.fullName;
                const y = b.person.fullName;
                if (x < y) return -1;
                if (x > y) return 1;
                return 0;
            });
            this.loading = false;
        });
        this.sub = this.patientService.getAll(search).subscribe(data => {
            this.items = data;
            this.loading = false;
        });
    }
}
