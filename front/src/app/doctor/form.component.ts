﻿import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Doctor } from '../_models/index';
import { AuthService, AlertService, DoctorService, ProcedureService, UserService } from '../_services/index';

@Component({
    templateUrl: './form.component.html'
})
export class DoctorFormComponent implements OnInit, OnDestroy {

    loading = false;

    model: Doctor = new Doctor();
    procedures: any[];
    users: any[];
    sub: Subscription;
    subProcedures: Subscription;
    subUsers: Subscription;

    constructor(
        public authService: AuthService,
        private alertService: AlertService,
        private route: ActivatedRoute,
        private router: Router,
        private service: DoctorService,
        private procedureService: ProcedureService,
        private userService: UserService
    ) { }

    ngOnInit() {
        this.loadUsers();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subProcedures) this.subProcedures.unsubscribe();
        if (this.subUsers) this.subUsers.unsubscribe();
    }

    loadUsers() {
        this.loading = true;
        this.subUsers = this.userService.getAll().subscribe(
            data => {
                this.loading = false;
                this.users = data.map(x => ({ 
                    id: x.id, name: x.username + ' (' + x.authorities.join(', ') + ')' 
                }));
                this.loadProcedures();
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
    
    loadProcedures() {
        this.loading = true;
        this.subProcedures = this.procedureService.getAll().subscribe(
            data => {
                this.loading = false;
                this.procedures = data.map(x => ({ name: x.name, value: x.id, checked: false, procent: 10 }) );
                const id = parseInt(this.route.snapshot.paramMap.get('id'));
                if (id > 0) this.load(id);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    initProcedures() {
        this.model.procedureIds.forEach(id => {
            const p = this.procedures.find(x => x.value === id);
            if (p) p.checked = true;
        });
        if (this.model.percents) {
            this.model.percents.forEach(r => {
                const p = this.procedures.find(x => x.value === r.procedureId);
                if (p) p.procent = r.procent;
            });
        }
    }

    load(id: number) {
        this.loading = true;
        this.sub = this.service.get(id).subscribe(
            data => {
                this.model = data;
                this.initProcedures();
                this.loading = false;
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    submit(f) {
        this.loading = true;
        this.model.procedureIds = this.procedures.filter(x => x.checked).map(x => x.value);
        this.model.percents = this.procedures.map(x => { return { procedureId: x.value, procent: x.procent }; });
        this.service.update(this.model).subscribe(() => {
            this.alertService.success('Операція пройшла успішно', true);
            this.router.navigate(['doctors']);
        },
        error => {
            this.alertService.error(error);
            this.loading = false;
        });
    }
}
