import { Component, ComponentRef, ViewChild, ElementRef } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogButton, IModalDialogOptions } from 'ngx-modal-dialog';

import { AlertService, PatientService } from '../_services/index';

@Component({
    templateUrl: './debt-list.modal.component.html',
})
export class DebtListModalComponent implements IModalDialog {

    data: any;




    constructor() {}

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        this.data = options.data;
        options.actionButtons = [{
          text: 'Закрити', buttonClass: 'btn btn-secondary' }];
    }

}
