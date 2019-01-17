import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Doctor } from '../_models/index';
import { AuthService, AlertService, DoctorService, ProcedureService, FinanceService } from '../_services/index';

@Component({
    templateUrl: './salary-preview.component.html'
})
export class FinanceSalaryPreviewComponent implements OnInit, OnDestroy {

    loading = false;

    from: string;
    to: string;
    id: number;
    model: Doctor = new Doctor();
    rate: number = 0;
    procedures: any[];
    proceduresTemp: any[];
    dataBase: any[];
    dataCalc: any[];
    sub: Subscription;
    subProcedures: Subscription;
    subBaseValues: Subscription;
    subCalcValues: Subscription;

    constructor(
        public authService: AuthService,
        private alertService: AlertService,
        private route: ActivatedRoute,
        private router: Router,
        private service: DoctorService,
        private procedureService: ProcedureService,
        private financeService: FinanceService
    ) { }

    ngOnInit() {
        this.id = parseInt(this.route.snapshot.paramMap.get('id'));
        const today = (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000))
            .toISOString().slice(0, -14); 
        this.from = this.route.snapshot.paramMap.get('from') || today;
        this.to = this.route.snapshot.paramMap.get('to') || today;
        this.loadProcedures();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subProcedures) this.subProcedures.unsubscribe();
        if (this.subBaseValues) this.subBaseValues.unsubscribe();
        if (this.subCalcValues) this.subCalcValues.unsubscribe();
    }

    isValid() {
        return (this.from && this.to && this.to >= this.from);
    }
    
    loadProcedures() {
        this.loading = true;
        this.subProcedures = this.procedureService.getAll().subscribe(
            data => {
                this.loading = false;
                this.proceduresTemp = data.map(x => ({ id: x.id, name: x.name, procent: 10 }) );
                if (this.id > 0) this.load(this.id);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    initProcedures() {
        this.procedures = [];
        this.model.procedureIds.forEach(id => {
            const p = this.proceduresTemp.find(x => x.id === id);
            this.procedures.push(p);
        });
        if (this.model.percents) {
            this.model.percents.forEach(r => {
                const p = this.procedures.find(x => x.id === r.procedureId);
                if (p) {
                    p.procent = r.procent;
                    p.procentNew = r.procent;
                }
            });
        }
    }
    
    loadBaseValues() {
        this.subBaseValues = this.financeService.getDoctorSalaryBase(this.id, this.from, this.to).subscribe(
            data => {
                this.loading = false;
                this.dataBase = data;
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    load(id: number) {
        this.loading = true;
        this.sub = this.service.get(id).subscribe(
            data => {
                this.model = data;
                this.rate = data.rate;
                this.initProcedures();
                this.loadBaseValues();
                this.loading = false;
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    save() {
        this.loading = true;
        this.subCalcValues = this.financeService.saveDoctorSalaryPreview({
            id: this.id,
            rate: this.rate,
            percents: this.procedures.map(x => { return { procedureId: x.id, procent: x.procentNew }; })
        }).subscribe(data => {
                this.loading = false;
                this.alertService.success('Дані успішно збережені', true);
                this.router.navigate(['finance/salary-summary']);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
    
    sendCalc() {
        this.loading = true;
        this.subCalcValues = this.financeService.getDoctorSalaryPreview({
            id: this.id,
            from: this.from,
            to: this.to,
            rate: this.rate,
            percents: this.procedures.map(x => { return { procedureId: x.id, procent: x.procentNew }; })
        }).subscribe(data => {
                this.loading = false;
                this.dataCalc = data;
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}