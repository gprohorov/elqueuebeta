import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { NgxMasonryOptions } from '../_helpers/index';
import { Patient } from '../_models/index';
import { Status, Activity } from '../_storage/index';
import { AlertService, PatientsQueueService } from '../_services/index';

@Component({
    templateUrl: './list.component.html',
    styleUrls: ['./list.component.css']
})
export class ProceduresQueueListComponent implements OnInit, OnDestroy {

    loading = false;

    sub: Subscription;
    items: any[] = [];
    Activity = Activity;
    Status = Status;
    updateMasonryLayout = false;

    public myOptions: NgxMasonryOptions = {
        transitionDuration: '0.2s',
        columnWidth: 200,
        fitWidth: true,
        horizontalOrder: true,
        gutter: 20
    };

    constructor(
        private alertService: AlertService,
        private service: PatientsQueueService
    ) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    getTimeDiffClass(v: number) {
        return 'text-' + (v > 60 ? 'danger' : v > 30 ? 'success' : 'primary');
    }

    toggleGroup(item: any) {
        item.expanded = !item.expanded;
        this.updateMasonryLayout = true;
    }

    load() {
        this.loading = true;
        this.sub = this.service.getTails().subscribe(
            data => {
                this.items = data;
                this.loading = false;
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

}
