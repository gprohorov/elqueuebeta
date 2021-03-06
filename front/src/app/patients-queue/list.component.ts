﻿import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';
import * as moment from 'moment';

import { Status, Activity } from '../_storage/index';
import { PatientsQueueService, AuthService } from '../_services/index';

import { PatientIncomeModalComponent } from '../patient/income.modal.component';
import { PatientAssignProcedureModalComponent } from '../patient/assign-procedure.modal.component';
import { PatientAssignProceduresOnDateModalComponent } from '../patient/assign-procedures-on-date.modal.component';
import { PatientAssignHotelModalComponent } from '../patient/assign-hotel.modal.component';
import { CreatePatientModalComponent } from '../patient/create-patient.modal.component';

@Component({
    templateUrl: './list.component.html'
})
export class PatientsQueueListComponent implements OnInit, OnDestroy {

    loading = false;

    sub: Subscription;
    subTemp: Subscription;
    items: any[] = [];
    appointments: any = [];
    totalPatients: number;
    hotelPatients: number;
    activePatients: number;
    notActivePatients: number;
    date: string = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().slice(0, -14);
    filters: any = 'all'; // possible values: 'all', 'active', 'notactive', 'hotel'
    Status = Status;
    Statuses = Object.keys(Status);
    Activity = Activity;
    Activities = Object.keys(Activity);

    constructor(
        private viewRef: ViewContainerRef,
        private modalService: ModalDialogService,
        private service: PatientsQueueService,
        private authService: AuthService
    ) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subTemp) this.subTemp.unsubscribe();
    }

    scrollToTop() {
        window.scrollTo(0, 0);
    }

    scrollToRow(itemId) {
        const item = this.items.find(x => x.id === itemId);
        if (item) {
            item.expanded = true;
            setTimeout(() => {
                document.getElementById('row-' + item.id).scrollIntoView();
            }, 100);
        }
    }

    getProgress(list: any[]) {
        let executed = 0, total = 0;
        list.forEach(function (item) {
            if (item.activity === 'EXECUTED') executed++;
            if (item.activity !== 'CANCELED') total++;
        });
        return executed + '/' + total;
    }

    showCreatePatientPopup() {
        const options = {
            title: 'Добавити пацієнта',
            childComponent: CreatePatientModalComponent,
            closeDialogSubject: null
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe((itemId) => { this.load(itemId); });
    }

    showAssignProcedurePopup(item: any) {
        const options = {
            title: 'Пацієнт: ' + item.person.fullName,
            childComponent: PatientAssignProcedureModalComponent,
            data: { patientId: item.id, patientName: item.person.fullName },
            closeDialogSubject: null
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(item.id); });
    }

    showIncomePopup(item: any) {
        const options = {
            title: 'Пацієнт: ' + item.person.fullName,
            childComponent: PatientIncomeModalComponent,
            data: item,
            closeDialogSubject: null
        };
        
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => {
            const clientwindow = window.open('/#/home', 'clientwindow') 
            clientwindow.location.href = '/#/check/' + item.id;
            setTimeout(() => { window.open('/#/home', 'clientwindow'); }, 10 * 1000);
            this.load(item.id);
        });
        
        const clientwindow = window.open('/#/home', 'clientwindow'); 
        clientwindow.location.href = '/#/check/' + item.id;
    }

    showAssignProceduresOnDatePopup(item: any) {
        const options = {
            title: 'Пацієнт: ' + item.person.fullName,
            childComponent: PatientAssignProceduresOnDateModalComponent,
            data: { patientId: item.id, patientName: item.person.fullName },
            closeDialogSubject: null
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(item.id); });
    }

    showAssignHotelPopup(item: any) {
        const options = {
            title: 'Пацієнт: ' + item.person.fullName,
            childComponent: PatientAssignHotelModalComponent,
            data: { patientId: item.id, patientName: item.person.fullName },
            closeDialogSubject: null
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(item.id); });
    }

    updateActivity(id: string, value: string, item: any) {
        if (value === 'CANCELED' && !confirm('Встановити процедурі "' + Activity[value].text + '" ?')) return false;
        this.subTemp = this.service.updateActivity(id, value).subscribe(() => {
            this.load(item.id);
        });
    }

    updateActivityAll(item: any, value: string) {
        if (!confirm('Встановити всім процедурам "' + Activity[value].text + '" ?')) return;
        this.loading = true;
        this.subTemp = this.service.updateActivityAll(item.id, value).subscribe(() => {
            this.load(item.id);
        });
    }

    updateOutOfTurn(id: string, value: boolean, item: any) {
        this.subTemp = this.service.updateOutOfTurn(id, value).subscribe(() => {
            this.load(item.id);
        });
    }

    getTimeDiffClass(v: number) {
        return (v > 60 ? 'danger' : v > 30 ? 'success' : 'primary');
    }

    getTalonTitle(talon: any) {
        let out = Activity[talon.activity].text, lastDate;
        if (talon.last && talon.last.date) {
            lastDate = moment(talon.last.date, 'YYYY-MM-DD');
            out += '. Останній раз проводилося ' + lastDate.format('DD.MM.YYYY') + ', ' + talon.last.zones + ' зон';
            if (talon.last.doctor) out += ', ' + talon.last.doctor.fullName;
        }
        return out;
    }

    getTalonInfo(talon: any) {
        let out = '', start, end, diff, lastDate;
        if (talon.last && talon.last.date) {
            lastDate = moment(talon.last.date, 'YYYY-MM-DD');
            out += lastDate.format('DD.MM.YYYY') + ' ';
        }
        if (talon.start) {
            start = moment(talon.start, 'YYYY-MM-DDTHH:mm:ss.SSS');
            out += start.format('HH:mm');
        }
        if (talon.start && talon.executionTime) {
            end = moment(talon.executionTime, 'YYYY-MM-DDTHH:mm:ss.SSS');
            diff = Math.floor(moment.duration(end.diff(start)).asMinutes());
            out += ' - ' + end.format('HH:mm') + ' (' + diff + ' хв.)';
        } else if (talon.start) {
            end = moment({});
            diff = Math.floor(moment.duration(end.diff(start)).asMinutes());
            out += ' (' + diff + ' хв.)';
        }
        if (talon.doctor) {
            out += ', ' + talon.doctor.fullName + ' (зон: ' + talon.zones + ')';
        }
        return out;
    }

    isHiddenRow(item: any) {
        return ((item.activity !== 'ACTIVE'
              && item.activity !== 'ON_PROCEDURE'
              && item.activity !== 'INVITED'
              && item.activity !== 'STUCK'
            ) && this.filters === 'active')
            || ((item.activity === 'ACTIVE'
              || item.activity === 'ON_PROCEDURE'
              || item.activity === 'INVITED'
              || item.activity === 'STUCK'
            ) && this.filters === 'notactive')
            || (item.hotel === false && this.filters === 'hotel');
    }

    changeDay(days: number) {
        const date = new Date(this.date);
        date.setDate(date.getDate() + days);
        this.date = date.toISOString().split('T')[0];
        this.load();
    }

    isSuperadmin() {
        return this.authService.isSuperadmin();
    }

    load(itemId: string = null) {
        this.loading = true;
        this.sub = this.service.getAll(this.date).subscribe(data => {
            data.forEach(x => { x.fullName = x.person.fullName; });
            this.items = data.sort(sort_by('appointed', 'fullName'));
            this.appointments = [];
            const appointmentsObj = {};
            let i = 0;
            data.forEach(x => {
                if (appointmentsObj[x.appointed] === undefined) appointmentsObj[x.appointed] = [];
                appointmentsObj[x.appointed].push(i);
                i++;
            });
            // tslint:disable-next-line:forin
            for (const prop in appointmentsObj) {
                this.appointments.push({ appointment: parseInt(prop), items: appointmentsObj[prop] });
            }
            this.totalPatients = data.length;
            this.hotelPatients = data.filter( x => x.hotel ).length;
            this.activePatients = data.filter( x =>
                   x.activity === 'ACTIVE'
                || x.activity === 'ON_PROCEDURE'
                || x.activity === 'INVITED'
                || x.activity === 'STUCK' ).length;
            this.notActivePatients = this.totalPatients - this.activePatients;
            this.loading = false;
            if (itemId) this.scrollToRow(itemId);
        });
    }

}

let sort_by;

(function () {
    // utility functions
    const default_cmp = function (a, b) {
        if (a === b) return 0;
        return a < b ? -1 : 1;
    },
        getCmpFunc = function (primer, reverse) {
            const dfc = default_cmp; // closer in scope
            let cmp = default_cmp;
            if (primer) {
                cmp = function (a, b) {
                    return dfc(primer(a), primer(b));
                };
            }
            if (reverse) {
                return function (a, b) {
                    return -1 * cmp(a, b);
                };
            }
            return cmp;
        };

    // actual implementation
    sort_by = function () {
        const fields = [], n_fields = arguments.length;
        let field, name, cmp;

        // preprocess sorting options
        for (let i = 0; i < n_fields; i++) {
            field = arguments[i];
            if (typeof field === 'string') {
                name = field;
                cmp = default_cmp;
            } else {
                name = field.name;
                cmp = getCmpFunc(field.primer, field.reverse);
            }
            fields.push({
                name: name,
                cmp: cmp
            });
        }

        // final comparison function
        return function (A, B) {
            // tslint:disable-next-line:no-shadowed-variable
            let name = '', result = 0;
            for (let i = 0; i < n_fields; i++) {
                result = 0;
                field = fields[i];
                name = field.name;

                result = field.cmp(A[name], B[name]);
                if (result !== 0) break;
            }
            return result;
        };
    };
}());
