import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription'; 

import { Doctor } from '../_models/index';
import { DoctorService, AlertService } from '../_services/index';

@Component({
  moduleId: module.id,
  templateUrl: './list.component.html'
})
export class DoctorListComponent implements OnInit {

  sub: Subscription;
  items: Doctor[] = [];
  loading = false;
  rows = [];

  constructor(private service: DoctorService, private alertService: AlertService) {
  }

  ngOnInit() {
    this.load();
  }
  
  ngOnDestroy() {
    this.sub.unsubscribe();
  } 
  
  getFullName(item: Doctor) {
    return [item.lastName, item.firstName, item.patronymic].join(' ');
  }
  
  load(search: string = '') {
    this.loading = true;
    this.sub = this.service.getAll(search).subscribe(data => { this.items = data; this.loading = false; });
  }

}
