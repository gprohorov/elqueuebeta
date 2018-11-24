import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';

import { AlertService, OutcomeService } from '../../_services/index';

@Component({
    templateUrl: './item.modal.component.html',
})
export class FinanceOutcomeItemModalComponent implements IModalDialog {

    node: {
        id: string,
        name: string,
        catID: string
    };
    categories: any = [];
    
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
        this.node = options.data.node || this.node;
        this.categories = options.data.categories.filter(x => x.catID === null); 
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.sub = this.service.sendNode(this.node).subscribe(data => {
                this.alertService.success('Статтю витрат успішно створено/відредаговано.');
                options.closeDialogSubject.next();
            },
            error => {
                this.alertService.error('Помилка створення/редагування статті витрат: ' + error.message);
            });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }
}
