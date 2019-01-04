import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { UserService } from '../_services/index';

@Component({
    templateUrl: './list.component.html'
})
export class UserListComponent implements OnInit, OnDestroy {

    sub: Subscription;
    subDelete: Subscription;
    items: any[] = [];
    loading = false;

    constructor(private service: UserService) { }

    ngOnInit() {
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subDelete) this.subDelete.unsubscribe();
    }

    delete(id: string, name: string) {
        if (confirm('Видалити "' + name + '" ?')) {
            this.subDelete = this.service.delete(id).subscribe(() => { this.load(); });
        }
    }
    
    getRoles(roles: any[]) {
        return roles.join(', ');
    }

    load() {
        this.loading = true;
        this.sub = this.service.getAll().subscribe(data => {
            this.items = data;
            this.loading = false;
        });
    }
}
