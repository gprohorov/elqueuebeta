import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';

import { Doctor } from '../_models/index';
import { DoctorService, FinanceService, AlertService } from '../_services/index';

@Component({
    templateUrl: './give-salary.modal.component.html',
})
export class GiveSalaryModalComponent implements IModalDialog {

    data = {
        doctorId: null,
        sum: 0
    };
    doctors: Doctor[];
    kassa = 0;
    sub: Subscription;
    subDoctors: Subscription;
    subKassa: Subscription;

    @ViewChild('f') myForm;
    constructor(
        private alertService: AlertService,
        private doctorService: DoctorService,
        private financeService: FinanceService
    ) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Виплатити',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.subDoctors = this.doctorService.getAll().subscribe(data => { this.doctors = data; });
        this.subKassa = this.financeService.getKassa().subscribe(data => { this.kassa = data; });
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.sub = this.financeService.giveSalary({
            doctorId: this.data.doctorId, 
            sum: this.data.sum}).subscribe(resp => {
                if (resp && resp.status) {
                    this.alertService.success('Зарплату видано.');
                    options.closeDialogSubject.next();
                } else {
                    this.alertService.error('Помилка видачі: ' + resp.message);
                }
        });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subDoctors) this.subDoctors.unsubscribe();
        if (this.subKassa) this.subKassa.unsubscribe();
    }
}
