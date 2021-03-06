import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
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
        private route: ActivatedRoute,
        private router: Router,
        private alertService: AlertService,
        private financeService: FinanceService
    ) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        this.data = {
            doctorID: options.data.doctorId,
            award: 0,
            penalty: 0
        };
        options.actionButtons = [{
            text: 'Призначити',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.sub = this.financeService.setSalary({
            doctorID: this.data.doctorID,
            award: this.data.award,
            penalty: this.data.penalty
        }).subscribe(() => {
            this.alertService.success('Зарплату призначено.');
            options.closeDialogSubject.next();
        });
    }

    editDoctor() {
        window.open('/#/doctor-form;id=' + this.data.doctorID, '_blank');
    }
    
    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }
}
