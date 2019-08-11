import { Component, OnInit, OnDestroy, ViewContainerRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ModalDialogService } from 'ngx-modal-dialog';
import { Subscription } from 'rxjs/Subscription';

import { AssignPatientRecomendationModalComponent } from './assign-patient-recomendation.modal.component';
import { PatientService, AlertService } from '../_services/index';
import { Patient } from '../_models/index';

@Component({
    templateUrl: './form.component.html'
})
export class PatientFormComponent implements OnInit, OnDestroy {

    loading = false;

    model: Patient = new Patient();
    sub: Subscription;

    constructor(
        private viewRef: ViewContainerRef,
        private route: ActivatedRoute,
        private router: Router,
        private service: PatientService,
        private alertService: AlertService,
        private modalService: ModalDialogService,
    ) { }

    ngOnInit() {
        const id = this.route.snapshot.paramMap.get('id');
        if (id && id !== '') this.load(id);
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
    }
    
    showAssignPatientRecomendationPopup() {
        const options = {
            title: 'Вибір рекомендавця',
            childComponent: AssignPatientRecomendationModalComponent,
            closeDialogSubject: null
        };
        this.modalService.openDialog(this.viewRef, options);
        options.closeDialogSubject.subscribe((item) => {
            this.model.recomendation = item.id; 
            this.model.recomendationName = item.person.fullName
        });
    }

    load(id: string) {
        this.loading = true;
        this.sub = this.service.get(id).subscribe(
            data => {
                data.person.gender = data.person.gender.toString();
                this.model = data;
                this.loading = false;
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    submit() {
        this.loading = true;
        this.service.update(this.model).subscribe(
            () => {
                this.alertService.success('Зміни збережено.', true);
                this.router.navigate(['patients']);
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}
