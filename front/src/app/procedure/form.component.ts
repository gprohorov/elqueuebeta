import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Procedure, Card } from '../_models/index';
import { AlertService, ProcedureService } from '../_services/index';
import { Status, ProcedureType } from '../_storage/index';

@Component({
    templateUrl: './form.component.html'
})

export class ProcedureFormComponent implements OnInit, OnDestroy {

    loading = false;

    sub: Subscription;
    subProcedures: Subscription;
    closeAfter: any[];
    activateAfter: any[];
    mustBeDoneBefore: any[];
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
                this.closeAfter         = data.map(x => { return { name: x.name, value: x.id + 'pc', checked: false }; } );
                this.activateAfter      = data.map(x => { return { name: x.name, value: x.id + 'pa', checked: false }; } );
                this.mustBeDoneBefore   = data.map(x => { return { name: x.name, value: x.id + 'pm', checked: false }; } );
                const id = parseInt(this.route.snapshot.paramMap.get('id'));
                if (id > 0) this.load(+id);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    initProcedures() {
        this.model.card.closeAfter.forEach(id => {
            let p = this.closeAfter.find(x => x.value == id + 'pc');
            if (p) p.checked = true;
        });
        this.model.card.activateAfter.forEach(id => {
            let p = this.activateAfter.find(x => x.value == id + 'pa');
            if (p) p.checked = true;
        });
        this.model.card.mustBeDoneBefore.forEach(id => {
            let p = this.mustBeDoneBefore.find(x => x.value == id + 'pm');
            if (p) p.checked = true;
        });
    }

    load(id: number) {
        this.loading = true;
        this.sub = this.service.get(id).subscribe(
            data => {
                this.model = data;
                if (!this.model.card) this.model.card = new Card();
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
        this.model.card.closeAfter = this.closeAfter.filter(x => x.checked).map(x => parseInt(x.value));
        this.model.card.activateAfter = this.activateAfter.filter(x => x.checked).map(x => parseInt(x.value));
        this.model.card.mustBeDoneBefore = this.mustBeDoneBefore.filter(x => x.checked).map(x => parseInt(x.value));
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
