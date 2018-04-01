import { Component, OnInit } from '@angular/core';
import { Person } from '../_models/index';
import { PersonService, AlertService } from '../_services/index';

@Component({
  moduleId: module.id,
  templateUrl: './persons.component.html',
  styleUrls: ['./persons.component.css']
})
export class PersonsComponent implements OnInit {

  persons: Person[] = [];

  constructor(private personService: PersonService, private alertService: AlertService) {
  }

  ngOnInit() {
      this.loadAllPersons();
  }
  
  doSearch() {
    this.alertService.error('На стадії розробки');
  }

  createPerson() {
    this.alertService.error('На стадії розробки');
  }
  
  editPerson(id: number) {
    this.alertService.error('На стадії розробки');
  }

  deletePerson(id: number, name: string) {
      if (confirm('Видалити персону "' + name + '" ?')) this.personService.deletePerson(id).subscribe(() => { this.loadAllPersons() });
  }

  getFullName(person: Person) {
    return [person.firstName, person.patronymic, person.lastName].join(' ');
  }
  
  private loadAllPersons() {
      this.personService.getAll().subscribe(persons => { this.persons = persons; });
  }

}
