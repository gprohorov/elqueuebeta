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
  
  getTimeDiffClass(date) {
    var diff = this.getTimeDiff(date);
    console.log(diff);
    if (diff > (60*60*1000)) return 'text-danger';
    if (diff > (30*60*1000)) return 'text-success';
    return 'text-primary';
  }
   
  getTimeDiff(date) {
    var d1 = new Date().getTime();
    var d2 = new Date(date).getTime();
    var diff = Math.abs(d1)-Math.abs(d2)-(2*60*60*1000);
    return diff;
  }
  
  load(search: string = '') {
    this.loading = true;
    this.sub = this.service.getAll(search).subscribe(data => { this.items = data; this.loading = false; });
  }

}
