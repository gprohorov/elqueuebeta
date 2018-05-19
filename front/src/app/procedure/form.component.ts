import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Procedure } from '../_models/index';
import { AlertService, ProcedureService } from '../_services/index';
import { Status } from '../_storage/index';

@Component({
    templateUrl: './form.component.html'
})

export class ProcedureFormComponent {

    loading = false;

    model: Procedure = new Procedure();
    sub: Subscription;
    Status = Status;
    Statuses = Object.keys(Status);

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: ProcedureService
    ) { }

    ngOnInit() {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) this.load(+id);
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load(id: number) {
        this.loading = true;
        this.sub = this.service.get(id).subscribe(
            data => {
                this.model = data;
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
