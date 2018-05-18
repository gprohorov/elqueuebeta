import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { Patient } from '../_models/index';
import { AlertService, PatientService, PatientSearchCriteria } from '../_services/index';

import { PatientAssignProcedureModalComponent } from './assign-procedure.modal.component';

@Component({
    templateUrl: './list.component.html'
})
export class PatientListComponent implements OnInit, OnDestroy {

    sub: Subscription;
    items: Patient[];
    loading = false;
    rows = [];

    constructor(
        private modalService: ModalDialogService,
        private viewRef: ViewContainerRef,
        private patientService: PatientService,
        private alertService: AlertService
    ) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    onSorted($event: PatientSearchCriteria) {
        this.items = this.patientService.sortBy($event, this.items);
    }

    delete(id: string, name: string) {
        if (confirm('Видалити "' + name + '" ?')) {
            this.patientService.delete(id).subscribe(() => { this.load(); });
        }
    }

    showAssignProcedurePopup(patientId: string) {
        const patient = this.items.filter(x => patientId == x.id)[0];
        this.modalService.openDialog(this.viewRef, {
            title: 'Пацієнт: ' + patient.person.fullName,
            childComponent: PatientAssignProcedureModalComponent,
            data: { patientId: patientId, patientName: patient.person.fullName }
        });
    }

    load(search: string = '') {
        this.loading = true;
        this.sub = this.patientService.getAll(search).subscribe(data => {
            this.items = this.patientService.sortBy({ sortColumn: 'fullName', sortDirection: 'asc' }, data);
            this.loading = false;
        });
    }

}
