import { Component, ViewContainerRef, OnInit, OnDestroy } from '@angular/core';
import { NgbPanelChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { CashType } from '../_storage/index';
import { AlertService, SettingsService, OutcomeService } from '../_services/index';
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
    subItemList: Subscription;
    subSettings: Subscription;
    
    data: any;
    totlalSum = 0;
    itemsCache: any = {};
    settings: any;
    from: string;
    to: string;

    constructor(
        private alertService: AlertService,
        private modalService: ModalDialogService,
        private viewRef: ViewContainerRef,
        private settingsService: SettingsService,
        private service: OutcomeService
    ) { }

    ngOnInit() {
        this.loading = true;
        this.from = new Date().toISOString().split('T').shift();
        this.to = new Date().toISOString().split('T').shift();
        this.subSettings = this.settingsService.get().subscribe(
            data => {
                this.settings = data;
                this.load();
            },
            error => {
                this.alertService.error('Помилка на сервері', false);
                this.loading = false;
            });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subDelete) this.subDelete.unsubscribe();
        if (this.subItemList) this.subItemList.unsubscribe();
        if (this.subSettings) this.subSettings.unsubscribe();
    }

    isValid() {
        return (this.from && this.to && this.to >= this.from);
    }
    
    load() {
        if (!this.isValid()) return;
        this.loading = true;
        this.totlalSum = 0;
        this.data = [];
        this.itemsCache = {};
        this.sub = this.service.getOutcomeTreeSum(this.from, this.to).subscribe(
            data => {
                this.data = data;
                this.data.forEach(x => {
                    if (!x.catID) {
                        this.totlalSum += x.sum;
                    }
                });
                this.loading = false;
            },
            error => {
                this.alertService.error('Помилка на сервері', false);
                this.loading = false;
            });
    }
    
    isLockedCategory(id: string) {
        if (!id || id.length === 0) return true;
        const cat = this.data.find(x => x.id === id);
        const ext = cat.items.findIndex(x => x.id === this.settings.extractionItemId);
        const sal = cat.items.findIndex(x => x.id === this.settings.salaryItemId);
        return (ext > -1 || sal > -1);
    }
    
    isLocked(id: string) {
        return id == this.settings.extractionItemId || id == this.settings.salaryItemId;
    }
    
    public toggleAccordian( props:NgbPanelChangeEvent ): void {
        if (props.nextState) {
            this.loading = true;
            this.itemsCache[props.panelId] = [];
            this.subItemList = this.service.getItemList(props.panelId, this.from, this.to).subscribe(
                data => {
                    this.itemsCache[props.panelId] = data;
                    this.loading = false;
                },
                error => {
                    this.alertService.error('Помилка на сервері', false);
                    this.loading = false;
                });
        }
        /*
        props.nextState // true === panel is toggling to an open state 
                                   // false === panel is toggling to a closed state
        props.panelId    // the ID of the panel that was clicked
        props.preventDefault(); // don't toggle the state of the selected panel
        */
    }
    
    public toggleCategory( props:NgbPanelChangeEvent ): void {
        if (props.nextState && !props.panelId) {
            props.panelId = null;
            this.toggleAccordian(props);
        }
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
