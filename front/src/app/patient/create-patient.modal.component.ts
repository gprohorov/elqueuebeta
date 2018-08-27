import { Component, ComponentRef, ViewChild, OnDestroy } from '@angular/core';
import { IModalDialog, IModalDialogButton, IModalDialogOptions } from 'ngx-modal-dialog';
import { Subscription } from 'rxjs/Subscription';

import { ProcedureService, PatientService, AlertService } from '../_services/index';
import { Patient } from '../_models/index';

@Component({
    templateUrl: './create-patient.modal.component.html'
})
export class CreatePatientModalComponent implements IModalDialog, OnDestroy {

    loading = false;

    data: any = new Patient();
    subPatients: Subscription;
    subProcedures: Subscription;
    procedures: any[];
    patients: any[];
    addProcedure = true;
    creating = false;
    search: string = '';
    patientId: string = null;

    @ViewChild('f') myForm;
    constructor(
        private patientService: PatientService,
        private procedureService: ProcedureService,
        private alertService: AlertService
    ) { }

    dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {

        this.subProcedures = this.procedureService.getAll().subscribe(data => {
            this.procedures = data;
            this.data.procedureId = this.procedures[0].id;
            this.data.date = new Date().toISOString().split('T').shift();
            let hours = new Date().getHours();
            this.data.appointed = hours < 8 ? 8 : hours > 16 ? 16 : hours;
            this.data.activate = true;
        });

        options.actionButtons = [{
            text: 'Зберегти',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];

    }

    ngOnDestroy() {
        if (this.subPatients) this.subPatients.unsubscribe();
        if (this.subProcedures) this.subProcedures.unsubscribe();
    }

    clearProcedure() {
        if (!this.addProcedure) {
            this.data.procedureId = null;
            this.data.date = null;
            this.data.appointed = null;
            this.data.activate = false;
        }
    }

    submit(f, options) {
        f.submitted = true;
        if (this.creating) {
            if (!f.form.valid) return false;
            this.patientService.create(this.data).subscribe(
                data => {
                    this.alertService.success('Пацієнта ' + this.data.person.fullName + ' створено.');
                    options.closeDialogSubject.next(data.id);
                },
                error => {
                    this.alertService.error(error);
                });
        } else if (this.patientId != null) {
            let patientName = '';
            let patient = this.patients.find(x => { return x.id == this.patientId });
            if (patient) patientName = patient.person.fullName;
            this.patientService.assignProcedure(
                this.patientId, this.data.procedureId, this.data.date, this.data.appointed, this.data.activate
            ).subscribe(() => {
                this.alertService.success('Пацієнта ' + patientName + ' назначено на процедуру '
                    + this.procedures.find(x => x.id == this.data.procedureId).name);
                options.closeDialogSubject.next(this.patientId);
            });
        }
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
            this.patientId = null;
        }
    }
}
