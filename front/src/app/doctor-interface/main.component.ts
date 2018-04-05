import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription'; 

import { Person } from '../_models/index';
import { PatientsQueueService, AlertService } from '../_services/index';

@Component({
  moduleId: module.id,
  templateUrl: './main.component.html'
})
export class DoctorInterfaceComponent implements OnInit {

  sub: Subscription;
  items: any[] = [];
  loading = false;
  doctorAvail = false;

  constructor(private service: PatientsQueueService, private alertService: AlertService) {
  }

  ngOnInit() {
    this.load();
  }
  
  ngOnDestroy() {
    this.sub.unsubscribe();
  } 
    
  getFullName(item: Person) {
    return [item.lastName, item.firstName, item.patronymic].join(' ');
  }

  showAlert(item: Person) {
    alert(this.getFullName(item));
  }
  
  load() {
    this.loading = true;
    this.sub = this.service.getAll().subscribe(data => { this.items = data; this.loading = false; });
  }

}
