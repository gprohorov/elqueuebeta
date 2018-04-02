import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription'; 

import { Procedure } from '../_models/index';
import { ProcedureService, AlertService } from '../_services/index';

@Component({
  moduleId: module.id,
  templateUrl: './list.component.html'
})
export class ProcedureListComponent implements OnInit {

  sub: Subscription;
  items: Procedure[] = [];
  loading = false;
  rows = [];

  constructor(private service: ProcedureService, private alertService: AlertService) {
  }

  ngOnInit() {
    this.sub = this.service.getAll().subscribe(data => { this.items = data; this.loading = false; });
  }
  
  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
