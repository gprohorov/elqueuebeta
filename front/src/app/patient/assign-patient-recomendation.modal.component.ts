import { Component, ComponentRef, ViewChild, OnDestroy } from '@angular/core';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';
import { Subscription } from 'rxjs/Subscription';

import { ProcedureService, PatientService, AlertService } from '../_services/index';
import { Patient } from '../_models/index';

@Component({
    templateUrl: './assign-patient-recomendation.modal.component.html'
})
export class AssignPatientRecomendationModalComponent implements IModalDialog, OnDestroy {

    loading = false;

    data: any = new Patient();
    subPatients: Subscription;
    patients: any[];
    search = '';
    patient: any = null;

    @ViewChild('f') myForm;
    constructor(
        private patientService: PatientService,
    ) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Вибрати',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];

    }

    ngOnDestroy() {
        if (this.subPatients) this.subPatients.unsubscribe();
    }

    submit(f, options) {
        options.closeDialogSubject.next(this.patient);
    }

    load() {
        if (this.search.length > 0) {
            this.loading = true;
            this.subPatients = this.patientService.getAll(this.search).subscribe(data => {
                this.patients = this.patientService.sortBy({ sortColumn: 'fullName', sortDirection: 'asc' }, data);
                this.loading = false;
            });
        } else {
            this.patients = [];
            this.patient = null;
        }
    }
}
