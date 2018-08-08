import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { Procedure } from '../_models/index';
import { AlertService, ProcedureService } from '../_services/index';
import { Status } from '../_storage/index';

@Component({
  templateUrl: './list.component.html'
})
export class ProcedureListComponent implements OnInit {

  sub: Subscription;
  items: Procedure[] = [];
  loading = false;
  Status = Status;
  Statuses = Object.keys(Status);

  constructor(
      private alertService: AlertService,
      private service: ProcedureService
  ) { }

  ngOnInit() {
    this.load();
  }

  ngOnDestroy() {
    if (this.sub) this.sub.unsubscribe();
  }
  
  load() {
    this.loading = true;
    this.sub = this.service.getAll().subscribe(data => { this.items = data; this.loading = false; });
  }
}
