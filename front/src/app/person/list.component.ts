import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { Person } from '../_models/index';
import { AlertService, PersonService, PersonSearchCriteria } from '../_services/index';

@Component({
  moduleId: module.id,
  templateUrl: './list.component.html'
})
export class PersonListComponent implements OnInit {

  sub: Subscription;
  items: Person[];
  loading = false;
  rows = [];

  constructor(private service: PersonService, private alertService: AlertService) {
  }

  ngOnInit() {
    this.load();
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  onSorted($event: PersonSearchCriteria) {
    this.items = this.service.sortBy($event, this.items);
  }

  delete(id: number, name: string) {
    if (confirm('Видалити "' + name + '" ?')) this.service.delete(id).subscribe(() => { this.load(); });
  }

  toPatientToday(id: number) {
    this.service.toPatientToday(id).subscribe(() => { this.alertService.success('Операція пройшла успішно'); });
  }

  getFullName(item: Person) {
    return [item.lastName, item.firstName, item.patronymic].join(' ');
  }

  load(search: string = '') {
    this.loading = true;
    this.sub = this.service.getAll(search).subscribe(data => { 
      this.items = this.service.sortBy({sortColumn: 'lastName', sortDirection: 'asc'}, data); 
      this.loading = false; 
    });
  }

}
