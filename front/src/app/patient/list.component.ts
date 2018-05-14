import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { Patient } from '../_models/index';
import { AlertService, PatientService, PatientSearchCriteria } from '../_services/index';

@Component({
    templateUrl: './list.component.html'
})
export class PatientListComponent implements OnInit {

    sub: Subscription;
    items: Patient[];
    loading = false;
    rows = [];

    constructor(
        private service: PatientService,
        private alertService: AlertService
    ) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    onSorted($event: PatientSearchCriteria) {
        this.items = this.service.sortBy($event, this.items);
    }

    delete(id: string, name: string) {
        if (confirm('Видалити "' + name + '" ?')) {
            this.service.delete(id).subscribe(() => { this.load(); });
        }
    }

    toPatientToday(id: string) {
        this.service.toPatientToday(id).subscribe(() => {
            this.alertService.success('Операція пройшла успішно');
        });
    }

    getFullName(item: Patient) {
        return [item.person.lastName, item.person.firstName, item.person.patronymic].join(' ');
    }

    load(search: string = '') {
        this.loading = true;
        this.sub = this.service.getAll(search).subscribe(data => {
            this.items = this.service.sortBy({ sortColumn: 'lastName', sortDirection: 'asc' }, data);
            this.loading = false;
        });
    }

}
