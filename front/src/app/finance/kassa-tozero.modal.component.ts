import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';

import { FinanceService, AlertService } from '../_services/index';

@Component({
    templateUrl: './kassa-tozero.modal.component.html',
})
export class KassaTozeroModalComponent implements IModalDialog {

    data = { sum: 0 };
    kassa = 0;
    sub: Subscription;
    subKassa: Subscription;

    @ViewChild('f') myForm;
    constructor(
        private alertService: AlertService,
        private financeService: FinanceService
    ) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Здати',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.subKassa = this.financeService.getKassa().subscribe(data => { 
            this.kassa = data;
            this.data.sum = data;
        });
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.sub = this.financeService.toZero(this.data.sum).subscribe(() => {
            this.alertService.success('Касу здано.');
            options.closeDialogSubject.next();
        });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subKassa) this.subKassa.unsubscribe();
    }
}
