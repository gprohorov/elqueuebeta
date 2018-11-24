﻿import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { CashType } from '../_storage/index';
import { AlertService, OutcomeService } from '../_services/index';
import { FinanceOutcomeCategoryModalComponent } from './outcome/category.modal.component';
import { FinanceOutcomeItemModalComponent } from './outcome/item.modal.component';

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

    constructor(
        private alertService: AlertService,
        private modalService: ModalDialogService,
        private viewRef: ViewContainerRef,
        private service: OutcomeService
    ) { }

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
        this.sub = this.service.getOutcomeTreeSum(this.from, this.to).subscribe(
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
    
    showCreateCategory() {
        const options: any = {
            title: 'Створити категорію',
            childComponent: FinanceOutcomeCategoryModalComponent,
            data: {
                id: null,
                name: '',
                catID: null
            }
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(); });
    }
    
    showUpdateCategory(cat: any) {
        const options: any = {
            title: 'Редагувати категорію',
            childComponent: FinanceOutcomeCategoryModalComponent,
            data: cat
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(); });
    }
    
    showDeleteCategory(id: string) {
        if (confirm('!!! УВАГА !!! Видалення категорій призведе'
                + ' до до безповоротного видалення всіх статей витрат в даній категорії,' 
                + ' а всі витрати по цим статтям перейдуть в спеціальну категорію ІНШЕ.'
                + ' ВИДАЛИТИ КАТЕГОРІЮ? ')) {
            this.subDelete = this.service.deleteNode(id).subscribe(data => {
                    this.alertService.success('Категорію успішно видалено.');
                    this.load();
                },
                error => {
                    this.alertService.error('Помилка видалення категорії: ' + error.message);
                });
        }
    }
    
    showCreateItem(catID: string) {
        const options: any = {
            title: 'Створити статтю витрат',
            childComponent: FinanceOutcomeItemModalComponent,
            data: {
                categories: this.data,
                node: {
                    id: null,
                    name: '',
                    catID: catID
                }
            }
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(); });
    }
    
    showUpdateItem(item: any) {
        const options: any = {
            title: 'Редагувати статтю витрат',
            childComponent: FinanceOutcomeItemModalComponent,
            data: {
                categories: this.data,
                node: item
            }
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe(() => { this.load(); });
    }
    
    showDeleteItem(id: string) {
        if (confirm('!!! УВАГА !!! Видалення статті витрат призведе до безповоротного '
                + ' переходу всіх витрат по цій статті в спеціальну категорію ІНШЕ.'
                + ' ВИДАЛИТИ СТАТТЮ ВИТРАТ? ')) {
            this.subDelete = this.service.deleteNode(id).subscribe(data => {
                    this.alertService.success('Статтю витрат успішно видалено.');
                    this.load();
                },
                error => {
                    this.alertService.error('Помилка видалення статті витрат: ' + error.message);
                });
        }
    }
}