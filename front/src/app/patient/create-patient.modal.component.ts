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
    
    data: any;
    sub: Subscription;

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
        this.data = options.data;
        this.data.date = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000 + 24*60*60*1000)).toISOString().slice(0, -14);
        this.data.appointed = 9;
    }
    
    ngOnInit() {
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load(id: string) {
        this.loading = true;
        this.sub = this.service.get(id).subscribe(
            data => {
                data.person.gender = data.person.gender.toString();
                this.data = data
                this.loading = false;
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;
        
        this.service.update(this.data).subscribe(
            data => {
                this.alertService.success('Зміни збережено.', true);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}
