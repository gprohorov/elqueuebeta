import { Component, OnInit } from '@angular/core';
import { Person } from '../_models/index';
import { PersonService, AlertService } from '../_services/index';
import { Subscription } from 'rxjs/Subscription'; 

@Component({
  moduleId: module.id,
  templateUrl: './persons.component.html',
  styleUrls: ['./persons.component.css']
})
export class PersonsComponent implements OnInit {

  personsSub: Subscription;
  persons: Person[] = [];
  loading = false;
  rows = [];

  constructor(private personService: PersonService, private alertService: AlertService) {
  }

  ngOnInit() {
    this.loadAllPersons();
  }
  
  ngOnDestroy() {
    this.personsSub.unsubscribe();
  } 
  
  deletePerson(id: number, name: string) {
    if (confirm('Видалити персону "' + name + '" ?')) this.personService.deletePerson(id).subscribe(() => { this.loadAllPersons() });
  }

  getFullName(person: Person) {
    return [person.lastName, person.firstName, person.patronymic].join(' ');
  }
  
  loadAllPersons(search: string = '') {
    this.loading = true;
    this.personsSub = this.personService.getAll(search).subscribe(persons => { this.persons = persons; this.loading = false; });
  }

}
