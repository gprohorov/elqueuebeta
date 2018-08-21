import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Subscription } from 'rxjs/Subscription';
import { Subject } from 'rxjs/Subject';
import { ModalDialogService } from 'ngx-modal-dialog';
import * as moment from 'moment';

import { Patient } from '../_models/index';
import { Status, Activity } from '../_storage/index';
import { AlertService, PatientsQueueService } from '../_services/index';

import { PatientIncomeModalComponent } from '../patient/income.modal.component';
import { PatientAssignProcedureModalComponent } from '../patient/assign-procedure.modal.component';
import { PatientAssignProceduresOnDateModalComponent } from '../patient/assign-procedures-on-date.modal.component';

@Component({
    templateUrl: './list.component.html'
})
export class PatientsQueueListComponent implements OnInit, OnDestroy {

    loading = false;
    
    sub: Subscription;
    subTemp: Subscription;
    items: any[] = [];
    totalPatients: number;
    activePatients: number;
    notActivePatients: number;
    date: string = (new Date()).toISOString().split('T')[0]; 
    filters: any = 'all'; // possible values: 'all', 'active', 'notactive'
    Status = Status;
    Statuses = Object.keys(Status);
    Activity = Activity;
    Activities = Object.keys(Activity);

    constructor(
        private viewRef: ViewContainerRef,
        private alertService: AlertService,
        private modalService: ModalDialogService,
        private service: PatientsQueueService
    ) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subTemp) this.subTemp.unsubscribe();
    }

    scrollToTop() {
        window.scrollTo(0,0);
    }
    
    scrollToRow(itemId) {
        let item = this.items.find(x => { return x.id == itemId });
        if (item) {
            item.expanded = true;
            document.getElementById('row-' + item.id).scrollIntoView();
        }
    }

    getProgress(list: any[]) {
        let executed = 0, total = 0;
        list.forEach(function (item) {
            if (item.activity == 'EXECUTED') executed++;
            if (item.activity != 'CANCELED') total++;
        });
        return executed + '/' + total;
    }

    showAssignProcedurePopup(item: any) {
        this.modalService.openDialog(this.viewRef, {
            title: 'Пацієнт: ' + item.person.fullName,
            childComponent: PatientAssignProcedureModalComponent,
            data: { patientId: item.id, patientName: item.person.fullName }
        });
        let alertSub = this.alertService.subject.subscribe(() => { this.load(item.id); alertSub.unsubscribe(); });
    }

    showIncomePopup(item: any) {
        this.modalService.openDialog(this.viewRef, {
            title: 'Пацієнт: ' + item.person.fullName,
            childComponent: PatientIncomeModalComponent,
            data: item
        });
        let alertSub = this.alertService.subject.subscribe(() => { this.load(item.id); alertSub.unsubscribe(); });
    }
    
    showAssignProceduresOnDatePopup(item: any) {
        this.modalService.openDialog(this.viewRef, {
            title: 'Пацієнт: ' + item.person.fullName,
            childComponent: PatientAssignProceduresOnDateModalComponent,
            data: { itemId: item.id, itemName: item.person.fullName }
        });
        let alertSub = this.alertService.subject.subscribe(() => { this.load(item.id); alertSub.unsubscribe(); });
    }
    
    updateActivity(id: string, value: string, item: any) {
        if (value === 'CANCELED' && !confirm('Встановити процедурі "' + Activity[value].text + '" ?')) return false;
        this.subTemp = this.service.updateActivity(id, value).subscribe(data => {
            this.load(item.id);
        });
    }

    updateActivityAll(item: any, value: string) {
        if (confirm('Встановити всім процедурам "' + Activity[value].text + '" ?')) {
            this.subTemp = this.service.updateActivityAll(item.id, value).subscribe(data => {
                this.load(item.id);
            });
        }
    }
    
    updateOutOfTurn(id: string, value: boolean, item: any) {
        this.subTemp = this.service.updateOutOfTurn(id, value).subscribe(data => {
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
    
    changeDay(days: number) {
        let date = new Date(this.date);
        date.setDate(date.getDate() + days);
        this.date = date.toISOString().split('T')[0];
        this.load();
    }
    
    load(itemId: string = null) {
        this.loading = true;
        this.sub = this.service.getAll(this.date).subscribe(data => {
            data.forEach(x => { x.fullName = x.person.fullName });
            this.items = data.sort(sort_by('appointed', 'fullName'));
            this.totalPatients = data.length;
            this.activePatients = data.filter(x => x.activity == 'ACTIVE' || x.activity == 'ON_PROCEDURE').length;
            this.notActivePatients = this.totalPatients - this.activePatients;
            this.loading = false;
            if (itemId) this.scrollToRow(itemId);
        });
    }

}

var sort_by;

(function() {
    // utility functions
    var default_cmp = function(a, b) {
            if (a == b) return 0;
            return a < b ? -1 : 1;
        },
        getCmpFunc = function(primer, reverse) {
            var dfc = default_cmp, // closer in scope
                cmp = default_cmp;
            if (primer) {
                cmp = function(a, b) {
                    return dfc(primer(a), primer(b));
                };
            }
            if (reverse) {
                return function(a, b) {
                    return -1 * cmp(a, b);
                };
            }
            return cmp;
        };

    // actual implementation
    sort_by = function() {
        var fields = [],
            n_fields = arguments.length,
            field, name, reverse, cmp;

        // preprocess sorting options
        for (var i = 0; i < n_fields; i++) {
            field = arguments[i];
            if (typeof field === 'string') {
                name = field;
                cmp = default_cmp;
            }
            else {
                name = field.name;
                cmp = getCmpFunc(field.primer, field.reverse);
            }
            fields.push({
                name: name,
                cmp: cmp
            });
        }

        // final comparison function
        return function(A, B) {
            var a, b, name, result;
            for (var i = 0; i < n_fields; i++) {
                result = 0;
                field = fields[i];
                name = field.name;

                result = field.cmp(A[name], B[name]);
                if (result !== 0) break;
            }
            return result;
        }
    }
}());