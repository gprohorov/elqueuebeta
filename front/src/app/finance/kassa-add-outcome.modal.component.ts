import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';

import { CashType } from '../_storage/index';
import { AlertService, FinanceService } from '../_services/index';

@Component({
    templateUrl: './kassa-add-outcome.modal.component.html',
})
export class KassaAddOutcomeModalComponent implements IModalDialog {

    data = {
        type: 'EXTRACTION',
        sum: 0,
        desc: ''
    };
    cashType = CashType;
    cashTypes = Object.keys(CashType);
    kassa = 0;
    sub: Subscription;
    subKassa: Subscription;

    @ViewChild('f') myForm;
    constructor(private alertService: AlertService, private financeService: FinanceService) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Видати', 
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.subKassa = this.financeService.getKassa().subscribe(data => { this.kassa = data; });
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.sub = this.financeService.kassaAddOutcome({
            type: this.data.type,
            sum: this.data.sum * -1,
            desc: this.data.desc
            }).subscribe(data => {
                this.alertService.success('Видано з каси ' + this.data.sum 
                    + ' грн. по статті витрат: "' + this.cashType[this.data.type].text + '"');
                options.closeDialogSubject.next();
            },
            error => {
                this.alertService.error('Помилка видачі: ' + error.message);
            });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subKassa) this.subKassa.unsubscribe();
    }
}
