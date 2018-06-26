import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogButton, IModalDialogOptions } from 'ngx-modal-dialog';

import { AlertService, PatientService } from '../_services/index';

@Component({
    templateUrl: './income.modal.component.html',
})
export class PatientIncomeModalComponent implements IModalDialog {

    data: any;
    model: any = {
        paymentType: 'CASH',
        sum: 0,
        discount: 0,
        desc: ''
    };
    sub: Subscription;
    loading: boolean = false;
    showDetails: boolean = false;

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
        this.model.patientId = this.data.id;
        this.model.sum = this.data.balance < 0 ? this.data.balance * -1 : 0;
    }

    details() {
        this.showDetails = true;
        this.loading = true;
        this.sub = this.patientService.getBalance(this.data.id).subscribe((data) => {
            this.loading = false;
            this.details = data;
        });
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;
        this.sub = this.patientService.income(this.model).subscribe(() => {
            this.alertService.success('На рахунок пацієнта ' + this.data.person.fullName
                + ' внесено ' + this.model.sum + ' грн.');
            options.closeDialogSubject.next();
        });
    }
}