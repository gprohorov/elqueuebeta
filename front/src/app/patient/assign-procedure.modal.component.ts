import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogButton, IModalDialogOptions } from 'ngx-modal-dialog';

import { Procedure } from '../_models/index';
import { ProcedureService, PatientService, AlertService } from '../_services/index';

@Component({
    templateUrl: './assign-procedure.modal.component.html',
})
export class PatientAssignProcedureModalComponent implements IModalDialog {

    data: any;
    procedures: Procedure[];
    sub: Subscription;

    @ViewChild('f') myForm;
    constructor(
        private alertService: AlertService,
        private procedureService: ProcedureService,
        private patientService: PatientService
    ) { }

    dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'Призначити',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Відміна', buttonClass: 'btn btn-secondary' }];
        this.data = options.data;
        this.data.date = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
        this.sub = this.procedureService.getAll().subscribe(data => {
            this.procedures = data;
            this.data.procedureId = this.procedures[0].id;
        });
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.patientService.assignProcedure(this.data.patientId, this.data.procedureId, this.data.date)
            .subscribe(() => {
                this.alertService.success('Пацієнта ' + this.data.patientName + ' назначено на процедуру '
                    + this.procedures.find(x => x.id == this.data.procedureId).name);
                options.closeDialogSubject.next();
            });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}