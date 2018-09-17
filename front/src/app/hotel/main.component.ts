import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { HotelState } from '../_storage/index';
import { AlertService, HotelService } from '../_services/index';

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
        private alertService: AlertService,
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
    
    load() {
        this.loading = true;
        this.dates = [];
        let currentDay = new Date();
        currentDay.setDate(currentDay.getDate() - 1);
        currentDay.setHours(0, 0, 0, 0);
        for (let i = 0; i < 14; i++) {
            let date = new Date(currentDay.setDate(currentDay.getDate() + 1));
            let day = date.toLocaleDateString("uk", { weekday: 'short', month: 'numeric', day: 'numeric' });
            this.dates.push({ date: date, str: day, we: [6,0].includes(date.getDay()) });
        }
        this.sub = this.service.getKoikaMap().subscribe(data => {
            this.loading = false;
            this.items = data;
            this.items.forEach(item => {
                let line = [];
                item.records.forEach(record => {
                    let start:any = new Date(record.start);
                    let finish:any = new Date(record.finish);
                    let duration:any = (finish - start) / (1000 * 60 * 60 * 24) + 1;
                    // console.log(start, finish, delta);
                    let currentDay = null;
                    for (let i = 0; i < 30; i++) {
                        currentDay = this.dates[i].date;
                        if (line[i] == undefined) line[i] = {state: 'FREE', name: ''};
                        if (start <= currentDay && finish >= currentDay) {
                            line[i].duration = (finish - currentDay) / (1000 * 60 * 60 * 24) + 1;
                            line[i].id = record.id;
                            line[i].price = record.price;
                            line[i].state = record.state;
                            line[i].start = record.start;
                            line[i].finish = record.finish;
                            line[i].name = record.koika.patient.person.fullName;
                            i = i + line[i].duration - 1;
                        }
                    }
                });
                item.line = line.length > 0 ? line : new Array(30).fill({state: 'FREE', name: '&nbsp;'});
            });
        });
    }
}
