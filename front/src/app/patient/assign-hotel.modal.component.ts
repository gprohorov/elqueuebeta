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
    subLoad: Subscription;

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
        this.sub = this.hotelService.getKoikaList().subscribe(data => {
            this.koikas = data;
        });
        this.data = options.data;
        this.data.start = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
        this.data.finish = this.data.start;
        this.data.state = "OCCUP";

        if (this.data.id) {
            this.load(this.data.id);
        }
    }

    load(id) {
        this.hotelService.getRecord(id).subscribe((data) => {
            this.data = data;
            this.data.start = data.start.slice(0, -9);
            this.data.finish = data.finish.slice(0, -9);
            this.data.koikaId = data.koika.id;
            if (data.state == 'OCCUP') {
                this.data.disableBook = true;
            }
        });
    }

    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;

        let record: any = {
            patientId: this.data.patientId,
            desc: this.data.desc,
            koikaId: this.data.koikaId,
            price: this.data.price,
            start: this.data.start,
            finish: this.data.finish,
            state: this.data.state
        };

        if (this.data.id) {
            record.id = this.data.id;
            this.hotelService.updateRecord(record).subscribe((data) => {
                if (data.status) {
                    this.alertService.success('Запис Пацієнта ' + this.data.patientName + ' у готель успішно змінений.');
                    options.closeDialogSubject.next();
                } else {
                    this.alertService.error(data.message);
                }
            });
        } else {
            this.hotelService.createRecord(record).subscribe((data) => {
                if (data.status) {
                    this.alertService.success('Пацієнта ' + this.data.patientName + ' поселено/заброньовано в готель.');
                    options.closeDialogSubject.next();
                } else {
                    this.alertService.error(data.message);
                }
            });
        }
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subLoad) this.subLoad.unsubscribe();
    }

    updatePrice(koikaId) {
        this.data.price = this.koikas.find(x => x.id == koikaId).price;
    }
}