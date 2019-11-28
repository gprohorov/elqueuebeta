import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { Doctor, Procedure } from '../_models/index';
import { DoctorService, ProcedureService } from '../_services/index';

@Component({
    templateUrl: './list.component.html'
})
export class DoctorListComponent implements OnInit, OnDestroy {

    sub: Subscription;
    subProcedures: Subscription;
    subDelete: Subscription;
    items: Doctor[] = [];
    procedures: Procedure[] = [];
    loading = false;
    hideNotActive = true;
    rows = [];

    constructor(
        private service: DoctorService,
        private procedureService: ProcedureService
    ) { }

    ngOnInit() {
        this.loadProcedures();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subProcedures) this.subProcedures.unsubscribe();
        if (this.subDelete) this.subDelete.unsubscribe();
    }

    delete(id: number, name: string) {
        if (confirm('Видалити "' + name + '" ?')) {
            this.subDelete = this.service.delete(id).subscribe(() => { this.load(); });
        }
    }

    loadProcedures() {
        this.subProcedures = this.procedureService.getAll().subscribe(data => {
            this.procedures = data;
            this.load();
        });
    }

    getProcedures(list) {
        return list.map(id => { 
          let p = this.procedures.find(x => x.id === id);
          return (p && p.name) ? p.name : '---'; 
        }).join(', ');
    }

    load(search: string = '') {
        this.loading = true;
        this.sub = this.service.getAll(search).subscribe(data => {
            if (this.hideNotActive) {
              data = data.filter(x => x.active === true);
            }
            this.items = data;
            this.loading = false;
        });
    }
}
