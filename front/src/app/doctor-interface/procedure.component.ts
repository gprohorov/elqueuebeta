import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription'; 
import { ActivatedRoute, Router } from '@angular/router';

import { Person } from '../_models/index';
import { PatientsQueueService, AlertService } from '../_services/index';

@Component({
  moduleId: module.id,
  templateUrl: './procedure.component.html'
})
export class DoctorInterfaceProcedureComponent implements OnInit, OnDestroy {
  
  sub: Subscription;
  subTemp: Subscription;
  item: any;
  loading = false;
  procedureStarted = false;

  constructor(private service: PatientsQueueService, private alertService: AlertService, private route: ActivatedRoute, private router: Router) {}
  
  ngOnInit() {
    this.sub = this.route
      .queryParams
      .subscribe(params => {
        this.item = JSON.parse(params.item);
      });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
    if (this.subTemp) this.subTemp.unsubscribe();
  }
    
  getFullName(item: Person) {
    return [item.lastName, item.firstName, item.patronymic].join(' ');
  }

  startProcedure() {
    this.subTemp = this.service.updateActivity(this.item.id, 'ON_PROCEDURE').subscribe(data => { 
      this.alertService.success('Процедуру розпочато', true); 
      this.procedureStarted = true;
    });
  }
  
  executeProcedure() {
    this.subTemp = this.service.executeProcedure(this.item.id, 6).subscribe(data => { 
      this.alertService.success('Процедуру завершено', true); 
      this.router.navigate(['doctor-interface']);
    });
  }
  
  cancelProcedure() {
    this.subTemp = this.service.updateActivity(this.item.id, 'ACTIVE').subscribe(data => { 
      this.alertService.success('Процедуру скасовано', true); 
      this.router.navigate(['doctor-interface']);
    });
  }
  
}
