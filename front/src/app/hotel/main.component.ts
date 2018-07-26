﻿import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { HotelState } from '../_storage/index';
import { AlertService, HotelService } from '../_services/index';

@Component({
    templateUrl: './main.component.html',
})

export class HotelMainComponent implements OnInit, OnDestroy {
    
    loading = false;
    sub: Subscription;
    items: any[];
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
    }
    
    load() {
        this.loading = true;
        this.sub = this.service.getBooking().subscribe(data => {
            this.items = data;
            this.loading = false;
        });
    }
}
