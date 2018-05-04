import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { Procedure } from '../_models/index';
import { ProcedureService, AlertService } from '../_services/index';
import { Statuses, StatusesArr } from '../_storage/index';

@Component({
  moduleId: module.id,
  templateUrl: './list.component.html'
})
export class ProcedureListComponent implements OnInit {

  sub: Subscription;
  items: Procedure[] = [];
  loading = false;
  rows = [];
  Statuses = Statuses;
  StatusesArr = StatusesArr;

  constructor(private service: ProcedureService, private alertService: AlertService) {
  }

  ngOnInit() {
    this.load();
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  delete(id: number, name: string) {
    if (confirm('Видалити "' + name + '" ?')) this.service.delete(id).subscribe(() => { this.load(); });
  }

  load(search: string = '') {
    this.loading = true;
    this.sub = this.service.getAll(search).subscribe(data => { this.items = data; this.loading = false; });
  }
}
