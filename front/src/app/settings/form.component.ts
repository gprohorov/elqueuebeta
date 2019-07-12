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
        canteen: 0,
        extractionItemId: 'other',
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
        this.load();
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
                if (this.model.salaryItemId == null) this.model.salaryItemId = 'other'; 
                if (this.model.extractionItemId == null) this.model.extractionItemId = 'other';
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
        if (this.model.extractionItemId === 'other') this.model.extractionItemId = null; 
        this.settingsService.update(this.model).subscribe(
            () => {
                this.alertService.success('Налаштування збережено.', true);
                this.loading = false;
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}
