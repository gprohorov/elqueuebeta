import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';

import { CashType } from '../_storage/index';
import { AlertService, FinanceService, OutcomeService, SettingsService } from '../_services/index';

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
    settings: any;
    outcomeItems: any;
    sub: Subscription;
    subKassa: Subscription;
    subSettings: Subscription;
    subOutcomeItems: Subscription;

    @ViewChild('f') myForm;
    constructor(
        private alertService: AlertService,
        private financeService: FinanceService,
        private outcomeService: OutcomeService,
        private settingsService: SettingsService
    ) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Видати', 
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.subKassa = this.financeService.getKassa().subscribe(data => { this.kassa = data; });
        this.subSettings = this.settingsService.get().subscribe(
            data => {
                this.settings = data;
                this.subOutcomeItems = this.outcomeService.getOutcomeTree()
                    .subscribe(data => { 
                        data = data.filter(x => x.id != null);
                        data.forEach(cat => {
                            const ext = cat.items.findIndex(x => x.id === this.settings.extractionItemId);
                            const sal = cat.items.findIndex(x => x.id === this.settings.salaryItemId);
                            if (ext > -1) cat.items.splice(ext, 1);
                            if (sal > -1) cat.items.splice(sal, 1);
                        })
                        this.outcomeItems = data;
                    });
            },
            error => {
                this.alertService.error('Помилка на сервері', false);
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
            }, options.data.SA).subscribe(resp => {
                if (resp && resp.status) {
                    this.alertService.success('Видано з каси ' + this.data.sum + ' грн.');
                    options.closeDialogSubject.next();
                } else {
                    this.alertService.error('Помилка видачі: ' + resp.message);
                }
            },
            error => {
                this.alertService.error('Помилка видачі: ' + error.message);
            });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subKassa) this.subKassa.unsubscribe();
        if (this.subSettings) this.subSettings.unsubscribe();
        if (this.subOutcomeItems) this.subOutcomeItems.unsubscribe();
    }
}
