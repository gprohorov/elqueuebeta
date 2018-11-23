import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { CashType } from '../_storage/index';
import { AlertService, OutcomeService } from '../_services/index';

@Component({
    templateUrl: './outcome.component.html'
})
export class FinanceOutcomeComponent implements OnInit, OnDestroy {

    loading = false;
    cashType = CashType;
    
    sub: Subscription;
    subDelete: Subscription;
    
    data: any;
    from: string;
    to: string;

    constructor(private alertService: AlertService, private service: OutcomeService) { }

    ngOnInit() {
        this.from = new Date().toISOString().split('T').shift();
        this.to = new Date().toISOString().split('T').shift();
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subDelete) this.subDelete.unsubscribe();
    }

    load() {
        this.data = [];
        this.sub = this.service.getOutcomeTree().subscribe(
            data => {
                this.data = data;
                this.loading = false;
            },
            error => {
                this.alertService.error('Помилка на сервері', false);
                this.loading = false;
            }
        );
    }
    
    showCreateCategory() {}
    
    showUpdateCategory() {}
    
    showDeleteCategory(id: string) {
        if (confirm('!!! УВАГА !!! Видалення категорій призведе'
                + ' до до безповоротного видалення всіх статей витрат в даній категорії,' 
                + ' а всі витрати по цим статтям перейдуть в спеціальну категорію ІНШЕ.'
                + ' ВИДАЛИТИ КАТЕГОРІЮ? ')) {
            this.subDelete = this.service.deleteNode(id).subscribe(() => { this.load(); });
        }
    }
    
    showCreateItem() {}
    
    showUpdateItem() {}
    
    showDeleteItem(id: string) {
        if (confirm('!!! УВАГА !!! Видалення статті витрат призведе до безповоротного '
                + ' переходу всіх витрат по цій статті в спеціальну категорію ІНШЕ.'
                + ' ВИДАЛИТИ СТАТТЮ ВИТРАТ? ')) {
            this.subDelete = this.service.deleteNode(id).subscribe(() => { this.load(); });
        }
    }
}
