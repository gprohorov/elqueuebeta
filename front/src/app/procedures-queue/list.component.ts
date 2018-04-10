import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription'; 

import { NgxMasonryOptions } from 'ng5-masonry';
import { Person } from '../_models/index';
import { Statuses, StatusesArr, Activity, ActivityArr } from '../_storage/index';
import { PatientsQueueService, AlertService } from '../_services/index';

@Component({
  moduleId: module.id,
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ProceduresQueueListComponent implements OnInit {

  sub: Subscription;
  items: any[] = [];
  loading = false;
  rows = [];
  Statuses = Statuses;
  StatusesArr = StatusesArr;
  Activity = Activity;
  ActivityArr = ActivityArr;

  public myOptions: NgxMasonryOptions = {
    //itemSelector: '.masonry-item',
    transitionDuration: '0.2s',
    columnWidth: 250,
    fitWidth: true,
    gutter: 20
  };
  
  constructor(
    private alertService: AlertService,
    private service: PatientsQueueService
    ) {
  }

  ngOnInit() {
    this.load();
  }
  
  ngOnDestroy() {
    this.sub.unsubscribe();
    // this.subQueue.forEach(function(item) {
      // item.unsubscribe();
    // });
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
    this.sub = this.service.getTails().subscribe(data => { this.items = data; this.loading = false; });
  }
  
  /*
  loadAll() {
    this.items.forEach(function(item) {
      this.loading = true;
      this.subQueue.push(this.servicePatients.getAllByProcedure(item.id).subscribe(data => { item.queue = data; this.loading = false; }));
    }, this);
  }
  */
}
