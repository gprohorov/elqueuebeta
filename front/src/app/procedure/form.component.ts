import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Procedure } from '../_models/index';
import { AlertService, ProcedureService } from '../_services/index';
import { Status, ProcedureType } from '../_storage/index';

@Component({
    templateUrl: './form.component.html'
})

export class ProcedureFormComponent implements OnInit, OnDestroy {

    loading = false;

    sub: Subscription;
    subProcedures: Subscription;
    procedures: Procedure[];
    Status = Status;
    Statuses = Object.keys(Status);
    ProcedureType = ProcedureType;
    ProcedureTypes = Object.keys(ProcedureType);
    model: Procedure = new Procedure();

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: ProcedureService
    ) { }

    ngOnInit() {
        this.loadProcedures();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subProcedures) this.subProcedures.unsubscribe();
    }

    loadProcedures() {
        this.subProcedures = this.service.getAll().subscribe(
            data => {
                this.loading = false;
                this.procedures = data.map(x => { return { name: x.name, value: x.id, checked: false }; } );
                const id = parseInt(this.route.snapshot.paramMap.get('id'));
                if (id > 0) this.load(+id);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    initProcedures() {
        /*
        this.model.procedureIds.forEach(id => {
            let p = this.procedures.find(x => x.value == id);
            if (p) p.checked = true;
        });
        */
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

    submit() {
        this.loading = true;
        this.service.save(this.model).subscribe(
            data => {
                this.alertService.success('Процедуру збережено.', true);
                this.router.navigate(['procedures']);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}
