import { Component, OnInit } from '@angular/core';
import { Person } from '../_models/index';
import { PersonService } from '../_services/index';

@Component({
  moduleId: module.id,
  templateUrl: './persons.component.html',
  styleUrls: ['./persons.component.css']
})
export class PersonsComponent implements OnInit {

  persons: Person[] = [];

  constructor(private personService: PersonService) {
  }

  ngOnInit() {
      this.loadAllPersons();
  }

  deletePerson(id: number, name: string) {
      if (confirm('Удалить персону "' + name + '" ?')) this.personService.deletePerson(id).subscribe(() => { this.loadAllPersons() });
  }

  private loadAllPersons() {
      this.personService.getAll().subscribe(persons => { this.persons = persons; });
  }

}
