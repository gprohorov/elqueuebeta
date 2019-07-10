import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';

import { UsiService, AlertService } from '../_services/index';

@Component({
    templateUrl: './usi.modal.component.html',
})
export class UsiModalComponent implements IModalDialog {

    data: any;
    sub: Subscription;

    @ViewChild('f') myForm;
    constructor(
        private alertService: AlertService,
        private usiService: UsiService
    ) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Зберегти',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.data = options.data;
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.usiService.create(this.data).subscribe(() => {
            this.alertService.success('Обстеження УЗД "' + this.data.title + '" створено ');
            options.closeDialogSubject.next();
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}
