import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, ActivationEnd } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Procedure } from '../_models/index';
import { AuthService, UtilService, AlertService, ProcedureService } from '../_services/index';

@Component({
    selector: 'app-nav',
    templateUrl: './nav.component.html'
})
export class NavComponent implements OnInit, OnDestroy {

    user: any;
    sub: Subscription;
    subProcedures: Subscription;
    procedures: Procedure[] = [];
    procedureName: string = '';
    procedureId: number;

    constructor(
        public authService: AuthService,
        private utilService: UtilService,
        private alertService: AlertService,
        private procedureService: ProcedureService,
        private router: Router,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {
        if (this.authService.isAuth()) {
            this.user = this.authService.getUserInfo();
        }
        this.loadProcedures();
        this.sub = this.router.events.subscribe((event) => {
            if (event instanceof ActivationEnd) {
                const params = event.snapshot.params;
                if (+params.id > 0) {
                    this.procedureId = +params.id;
                    this.setProcedureName();
                }
            }
        });
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subProcedures) this.subProcedures.unsubscribe();
    }

    loadProcedures() {
        this.subProcedures = this.procedureService.getAll().subscribe(data => {
            this.procedures = data;
            this.setProcedureName();
        });
    }

    getProcedureName(id: number): string {
        let procedure = this.procedures.find(x => x.id == id);
        return procedure != null ? procedure.name : 'Процедура не вибрана...';
    }

    setProcedureName() {
        this.procedureName = this.getProcedureName(this.procedureId);
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
