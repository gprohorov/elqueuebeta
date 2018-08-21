import { Component, ComponentRef, ViewChild } from '@angular/core';
import { IModalDialog, IModalDialogButton, IModalDialogOptions } from 'ngx-modal-dialog';
import { Subscription } from 'rxjs/Subscription';

import { PatientService, AlertService } from '../_services/index';
import { Patient } from '../_models/index';

@Component({
    templateUrl: './create-patient.modal.component.html'
})
export class CreatePatientModalComponent implements IModalDialog {
    
    loading = false;
    
    data: Patient = new Patient();

    @ViewChild('f') myForm;
    constructor(
        private service: PatientService,
        private alertService: AlertService
    ) { }

    dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Зберегти',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
    }
    
    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;
        
        this.service.update(this.data).subscribe(
            data => {
                this.alertService.success('Пацієнта ' + this.data.person.fullName + ' створено.');
                options.closeDialogSubject.next();
            },
            error => {
                this.alertService.error(error);
            });
    }
}
