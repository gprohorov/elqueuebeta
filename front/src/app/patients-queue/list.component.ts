import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription'; 

import { Person } from '../_models/index';
import { Statuses, StatusesArr, Activity, ActivityArr } from '../_storage/index';
import { PatientsQueueService, AlertService } from '../_services/index';

@Component({
  moduleId: module.id,
  templateUrl: './list.component.html'
})
export class PatientsQueueListComponent implements OnInit {

  sub: Subscription;
  items: any[] = [];
  loading = false;
  rows = [];
  Statuses = Statuses;
  StatusesArr = StatusesArr;
  Activity = Activity;
  ActivityArr = ActivityArr;

  constructor(private service: PatientsQueueService, private alertService: AlertService) {
  }

  ngOnInit() {
    this.load();
  }
  
  ngOnDestroy() {
    this.sub.unsubscribe();
  } 
  
  delete(id: number, name: string) {
    if (confirm('Видалити "' + name + '" ?')) this.service.delete(id).subscribe(() => { this.load() });
  }
  
  getFullName(item: Person) {
    return [item.lastName, item.firstName, item.patronymic].join(' ');
  }
  
  getProgress(list: any[]) {
    var executed = 0;
    list.forEach(function(item) {
      if (item.executed) executed++;
    });
    return executed + '/' + list.length;
  }

  getTimeDiffClass(v: number) {
    return v > 60 ? 'text-danger' : v > 30 ? 'text-success' : 'text-primary';
  }
  
  load(search: string = '') {
    this.loading = true;
    this.sub = this.service.getAll().subscribe(data => { this.items = data; this.loading = false; });
  }

}
