import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { HotelState } from '../_storage/index';
import { AlertService, HotelService } from '../_services/index';
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
    HotelState = HotelState;
    HotelStates = Object.keys(HotelState);
    
    constructor(
        private viewRef: ViewContainerRef,
        private alertService: AlertService,
        private modalService: ModalDialogService,
        private service: HotelService
    ) { }
    
    ngOnInit() {
        this.load();
    }
    
    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subDel) this.subDel.unsubscribe();
    }
    
    cancel(recordId) {
        if (confirm('Видалити запис?')) {
            this.subDel = this.service.cancelRecord(recordId).subscribe(data => { this.load(); });
        }
    }
    
    update(record) {
        let options = {
            title: 'Пацієнт: ' + record.name,
            childComponent: PatientAssignHotelModalComponent,
            data: { id: record.id, patientName: record.name },
            closeDialogSubject: null 
        }
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(); });
    }
    
    load() {
        this.loading = true;
        this.dates = [];
        const start = new Date();
        let currDay = start;
        currDay.setDate(currDay.getDate() - 1);
        currDay.setHours(0, 0, 0, 0);
        for (let i = 0; i < 14; i++) {
            let date = new Date(currDay.setDate(currDay.getDate() + 1));
            let day = date.toLocaleDateString("uk", { weekday: 'short', month: 'numeric', day: 'numeric' });
            this.dates.push({ date: date, str: day, we: [6,0].includes(date.getDay()), 
                today: ( date.toDateString() == (new Date()).toDateString() ) });
        }
        this.sub = this.service.getKoikaMap(start.toISOString().split('T')[0]).subscribe(data => {
            this.loading = false;
            this.items = data;
            this.items.forEach(item => {
                item.line = new Array(14).fill({state: 'FREE', name: ''});
                item.records.forEach(record => {
                    let start:any = new Date(record.start);
                    let finish:any = new Date(record.finish);
                    for (let i = 0; i < 14; i++) {
                        let currentDay = this.dates[i].date;
                        if (start <= currentDay && finish >= currentDay) {
                            let duration = (finish - currentDay) / (1000 * 60 * 60 * 24) + 1;
                            item.line[i] = {
                                duration: duration,
                                id: record.id,
                                price: record.price,
                                state: record.state,
                                start: record.start,
                                finish: record.finish,
                                name: record.koika.patient.person.fullName
                            }
                            for (let c = i + 1; c < duration + i; c++) item.line[c] = {};
                            i = i - 1 + duration;
                        }
                    }
                });
            });
        });
    }
}
