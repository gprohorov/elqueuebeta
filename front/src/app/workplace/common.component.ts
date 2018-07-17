import { Component, OnInit, OnDestroy, ElementRef, ViewChild, ViewContainerRef } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { ModalDialogService } from 'ngx-modal-dialog';

import { Status } from '../_storage/index';
import { Patient } from '../_models/index';
import { AlertService, WorkplaceCommonService, PatientsQueueService } from '../_services/index';
import { PatientIncomeModalComponent } from '../patient/income.modal.component';

@Component({
    templateUrl: './common.component.html'
})
export class WorkplaceCommonComponent implements OnInit, OnDestroy {

    @ViewChild('canvas', { read: ElementRef })
    public canvas: ElementRef;
    private cx: CanvasRenderingContext2D;
    private canvasImage = new Image;

    loading = false;
    sub: Subscription;
    subTemp: Subscription;
    subPatient: Subscription;
    subProcedure: Subscription;
    Status = Status;
    Statuses = Object.keys(Status);
    
    reloadFunc: any;
    item: any;
    talonId: string;
    zones: number = 1;
    patientId: string;
    procedureId: number;
    procedureStarted: boolean = false;
    model: any = { comment: '' };

    constructor(
        private viewRef: ViewContainerRef,
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: WorkplaceCommonService,
        private patientsQueueService: PatientsQueueService,
        private modalService: ModalDialogService
    ) { }

    ngOnInit() {
        this.canvasImage.src = '/assets/skeleton.jpg';
        this.patientId = this.route.snapshot.paramMap.get('patientId');
        this.procedureId = +this.route.snapshot.paramMap.get('procedureId');
        this.load();
        this.reloadFunc = setInterval(() => {
            this.load();
        }, 60000);
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subPatient) this.subPatient.unsubscribe();
        if (this.subProcedure) this.subProcedure.unsubscribe();
        if (this.subTemp) this.subTemp.unsubscribe();
        if (this.reloadFunc) clearInterval(this.reloadFunc);
    }

    load() {
        this.loading = true;
        this.subPatient = this.service.getPatient(this.patientId, this.procedureId).subscribe(data => {
            this.item = data;
            this.loading = false;
            this.procedureStarted = ('ON_PROCEDURE' == this.item.talon.activity)

            setTimeout(() => {
                this.canvasInit();
                if (this.item.patient.therapy && this.item.patient.therapy.assignments) {
                    const procedure = this.item.patient.therapy.assignments.find(x => { 
                        return (x.procedureId == this.procedureId) ? x : false; 
                    });
                    if (procedure && procedure.picture) {
                        procedure.picture.forEach(x => { this.drawOnCanvas(x[0], x[1]) });
                    }
                }
            }, 0);
        });
    }

    private canvasInit() {
        if (this.canvas) {
            const canvasEl: HTMLCanvasElement = this.canvas.nativeElement;
            this.cx = canvasEl.getContext('2d');

            this.cx.lineWidth = 6;
            this.cx.lineCap = 'round';
            this.cx.strokeStyle = 'blue';

            this.cx.drawImage(this.canvasImage, 0, 0);
        }
    }

    private drawOnCanvas(
        prevPos: { x: number, y: number, c: string },
        currentPos: { x: number, y: number, c: string }
    ) {
        // incase the context is not set
        if (!this.cx) { return; }

        // start our drawing path
        this.cx.beginPath();

        // we're drawing lines so we need a previous position
        if (prevPos) {
            this.cx.strokeStyle = prevPos.c;
            // sets the start point
            this.cx.moveTo(prevPos.x, prevPos.y); // from
            // draws a line from the start pos until the current position
            this.cx.lineTo(currentPos.x, currentPos.y);

            // strokes the current path with the styles we set earlier
            this.cx.stroke();
        }
    }
    
    updateStatus(id: string, value: string, event: any) {
        if (confirm('Встановити статус "' + Status[value].text + '" ?')) {
            this.subTemp = this.patientsQueueService.updateStatus(id, value).subscribe(data => {
                this.load();
            });
        } else {
            this.load();
        }
    }

    showIncomePopup(patient: any) {
        this.modalService.openDialog(this.viewRef, {
            title: 'Пацієнт: ' + patient.patient.person.fullName,
            childComponent: PatientIncomeModalComponent,
            data: patient.patient
        });
        this.alertService.subject.subscribe(() => { this.load() });
    }

    startProcedure() {
        this.subProcedure = this.service.startProcedure(this.item.talon.id).subscribe(data => {
            this.alertService.success('Процедуру розпочато.');
            this.load();
        });
    }

    cancelProcedure() {
        if (confirm('Скасувати процедуру ?')) {
            this.subProcedure = this.service.cancelProcedure(this.item.talon.id).subscribe(data => {
                this.alertService.success('Процедуру скасовано.');
                this.router.navigate(['workplace']);
            });
        }
    }

    executeProcedure() {
        this.subProcedure = this.service.executeProcedure(this.item.talon.id).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.router.navigate(['workplace']);
        }, 
        error => {
            this.alertService.error(error, true);
            this.router.navigate(['workplace']);
        });
    }

    subZone() {
        this.loading = true;
        this.service.subZone(this.item.talon.id).subscribe(
            data => {
                this.alertService.success('Зону скасовано.', true);
                this.load();
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }

    addZone() {
        this.loading = true;
        this.service.addZone(this.item.talon.id).subscribe(
            data => {
                this.alertService.success('Зону додано.', true);
                this.load();
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
    
    comment() {
        this.loading = true;
        this.service.commentProcedure(this.item.talon.id, this.model.comment).subscribe(
            data => {
                this.alertService.success('Зміни збережено.', true);
                this.model.comment = '';
                this.load();
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
    }
}
