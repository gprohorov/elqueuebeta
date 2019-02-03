import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { AlertService, UserService } from '../_services/index';

import { User, Doctor } from '../_models/index';
import { Roles } from '../_storage/index';

@Component({
    templateUrl: './form.component.html'
})
export class UserFormComponent implements OnInit, OnDestroy {

    loading = false;
    model: User = new User();
    roles: any[] = [];
    sub: Subscription;

    constructor(
        private alertService: AlertService,
        private route: ActivatedRoute,
        private router: Router,
        private service: UserService
    ) { }

    ngOnInit() {
        Roles.forEach(x => {
            this.roles.push({ name: x, checked: false });
        });
        const id = this.route.snapshot.paramMap.get('id');
        if (id) this.load(id);
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load(id: string) {
        this.loading = true;
        this.sub = this.service.getWithDoctor(id).subscribe(
            data => {
                this.model = data;
                this.roles = [];
                Roles.forEach(x => {
                    this.roles.push({ name: x, checked: this.model.authorities.includes(x) });
                });
                this.loading = false;
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    submit(f) {
        this.loading = true;
        this.model.info = null;
        this.model.authorities = [];
        this.roles.forEach( x => {
            if (x.checked) this.model.authorities.push(x.name);
        });
        this.service.update(this.model).subscribe(() => {
            this.alertService.success('Операція пройшла успішно', true);
            this.router.navigate(['users']); 
        },
        error => {
            this.alertService.error(error);
            this.loading = false;
        });
    }
}
