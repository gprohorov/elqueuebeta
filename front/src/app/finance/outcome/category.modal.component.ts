import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';

import { AlertService, OutcomeService } from '../../_services/index';

@Component({
    templateUrl: './category.modal.component.html',
})
export class FinanceOutcomeCategoryModalComponent implements IModalDialog {

    node: {
        id: string,
        name: string,
        catID: string
    };
    
    sub: Subscription;

    @ViewChild('f') myForm;
    constructor(private alertService: AlertService, private service: OutcomeService) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Застосувати', 
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.node = options.data || this.node;
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.sub = this.service.sendNode(this.node).subscribe(data => {
                this.alertService.success('Категорію успішно створено/відредаговано.');
                options.closeDialogSubject.next();
            },
            error => {
                this.alertService.error('Помилка створення/редагування категорії: ' + error.message);
            });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }
}
