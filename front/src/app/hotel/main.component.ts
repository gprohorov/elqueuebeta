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
    items: any[] = [];
    dates: any[] = [];
    HotelState = HotelState;
    HotelStates = Object.keys(HotelState);
    
    constructor(
        private alertService: AlertService,
        private service: HotelService
    ) { }
    
    ngOnInit() {
        let currentDay = new Date();
        currentDay.setDate(currentDay.getDate() - 1);
        currentDay.setHours(0, 0, 0, 0);
        for (let i = 0; i < 30; i++) {
            let date = new Date(currentDay.setDate(currentDay.getDate() + 1));
            let day = date.toLocaleDateString("uk", { weekday: 'short', month: 'numeric', day: 'numeric' });
            this.dates.push({ date: date, str: day, we: [6,0].includes(date.getDay()) });
        }
        this.load();
    }
    
    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }
    
    load() {
        this.loading = true;
        this.sub = this.service.getKoikaMap().subscribe(data => {
            this.loading = false;
            this.items = data;
            this.items.forEach(item => {
                let line = [];
                item.records.forEach(record => {
                    let start = new Date(record.start);
                    let finish = new Date(record.finish);
                    let currentDay = null;
                    for (let i = 0; i < 30; i++) {
                        currentDay = this.dates[i].date;
                        if (line[i] == undefined) line[i] = {state: 'FREE', name: '&nbsp;'};
                        if (start <= currentDay && finish >= currentDay) {
                            line[i].state = record.state;
                            line[i].name = record.koika.patient.person.fullName;
                        }
                    }
                });
                item.line = line.length > 0 ? line : new Array(30).fill({state: 'FREE', name: '&nbsp;'});
            });
        });
    }
}
