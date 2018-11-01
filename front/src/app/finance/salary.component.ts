import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { FinanceService } from '../_services/index';

import { SetSalaryModalComponent } from './set-salary.modal.component';

@Component({
    templateUrl: './salary.component.html'
})
export class FinanceSalaryComponent implements OnInit, OnDestroy {

    sub: Subscription;
    loading = false;
    data: any;

    totalRecd = 0;
    totalSummary = 0;
    totalActual = 0;

    constructor(
        private modalService: ModalDialogService,
        private viewRef: ViewContainerRef,
        private service: FinanceService) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    showSetSalaryPopup(doctor: any) {
        this.modalService.openDialog(this.viewRef, {
            title: 'Лікар: ' + doctor.name,
            childComponent: SetSalaryModalComponent,
            data: doctor
        });
    }

    load() {
        this.loading = true;
        this.sub = this.service.getSalary().subscribe(data => {
            this.data = data;
            this.totalSummary = 0;
            this.totalActual = 0;
            data.forEach( currentValue => {
                currentValue.lastName = currentValue.name.split(' ')[0];
                this.totalRecd += currentValue.recd;
                this.totalSummary += currentValue.total;
                this.totalActual += currentValue.actual;
            });
            this.loading = false;
        });
    }
}
