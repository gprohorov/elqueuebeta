import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Doctor, Procedure } from '../_models/index';
import { AlertService, DoctorService, ProcedureService } from '../_services/index';

@Component({
    templateUrl: './form.component.html'
})
export class DoctorFormComponent implements OnInit, OnDestroy {

    loading = false;

    model: Doctor = new Doctor();
    sub: Subscription;
    procedures: any[];
    subProcedures: Subscription;

    constructor(
        private alertService: AlertService,
        private route: ActivatedRoute,
        private router: Router,
        private service: DoctorService,
        private procedureService: ProcedureService
    ) { }

    ngOnInit() {
        this.loadProcedures();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subProcedures) this.subProcedures.unsubscribe();
    }

    loadProcedures() {
        this.subProcedures = this.procedureService.getAll().subscribe(
            data => {
                this.loading = false;
                this.procedures = data.map(x => { return { name: x.name, value: x.id, checked: false }; } );
                const id = parseInt(this.route.snapshot.paramMap.get('id'));
                if (id > 0) this.load(id);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    initProcedures() {
        this.model.procedureIds.forEach(id => {
            let p = this.procedures.find(x => x.value == id);
            if (p) p.checked = true;
        });
    }

    load(id: number) {
        this.loading = true;
        this.sub = this.service.get(id).subscribe(
            data => {
                this.model = data;
                this.initProcedures();
                this.loading = false;
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    submit(form) {
        this.loading = true;
        this.model.procedureIds = this.procedures.filter(x => x.checked).map(x => x.value);
        this.service.update(this.model).subscribe(
            data => {
                this.alertService.success('Операція пройшла успішно', true);
                this.router.navigate(['doctors']);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}
