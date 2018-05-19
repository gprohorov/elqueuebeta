import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Procedure } from '../_models/index';
import { AuthService, UtilService, AlertService, ProcedureService } from '../_services/index';

@Component({
    selector: 'app-nav',
    templateUrl: './nav.component.html',
    styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit, OnDestroy {

    user: any;
    subProcedures: Subscription;
    procedures: Procedure[] = [];

    constructor(
        public authService: AuthService,
        private utilService: UtilService,
        private alertService: AlertService,
        private procedureService: ProcedureService,
        private router: Router
    ) {
        if (this.authService.isAuth()) {
            this.user = this.authService.getUserInfo();
        }
    }

    ngOnInit() {
        this.loadProcedures();
    }

    ngOnDestroy() {
        if (this.subProcedures) this.subProcedures.unsubscribe();
    }

    loadProcedures() {
        this.subProcedures = this.procedureService.getAll().subscribe(data => {
            this.procedures = data;
        });
    }

    getProcedureName(id: number): string {
        let procedure = this.procedures.find(x => x.id == id);
        return procedure != null ? procedure.name : 'not found';
    }

    resetDB() {
        if (confirm('Reset DB?')) {
            this.utilService.resetDB().subscribe(() => {
                this.alertService.success('DB has been reseted.', true);
                this.router.navigate(['/']);
            });
        }
    }

}
