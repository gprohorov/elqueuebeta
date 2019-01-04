import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { AlertService, UserService } from '../_services/index';

@Component({
    templateUrl: './form.component.html'
})
export class UserFormComponent implements OnInit, OnDestroy {

    loading = false;
    model: any;
    sub: Subscription;

    constructor(
        private alertService: AlertService,
        private route: ActivatedRoute,
        private router: Router,
        private service: UserService
    ) { }

    ngOnInit() {
        const id = this.route.snapshot.paramMap.get('id');
        this.load(id);
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load(id: string) {
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

    submit(f) {
        this.loading = true;
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
