import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';

import { CashType } from '../_storage/index';
import { AlertService, FinanceService, OutcomeService } from '../_services/index';

@Component({
    templateUrl: './kassa-add-outcome.modal.component.html',
})
export class KassaAddOutcomeModalComponent implements IModalDialog {

    data = {
        type: 'EXTRACTION',
        itemId: 'other',
        sum: 0,
        desc: ''
    };
    kassa = 0;
    outcomeItems: any;
    sub: Subscription;
    subKassa: Subscription;
    subOutcomeItems: Subscription;

    @ViewChild('f') myForm;
    constructor(
        private alertService: AlertService,
        private financeService: FinanceService,
        private outcomeService: OutcomeService
    ) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Видати', 
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.subKassa = this.financeService.getKassa().subscribe(data => { this.kassa = data; });
        this.subOutcomeItems = this.outcomeService.getOutcomeTree()
            .subscribe(data => { 
                this.outcomeItems = data.filter(x => x.id != null );
            });
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.sub = this.financeService.kassaAddOutcome({
            type: this.data.type,
            itemId: this.data.itemId == 'other' ? null : this.data.itemId,
            sum: this.data.sum * -1,
            desc: this.data.desc
            }).subscribe(data => {
                this.alertService.success('Видано з каси ' + this.data.sum + ' грн.');
                options.closeDialogSubject.next();
            },
            error => {
                this.alertService.error('Помилка видачі: ' + error.message);
            });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subKassa) this.subKassa.unsubscribe();
        if (this.subOutcomeItems) this.subOutcomeItems.unsubscribe();
    }
}
