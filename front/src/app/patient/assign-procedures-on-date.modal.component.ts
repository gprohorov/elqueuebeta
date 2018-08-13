import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogButton, IModalDialogOptions } from 'ngx-modal-dialog';

import { PatientService, AlertService } from '../_services/index';

@Component({
    templateUrl: './assign-procedures-on-date.modal.component.html',
})
export class PatientAssignProceduresOnDateModalComponent implements IModalDialog {

    data: any;

    @ViewChild('f') myForm;
    constructor(
        private alertService: AlertService,
        private patientService: PatientService
    ) { }

    dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Призначити',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.data = options.data;
        this.data.date = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000 + 24*60*60*1000)).toISOString().slice(0, -14);
        this.data.appointed = 9;
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.patientService.assignProceduresOnDate(
            this.data.patientId, this.data.date, this.data.appointed
        ).subscribe(() => {
            this.alertService.success('Пацієнта ' + this.data.patientName
                + ' назначено на процедури на наступну дату.');
            options.closeDialogSubject.next();
        });
    }

    ngOnDestroy() {
    }
}