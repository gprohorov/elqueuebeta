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
    cash = 0;
    change = 0;
    balanceAfter: number;

    @ViewChild('incomeField') incomeField: ElementRef;
    @ViewChild('changeField') changeField: ElementRef;
    @ViewChild('f') myForm;
    constructor(private alertService: AlertService, private patientService: PatientService) {}

    dialogInit(_reference: ComponentRef<IModalDialog>, options: Partial<IModalDialogOptions<any>>) {
        this.data = options.data;
        this.model.patientId = this.data.id;
        this.model.sum = this.data.balance < 0 ? this.data.balance * -1 : 0;

        // Apply discount
        if (this.data.balance < 0) {
            this.model.discount = Math.ceil((this.data.balance*-1 / 100) * this.data.discount);
            this.model.sum = this.data.balance*-1 - this.model.discount;
        } else {
            this.model.sum = this.data.balance*-1;
        }
        this.calcBalanceAfter();

        options.actionButtons = [{
            text: 'Виписка',
            buttonClass: 'btn btn-info mr-5',
            onAction: () => {
                setTimeout(() => { window.open('/#/receipt/' + this.model.patientId, '_blank'); }, 0);
            }
        }, {
            text: 'Внести',
            buttonClass: 'insbutton btn btn-primary',
            onAction: () => {
                document.getElementsByClassName('insbutton')[0]['disabled'] = true;
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
                || item.payment === 'CHECK'
                || item.payment === 'DODATOK'
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
        if (item.payment === 'CHECK') return 'Внесення чеком'
            + (item.desc === '' ? '' : ' (' + item.desc + ')');
        if (item.payment === 'DODATOK') return 'Внесення через додаток'
            + (item.desc === '' ? '' : ' (' + item.desc + ')');
        return item.desc;
    }

    calcChange() {
        this.change = this.cash - this.model.sum;
        this.calcBalanceAfter();
    }

    calcBalanceAfter() {
        this.balanceAfter = this.data.balance + this.model.sum + this.model.discount;
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
