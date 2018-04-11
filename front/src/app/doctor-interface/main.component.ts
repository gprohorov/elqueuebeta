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
  item: any = {};
  loading = false;
  procedureStarted = false;

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

  load() {
    this.loading = true;
    this.sub = this.service.getDoctorPatient(6).subscribe(data => { this.item = data; this.loading = false; });
  }
  
  startProcedure() {
    this.sub = this.service.startProcedure(this.item.person.id, this.item.id).subscribe(data => { 
      this.alertService.success('Процедуру розпочато.'); 
      this.procedureStarted = true;
      this.load();
    });
  }
  
  cancelProcedure() {
    this.sub = this.service.cancelProcedure(this.item.person.id, this.item.id).subscribe(data => { 
      this.alertService.success('Процедуру скасовано.'); 
      this.procedureStarted = false;
      this.load();
    });
  }
  
  executeProcedure() {
    this.sub = this.service.executeProcedure(this.item.person.id, this.item.id).subscribe(data => { 
      this.alertService.success('Процедуру завершено.'); 
      this.procedureStarted = false;
      this.load();
    });
  }

}
