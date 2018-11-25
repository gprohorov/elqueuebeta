import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { AlertService, SettingsService, OutcomeService } from '../_services/index';

@Component({
    templateUrl: './form.component.html'
})
export class SettingsFormComponent implements OnInit, OnDestroy {

    loading = false;

    model = {
        tax: 0,
        salaryItemId: 'other'
    };
    outcomeItems: any;
    sub: Subscription;
    subOutcomeItems: Subscription;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private alertService: AlertService,
        private settingsService: SettingsService,
        private outcomeService: OutcomeService
    ) { }

    ngOnInit() {
//        this.load();
        this.subOutcomeItems = this.outcomeService.getOutcomeTree()
        .subscribe(data => { 
            this.outcomeItems = data.filter(x => x.id != null );
        });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }

    load() {
        this.loading = true;
        this.sub = this.settingsService.get().subscribe(
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
        if (this.model.salaryItemId === 'other') this.model.salaryItemId = null; 
        this.settingsService.update(this.model).subscribe(
            () => {
                this.alertService.success('Зміни збережено.', true);
                this.router.navigate(['/']);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}
