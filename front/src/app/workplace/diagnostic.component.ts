import { Component, OnInit, OnDestroy, ElementRef, ViewChild, ViewContainerRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
// tslint:disable-next-line:import-blacklist
import { Observable, pipe } from 'rxjs';
import { fromEvent } from 'rxjs/observable/fromEvent';
import { switchMap, takeUntil, pairwise } from 'rxjs/operators';
import { ModalDialogService } from 'ngx-modal-dialog';

import { Status } from '../_storage/index';
import { AlertService, WorkplaceDiagnosticService, PatientsQueueService, PatientService } from '../_services/index';
import { PatientIncomeModalComponent } from '../patient/income.modal.component';

@Component({
    templateUrl: './diagnostic.component.html',
    styles: ['canvas { border: 1px solid #000; }']
})
export class WorkplaceDiagnosticComponent implements OnInit, OnDestroy {

    @ViewChild('canvasDiag', { read: ElementRef })
    public canvasDiag: ElementRef;
    private cx: CanvasRenderingContext2D;
    private canvasImage = new Image;
    private canvasBuffer = [];

    loading = false;
    sub: Subscription;
    subPatient: Subscription;
    subProcedure: Subscription;
    subProcedures: Subscription;
    subTemp: Subscription;
    Status = Status;
    Statuses = Object.keys(Status);
    procedures: any;
    currentProcedureId: number = null;
    currentProcedureName = '';

    item: any;
    patientId: string;
    procedureId: number;
    lastCabinet = 0;
    procedureStarted = false;
    model: any = {};
    Discounts = [0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100];

    constructor(
        private viewRef: ViewContainerRef,
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: WorkplaceDiagnosticService,
        private patientsQueueService: PatientsQueueService,
        private patientService: PatientService,
        private modalService: ModalDialogService
    ) { }

    ngOnInit() {
        this.canvasImage.src = '/assets/skeleton.jpg';
        this.patientId = this.route.snapshot.paramMap.get('patientId');
        this.procedureId = +this.route.snapshot.paramMap.get('procedureId');
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subPatient) this.subPatient.unsubscribe();
        if (this.subProcedure) this.subProcedure.unsubscribe();
        if (this.subProcedures) this.subProcedures.unsubscribe();
        if (this.subTemp) this.subTemp.unsubscribe();
    }

    public toggleProcedure(procedure, val: boolean = null) {
        if (!procedure.selected || val === true) {
            if (this.canvasBuffer.length > 0 && (!procedure.picture || procedure.picture.length === 0)) {
                procedure.picture = this.canvasBuffer;
            }
            procedure.selected = true;
        } else {
            procedure.selected = false;
        }
    }

    public saveProcedure() {
        const p = this.procedures.find(x => (x.id === this.currentProcedureId) ? x : false);
        p.picture = this.canvasBuffer;
        this.currentProcedureId = null;
        this.currentProcedureName = '';
    }
    
    public saveProcedureAll() {
        this.procedures.forEach(p => {
            p.picture = this.canvasBuffer;
        });
        this.currentProcedureId = null;
        this.currentProcedureName = '';
    }

    public clearCanvas() {
        this.restoreCanvas([]);
        if (this.currentProcedureId != null) {
            const p = this.procedures.find(x => (x.id === this.currentProcedureId) ? x : false);
            p.picture = this.canvasBuffer;
        }
    }

    public isCorrectable(procedure) {
        return (procedure.selected && Array.isArray(procedure.picture) && procedure.picture.length > 0);
    }

    public correctProcedure(procedure) {
        this.restoreCanvas(procedure.picture);
        this.currentProcedureId = procedure.id;
        this.currentProcedureName = procedure.name;
    }

    public restoreCanvas(picture) {
        this.canvasInit();
        this.canvasBuffer = picture;
        this.canvasBuffer.forEach(x => { this.drawOnCanvas(x[0], x[1]); });
    }

    public setColor(color) {
        this.cx.strokeStyle = color;
    }

    private canvasInit() {
        if (this.canvasDiag) {
            this.canvasBuffer = [];

            const canvasEl: HTMLCanvasElement = this.canvasDiag.nativeElement;
            this.cx = canvasEl.getContext('2d');

            this.cx.lineWidth = 6;
            this.cx.lineCap = 'round';
            this.cx.strokeStyle = 'blue';

            this.cx.drawImage(this.canvasImage, 0, 0);

            this.captureEvents(canvasEl);
        }
    }

    private captureEvents(canvasEl: HTMLCanvasElement) {

        // this will capture all mousedown events from the canvas element
        fromEvent(canvasEl, 'mousedown').pipe(
            switchMap((e: MouseEvent) => {
                e.preventDefault();
                // after a mouse down, we'll record all mouse moves
                return fromEvent(canvasEl, 'mousemove').pipe(
                    // we'll stop (and unsubscribe) once the user releases the mouse
                    // this will trigger a 'mouseup' event
                    takeUntil(Observable.fromEvent(canvasEl, 'mouseup')),
                    // we'll also stop (and unsubscribe) once the mouse leaves the canvas (mouseleave event)
                    takeUntil(Observable.fromEvent(canvasEl, 'mouseleave')),
                    // pairwise lets us get the previous value to draw a line from
                    // the previous point to the current point
                    pairwise()
                );
            })
        ).subscribe((res: [MouseEvent, MouseEvent]) => {
            const rect = canvasEl.getBoundingClientRect();

            // previous and current position with the offset
            const prevPos = {
                x: res[0].clientX - rect.left,
                y: res[0].clientY - rect.top,
                c: this.cx.strokeStyle
            };

            const currentPos = {
                x: res[1].clientX - rect.left,
                y: res[1].clientY - rect.top,
                c: this.cx.strokeStyle
            };

            this.storePoints(prevPos, currentPos);
        });

        // this will capture all touch events from the canvas element
        fromEvent(canvasEl, 'touchstart').pipe(
            switchMap((e: TouchEvent) => {
                e.preventDefault();
                // after a touch start, we'll record all moves
                return fromEvent(canvasEl, 'touchmove').pipe(
                    // we'll stop (and unsubscribe) once the user stop touching
                    // this will trigger a 'touchend' event
                    takeUntil(Observable.fromEvent(canvasEl, 'touchend')),
                    // pairwise lets us get the previous value to draw a line from
                    // the previous point to the current point
                    pairwise()
                );
            })
        ).subscribe((res: [TouchEvent, TouchEvent]) => {
            const rect = canvasEl.getBoundingClientRect();

            // previous and current position with the offset
            const prevPos = {
                x: res[0].touches[0].clientX - rect.left,
                y: res[0].touches[0].clientY - rect.top,
                c: this.cx.strokeStyle
            };

            const currentPos = {
                x: res[1].touches[0].clientX - rect.left,
                y: res[1].touches[0].clientY - rect.top,
                c: this.cx.strokeStyle
            };

            this.storePoints(prevPos, currentPos);
        });
    }

    private storePoints(prevPos, currentPos) {
        if (!this.procedureStarted) return;
        this.canvasBuffer.push([prevPos, currentPos]);
        this.drawOnCanvas(prevPos, currentPos);
    }

    private drawOnCanvas(
        prevPos: { x: number, y: number, c: string },
        currentPos: { x: number, y: number, c: string }
    ) {
        // in case the context is not set
        if (!this.cx) { return; }

        // start our drawing path
        this.cx.beginPath();

        // we're drawing lines so we need a previous position
        if (prevPos) {
            this.cx.strokeStyle = prevPos.c;
            // sets the start point
            this.cx.moveTo(prevPos.x, prevPos.y); // from
            // draws a line from the start position until the current position
            this.cx.lineTo(currentPos.x, currentPos.y);

            // strokes the current path with the styles we set earlier
            this.cx.stroke();
        }
    }

    load() {
        this.loading = true;
        this.sub = this.service.getProcedures().subscribe(data => {
            this.procedures = data.sort(function (a, b) {
                const x = a.cabinet;
                const y = b.cabinet;
                return ((x < y) ? -1 : ((x > y) ? 1 : 0));
            });

            let cab = 0;
            this.procedures.forEach((p) => {
                p.header = (p.cabinet > cab) ? 'Кабінет № ' + p.cabinet : '';
                cab = p.cabinet;
            });

            // tslint:disable-next-line:no-shadowed-variable
            this.subPatient = this.service.getPatient(this.patientId).subscribe(data => {
                this.loading = false;

                this.item = data;
                this.model = data.therapy ? data.therapy : { days: 10 };
                this.model.manualTherapy = true;

                this.procedureStarted = ('ON_PROCEDURE' === this.item.talon.activity);

                setTimeout(() => {
                    this.canvasInit();
                    if (this.model.assignments) {
                        this.procedures.forEach((p) => {
                            this.model.assignments.forEach((sp) => {
                                if (sp.procedureId === p.id) {
                                    p.picture = sp.picture;
                                    this.toggleProcedure(p, true);
                                }
                            });
                        });
                        if (this.canvasBuffer.length === 0) {
                            for (let i = 0; i <= this.procedures.length; i++) {
                                if (this.procedures[i]
                                    && this.procedures[i].picture
                                    && this.procedures[i].picture.length > 0
                                    ) {
                                        this.restoreCanvas(this.procedures[i].picture);
                                        this.currentProcedureId = this.procedures[i].id;
                                        this.currentProcedureName = this.procedures[i].name;
                                        break;
                                }
                            }
                        }
                    }
                }, 0);
            });
        });
    }

    updateStatus(id: string, value: string) {
        if (confirm('Встановити статус "' + Status[value].text + '" ?')) {
            this.loading = true;
            this.subTemp = this.patientsQueueService.updateStatus(id, value).subscribe(() => {
                this.load();
            });
        } else {
            this.load();
        }
    }
    
    updateDiscount(id: string, value: number) {
        this.loading = true;
        this.subTemp = this.patientService.updateDiscount(id, value).subscribe(() => {
            this.load();
        });
    }

    showIncomePopup(patient: any) {
        this.modalService.openDialog(this.viewRef, {
            title: 'Пацієнт: ' + patient.patient.person.fullName,
            childComponent: PatientIncomeModalComponent,
            data: patient.patient
        });
        this.alertService.subject.subscribe(() => { this.load(); });
    }

    startProcedure() {
        this.loading = true;
        this.subProcedure = this.service.startProcedure(this.item.talon.id).subscribe(() => {
            this.alertService.success('Процедуру розпочато.');
            this.load();
        });
    }

    cancelProcedure() {
        if (confirm('Скасувати процедуру ?')) {
            this.loading = true;
            this.subProcedure = this.service.cancelProcedure(this.item.talon.id).subscribe(() => {
                this.alertService.success('Процедуру скасовано.');
                this.router.navigate(['workplace']);
            });
        }
    }

    executeProcedure() {
        this.loading = true;
        // form assignment
        this.model.assignments = [];
        this.procedures.forEach((p) => {
            if (p.selected) this.model.assignments.push({ procedureId: p.id, desc: '', picture: p.picture });
        });

        this.subProcedure = this.service.executeProcedure(this.item.talon.id, this.model).subscribe(() => {
            this.alertService.success('Процедуру завершено.');
            this.router.navigate(['workplace']);
        });
    }
}
