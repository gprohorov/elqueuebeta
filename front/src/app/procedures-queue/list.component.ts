import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription'; 

import { Person } from '../_models/index';
import { Statuses, StatusesArr, Activity, ActivityArr } from '../_storage/index';
import { ProcedureService, PatientsQueueService, AlertService } from '../_services/index';

@Component({
  moduleId: module.id,
  templateUrl: './list.component.html'
})
export class ProceduresQueueListComponent implements OnInit {

  subProcedures: Subscription;
  subQueue: Subscription[] = [];
  items: any[] = [];
  loading = false;
  rows = [];
  Statuses = Statuses;
  StatusesArr = StatusesArr;
  Activity = Activity;
  ActivityArr = ActivityArr;

  constructor(
    private alertService: AlertService,
    private service: ProcedureService, 
    private servicePatients: PatientsQueueService
    ) {
  }

  ngOnInit() {
    this.load();
  }
  
  ngOnDestroy() {
    this.subProcedures.unsubscribe();
    this.subQueue.forEach(function(item) {
      item.unsubscribe();
    });
  } 
  
  delete(id: number, name: string) {
    // if (confirm('Видалити "' + name + '" ?')) this.service.delete(id).subscribe(() => { this.load() });
  }
  
  getFullName(item: Person) {
    return [item.lastName, item.firstName, item.patronymic].join(' ');
  }

  getProgress(list: any[]) {
    var executed = 0;
    list.forEach(function(item) { if (item.executed) executed++; });
    return executed + '/' + list.length;
  }

  getTimeDiffClass(v: number) {
    return v > 60 ? 'text-danger' : v > 30 ? 'text-success' : 'text-primary';
  }
   
  load() {
    this.loading = true;
<<<<<<< HEAD
    this.subProcedures = this.service.getAll().subscribe(data => { this.items = data; this.loading = false; this.loadAll(); });
=======
    this.subProcedures = this.service.getAll().subscribe(data => { this.items = data; this.loading = false; this.loadAll(););
>>>>>>> fe794ee8489e50610b0fb2223da509a13023337c
  }
  
  loadAll() {
    this.items.forEach(function(item) {
      this.loading = true;
      this.subQueue.push(this.servicePatients.getAllByProcedure(item.id).subscribe(data => { item.queue = data; this.loading = false; }));
    }, this);
  }

}
