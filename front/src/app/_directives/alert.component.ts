import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { AlertService } from '../_services/index';

@Component({
    selector: 'app-alert',
    template: `<div *ngIf="message" (click)="message=''" class="alert alert-{{message.type}}">{{message.text}}</div>`
})
export class AlertComponent implements OnDestroy {
    
    private subscription: Subscription;
    message: any;

    constructor(private alertService: AlertService) {
        this.subscription = alertService.getMessage().subscribe(message => { this.message = message; });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
