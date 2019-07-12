import { Component, ComponentRef, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogOptions } from 'ngx-modal-dialog';

import { FinanceService, AlertService } from '../_services/index';

@Component({
    templateUrl: './doctor-salary-history.modal.component.html'
})
export class DoctorSalaryHistoryModalComponent implements IModalDialog {

    loading = false;
    data: any;
    sub: Subscription;
    
    doctorName: string;
    doctor: number;
    from: string;
    to: string;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private alertService: AlertService,
        private financeService: FinanceService
    ) { }

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        options.actionButtons = [{ text: 'Закрити', buttonClass: 'btn btn-secondary' }];
        this.doctorName = options.data.name;
        this.doctor = options.data.doctorId;
        this.from = options.data.from;
        this.to = options.data.to;
        this.load();
    }

    getSumTotal() {
        let sum = 0;
        if (this.data && this.data.length > 0) this.data.forEach( (item) => { sum += item.sum; });
        return sum;
    }
    
    isValid() {
        return (this.from && this.to && this.to >= this.from);
    }

    load() {
        if (!this.isValid()) return;
        this.loading = true;
        this.data = [];
        this.sub = this.financeService.getDoctorSalaryHistory(this.doctor, this.from, this.to)
            .subscribe(data => {
                this.data = data;
                this.loading = false;
            },
            error => {
                this.loading = false;
                this.alertService.error('Помилка завантаження');
            });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }
}
