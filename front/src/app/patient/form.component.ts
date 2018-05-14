import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Patient } from '../_models/index';
import { PatientService, AlertService } from '../_services/index';

@Component({
    templateUrl: './form.component.html'
})
export class PatientFormComponent {
    model: Patient;
    sub: Subscription;
    loading = false;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private service: PatientService,
        private alertService: AlertService) { }

    ngOnInit() {
        const id = this.route.snapshot.paramMap.get('id');
        if (id && id != '') this.load(id);
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load(id: string) {
        this.loading = true;
        this.sub = this.service.get(id)
            .subscribe(
            data => {
                data.person.gender = data.person.gender.toString();
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
        this.service.update(this.model)
            .subscribe(
            data => {
                this.alertService.success('Зміни збережено.', true);
                this.router.navigate(['patients']);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}
