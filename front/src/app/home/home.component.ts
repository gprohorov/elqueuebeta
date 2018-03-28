import { Component, OnInit } from '@angular/core';

import { User } from '../_models/index';
import { UserService } from '../_services/index';

@Component({
    moduleId: module.id,
    templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit {
    currentUser: User;
    users: User[] = [];
    model: any = {};

    constructor(private userService: UserService) {
        this.model.sum = 0;
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }

    ngOnInit() {
        this.loadAllUsers();
    }

    updateBalance(sum) {
        this.currentUser.balance += sum;
        this.model.sum = 0;
        this.userService.update(this.currentUser).subscribe(() => { this.loadAllUsers() });
    }
    
    deleteUser(id: number) {
        if (confirm('Are you sure ?')) this.userService.delete(id).subscribe(() => { this.loadAllUsers() });
    }

    private loadAllUsers() {
        this.userService.getAll().subscribe(users => { this.users = users; });
    }
}