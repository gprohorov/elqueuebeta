import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogButton, IModalDialogOptions } from 'ngx-modal-dialog';

import { AlertService, PatientService } from '../_services/index';

@Component({
    templateUrl: './income.modal.component.html',
})
export class PatientIncomeModalComponent implements IModalDialog {

    data: any
    sub: Subscription;

    @ViewChild('f') myForm;
    constructor(
        private alertService: AlertService,
        private patientService: PatientService
    ) { }

    dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Внести',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.data = options.data;
        this.data.cashLess = false;
        this.patientService.getBalance(this.data.patientId).subscribe( (data) => {
            console.log(data);
            this.data = data;
        }, error => {
            this.alertService.error(error);
        });
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;
        this.patientService.income(this.data.patientId, this.data.sum, this.data.cashLess)
            .subscribe(() => {
                this.alertService.success('На рахунок пацієнта ' + this.data.patientName
                    + ' внесено ' + this.data.sum + 'грн.');
                options.closeDialogSubject.next();
            });
    }
}