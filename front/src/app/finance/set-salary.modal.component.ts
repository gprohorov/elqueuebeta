import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';

import { FinanceService, AlertService } from '../_services/index';

@Component({
    templateUrl: './set-salary.modal.component.html',
})
export class SetSalaryModalComponent implements IModalDialog {

    data: any;
    sub: Subscription;

    @ViewChild('f') myForm;
    constructor(
        private alertService: AlertService,
        private financeService: FinanceService
    ) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Призначити',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.data = options.data;
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.sub = this.financeService.setSalary({
            doctorId: this.data.doctorId,
            award: this.data.award,
            penalty: this.data.penalty
        }).subscribe(() => {
            this.alertService.success('Зарплату призначено.');
            options.closeDialogSubject.next();
        });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }
}
