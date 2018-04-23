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
  subTemp: Subscription;
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

  updateActivity(id: number, value: string) {
    this.subTemp = this.service.updateActivity(id, value).subscribe(data => {});
  }

  updateStatus(id: number, value: string) {
    this.subTemp = this.service.updateStatus(id, value).subscribe(data => {});
  }

  updateBalance(id: number, value: string) {
    this.subTemp = this.service.updateBalance(id, value).subscribe(data => {});
  }

  getTimeDiffClass(v: number) {
    return 'badge badge-pill badge-' + (v > 60 ? 'danger' : v > 30 ? 'success' : 'primary');
  }
  
  load(search: string = '') {
    this.loading = true;
    this.sub = this.service.getAll().subscribe(data => { this.items = data; this.loading = false; });
  }

}
