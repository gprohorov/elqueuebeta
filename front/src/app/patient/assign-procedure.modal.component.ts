import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogButton, IModalDialogOptions } from 'ngx-modal-dialog';

import { Procedure } from '../_models/index';
import { ProcedureService, PatientService } from '../_services/index';

@Component({
    templateUrl: './assign-procedure.modal.component.html',
})
export class PatientAssignProcedureModalComponent implements IModalDialog {

    data: any;
    procedures: Procedure[];
    sub: Subscription;

    @ViewChild('f') myForm;
    constructor(private procedureService: ProcedureService, private patientService: PatientService) {
    }

    dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Призначити',
            onAction: () => {
                return this.submit(this.myForm);
            }
        }, { text: 'Відміна', buttonClass: 'btn btn-secondary' }];
        this.data = options.data;
        this.data.date = (new Date()).toISOString().split('T')[0];
        this.sub = this.procedureService.getAll().subscribe(data => {
            this.procedures = data;
            console.log(this.procedures);
            this.data.procedureId = this.procedures[0].id;
        });
    }

    submit(f) {
        f.submitted = true;
        if (!f.form.valid) return false;
        
        this.patientService.assignProcedure(this.data.patientId, this.data.procedureId, this.data.date).subscribe(() => {
            //this.alertService.success('Операція пройшла успішно');
            return true;
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}