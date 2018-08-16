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
    subTemp: Subscription;
    reloadFunc: any;
    items: any[] = [];
    Activity = Activity;
    Status = Status;
    updateMasonryLayout = false;

    public myOptions: NgxMasonryOptions = {
        transitionDuration: '0.001s',
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
        this.reloadFunc = setInterval(() => {
            this.load();
        }, 60000);
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subTemp) this.subTemp.unsubscribe();
        if (this.reloadFunc) clearInterval(this.reloadFunc);
    }

    getTimeDiffClass(v: number) {
        return 'text-' + (v > 60 ? 'danger' : v > 30 ? 'success' : 'primary');
    }

    toggleGroup(item: any) {
        item.expanded = !item.expanded;
        this.updateMasonryLayout = true;
    }

    updateOutOfTurn(id: string, value: boolean, patientName: string, procedureName: string) {
        if (confirm((value ? 'Призначити' : 'Зняти') + ' "Поза чергою" пацієнта "'
                + patientName + '", процедура "'  + procedureName + '" ?')) {
            this.subTemp = this.service.updateOutOfTurn(id, value).subscribe(data => {
                this.load();
            });
        }
    }

    executeProcedure(talonId: string, patientName: string, procedureName: string) {
        if (confirm('Виконати процедуру "' + procedureName + '" для пацієнта "' + patientName + '" ?')) {
            this.subTemp = this.service.executeProcedure(talonId).subscribe(data => {
                this.load();
            });
        }
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
