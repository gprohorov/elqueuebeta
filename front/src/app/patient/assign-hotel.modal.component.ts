import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogButton, IModalDialogOptions } from 'ngx-modal-dialog';

import { HotelService, PatientService, AlertService } from '../_services/index';

@Component({
    templateUrl: './assign-hotel.modal.component.html',
})
export class PatientAssignHotelModalComponent implements IModalDialog {

    data: any;
    koikas: any[];
    sub: Subscription;

    @ViewChild('f') myForm;
    constructor(
        private alertService: AlertService,
        private hotelService: HotelService
    ) { }

    dialogInit(reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{
            text: 'OK',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
        this.data = options.data;
        this.data.start = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
        this.data.finish = this.data.start;
        this.data.state = "OCCUP";
        this.sub = this.hotelService.getKoikaList().subscribe(data => {
            this.koikas = data;
        });
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        this.hotelService.createRecord({
            patientId: this.data.patientId,
            desc: this.data.desc,
            koikaId: this.data.koikaId,
            price: this.data.price,
            start: this.data.start,
            finish: this.data.finish,
            state: this.data.state
        }).subscribe(() => {
            this.alertService.success('Пацієнта ' + this.data.patientName + ' поселено/заброньовано в готель.');
            options.closeDialogSubject.next();
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
    
    updatePrice(koikaId) {
        this.data.price = this.koikas.find(x => x.id == koikaId).price;
    }
}