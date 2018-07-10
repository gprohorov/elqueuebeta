import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { Procedure } from '../_models/index';
import { ProcedureService, AlertService } from '../_services/index';
import { Status } from '../_storage/index';

@Component({
  templateUrl: './list.component.html'
})
export class ProcedureListComponent implements OnInit {

  sub: Subscription;
  items: Procedure[] = [];
  loading = false;
  rows = [];
  Status = Status;
  Statuses = Object.keys(Status);

  constructor(private service: ProcedureService, private alertService: AlertService) {
  }

  ngOnInit() {
    this.load();
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  load(search: string = '') {
    this.loading = true;
    this.sub = this.service.getAll(search).subscribe(data => { this.items = data; this.loading = false; });
  }
}
