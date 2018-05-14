import { Component, ViewChild, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { Patient, Procedure } from '../_models/index';
import { AlertService, ProcedureService, PatientService, PatientSearchCriteria } from '../_services/index';

@Component({
    templateUrl: './list.component.html'
})
export class PatientListComponent implements OnInit {
    
    sub: Subscription;
    subProcedures: Subscription;
    items: Patient[];
    procedures: Procedure[];
    loading = false;
    rows = [];

    constructor(
        private patientService: PatientService,
        private procedureService: ProcedureService,
        private alertService: AlertService
    ) { }

    ngOnInit() {
        this.load();
        this.subProcedures = this.procedureService.getAll().subscribe(data => {
            this.procedures = data;
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
        this.subProcedures.unsubscribe();
    }

    onSorted($event: PatientSearchCriteria) {
        this.items = this.patientService.sortBy($event, this.items);
    }

    delete(id: string, name: string) {
        if (confirm('Видалити "' + name + '" ?')) {
            this.patientService.delete(id).subscribe(() => { this.load(); });
        }
    }

    showAssignProcedurePopup(patientId: string) {
        const patient = this.items.find(x => x.id == patientId);
        console.log(this.modal);
    }
    
    assignProcedure(patientId: string, procedureId: number, date: string) {
        this.patientService.assignProcedure(patientId, procedureId, date).subscribe(() => {
            this.alertService.success('Операція пройшла успішно');
        });
    }

    getFullName(item: Patient) {
        return [item.person.lastName, item.person.firstName, item.person.patronymic].join(' ');
    }

    load(search: string = '') {
        this.loading = true;
        this.sub = this.patientService.getAll(search).subscribe(data => {
            this.items = this.patientService.sortBy({ sortColumn: 'lastName', sortDirection: 'asc' }, data);
            this.loading = false;
        });
    }

}
