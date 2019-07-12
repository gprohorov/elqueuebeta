import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { HotelState } from '../_storage/index';
import { HotelService } from '../_services/index';
import { PatientAssignHotelModalComponent } from '../patient/assign-hotel.modal.component';

@Component({
    templateUrl: './main.component.html',
})
export class HotelMainComponent implements OnInit, OnDestroy {

    loading = false;
    sub: Subscription;
    subDel: Subscription;
    items: any[] = [];
    dates: any[] = [];
    start;
    HotelState = HotelState;
    HotelStates = Object.keys(HotelState);

    constructor(
        private viewRef: ViewContainerRef,
        private modalService: ModalDialogService,
        private service: HotelService
    ) {
        this.start = this.getMonday(new Date());
        this.start.setHours(0, 0, 0, 0);
    }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subDel) this.subDel.unsubscribe();
    }

    cancel(recordId) {
        if (confirm('Видалити запис?')) {
            this.subDel = this.service.cancelRecord(recordId).subscribe(() => { this.load(); });
        }
    }

    update(record) {
        const options = {
            title: 'Пацієнт: ' + record.name,
            childComponent: PatientAssignHotelModalComponent,
            data: { id: record.id, patientName: record.name },
            closeDialogSubject: null
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(); });
    }

    slide(days: number) {
        this.start.setDate(this.start.getDate() + days);
        this.load();
    }

    getMonday(date) {
        const day = date.getDay() || 7;
        if (day !== 1) date.setHours(-24 * (day - 1));
        return date;
    }

    load() {
        this.loading = true;
        this.dates = [];
        const currDay = new Date(this.start.getTime());
        currDay.setDate(currDay.getDate() - 1);
        currDay.setHours(0, 0, 0, 0);
        for (let i = 0; i < 14; i++) {
            const date = new Date(currDay.setDate(currDay.getDate() + 1));
            const day = date.toLocaleDateString('uk', { weekday: 'long', month: 'numeric', day: 'numeric' });
            this.dates.push({ date: date, str: day, we: [6, 0].includes(date.getDay()),
                today: ( date.toDateString() === (new Date()).toDateString() ) });
        }
        this.sub = this.service.getKoikaMap(this.start.toISOString().split('T')[0]).subscribe(data => {
            this.items = data;
            this.items.forEach(item => {
                item.line = new Array(14).fill({state: 'FREE', name: ''});
                item.records.forEach(record => {
                    const start: any = new Date(record.start);
                    const finish: any = new Date(record.finish);
                    for (let i = 0; i < 14; i++) {
                        const currentDay = this.dates[i].date;
                        if (start <= currentDay && finish >= currentDay) {
                            const duration = Math.round((finish - currentDay) / (1000 * 60 * 60 * 24) + 1);
                            item.line[i] = {
                                duration: duration,
                                id: record.id,
                                price: record.price,
                                state: record.state,
                                start: record.start,
                                finish: record.finish,
                                name: record.koika.patient.person.fullName
                            };
                            for (let c = i + 1; c < duration + i; c++) item.line[c] = {};
                            i = i - 1 + duration;
                        }
                    }
                });
            });
            this.loading = false;
        });
    }
}
