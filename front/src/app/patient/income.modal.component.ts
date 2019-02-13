import { Component, ComponentRef, ViewChild, ElementRef } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { IModalDialog, IModalDialogButton, IModalDialogOptions } from 'ngx-modal-dialog';

import { AlertService, PatientService } from '../_services/index';

@Component({
    templateUrl: './income.modal.component.html',
    styleUrls: ['./income.modal.component.css']
})
export class PatientIncomeModalComponent implements IModalDialog {

    data: any;
    model: any = {
        paymentType: 'CASH',
        sum: 0,
        discount: 0,
        desc: ''
    };
    sub: Subscription;
    loading = false;
    showDetails = false;
    details: any = [];

    @ViewChild('incomeField') incomeField: ElementRef;
    @ViewChild('changeField') changeField: ElementRef;
    @ViewChild('f') myForm;
    constructor(private alertService: AlertService, private patientService: PatientService) {}

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        this.data = options.data;
        this.model.patientId = this.data.id;
        this.model.sum = this.data.balance < 0 ? this.data.balance * -1 : 0;
        options.actionButtons = [{
            text: 'Виписка',
            buttonClass: 'btn btn-info mr-5',
            onAction: () => {
                setTimeout(() => { window.open('/#/receipt/' + this.model.patientId, '_blank'); }, 0);
            }
        }, {
            text: 'Внести',
            onAction: () => {
                return this.submit(this.myForm, options);
            }
        }, { text: 'Скасувати', buttonClass: 'btn btn-secondary' }];
    }

    getDetails() {
        this.showDetails = !this.showDetails;
        this.loading = true;
        this.sub = this.patientService.getBalance(this.data.id).subscribe((data) => {
            this.loading = false;
            this.details = data;
        });
    }

    getSumProcedures() {
        let sum = 0;
        this.details.forEach( (item) => {
            if (item.payment === 'PROC') sum += item.sum;
        });
        return sum * -1;
    }

    getSumIncome() {
        let sum = 0;
        this.details.forEach( (item) => {
            if (item.payment === 'CASH'
                || item.payment === 'CARD'
                || item.payment === 'WIRED'
            ) sum += item.sum;
        });
        return sum;
    }

    getSumDiscount() {
        let sum = 0;
        this.details.forEach( (item) => {
            if (item.payment === 'DISCOUNT') sum += item.sum;
        });
        return sum;
    }

    getSumHotel() {
        let sum = 0;
        this.details.forEach( (item) => {
            if (item.payment === 'HOTEL') sum += item.sum;
        });
        return sum * -1;
    }

    getDesc(item) {
        if (item.payment === 'DISCOUNT') return 'Знижка'
            + (item.desc === '' ? '' : ' (' + item.desc + ')');
        if (item.payment === 'CASH') return 'Внесення готівки'
            + (item.desc === '' ? '' : ' (' + item.desc + ')');
        if (item.payment === 'CARD') return 'Внесення з картки'
            + (item.desc === '' ? '' : ' (' + item.desc + ')');
        if (item.payment === 'WIRED') return 'Внесення по перерахунку'
            + (item.desc === '' ? '' : ' (' + item.desc + ')');
        return item.desc;
    }

    calcChange() {
        this.changeField.nativeElement.value = this.incomeField.nativeElement.value - this.model.sum; 
    }
    
    submit(f, options) {
        f.submitted = true;
        if (!f.form.valid) return false;
        this.model.closeDay = this.model.closeDay || false;
        this.sub = this.patientService.accounting(this.model).subscribe(() => {
            this.alertService.success('На рахунок пацієнта ' + this.data.person.fullName
                + ' внесено ' + this.model.sum + ' грн.');
            options.closeDialogSubject.next();
        });
    }
}
