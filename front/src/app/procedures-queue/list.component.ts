import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { NgxMasonryOptions } from '../_helpers/index';
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
  Statuses = Statuses;
  StatusesArr = StatusesArr;
  Activity = Activity;
  ActivityArr = ActivityArr;
  updateMasonryLayout = false;

  public myOptions: NgxMasonryOptions = {
    transitionDuration: '0.2s',
    columnWidth: 250,
    fitWidth: true,
    horizontalOrder: true,
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
  }

  getFullName(item: Person) {
    return [item.lastName, item.firstName, item.patronymic].join(' ');
  }

  getProgress(list: any[]) {
    let executed = 0;
    list.forEach(function(item) { if (item.executed) executed++; });
    return executed + '/' + list.length;
  }

  getTimeDiffClass(v: number) {
    return v > 60 ? 'text-danger' : v > 30 ? 'text-success' : 'text-primary';
  }

  toggleGroup(item: any) {
    item.expanded = !item.expanded;
    this.updateMasonryLayout = true;
  }

  load() {
    this.loading = true;
    this.sub = this.service.getTails().subscribe(data => { this.items = data; this.loading = false; });
  }

}
