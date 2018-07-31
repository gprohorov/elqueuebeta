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
        let date = new Date();
        date = new Date(date.setDate(date.getDate() - 1));
        for (let i=1; i<=28; i++) {
            date = new Date(date.setDate(date.getDate() + 1));
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
                item.line = this.dates;
                item.records.forEach(record => {
                    
                });
            });
        });
    }
}
