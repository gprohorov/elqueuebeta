import { Component, OnInit, OnDestroy, ElementRef, ViewChild, ViewContainerRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
//tslint:disable-next-line:import-blacklist
import { Observable, pipe } from 'rxjs';
import { fromEvent } from 'rxjs/observable/fromEvent';
import { switchMap, takeUntil, pairwise } from 'rxjs/operators';
import { ModalDialogService } from 'ngx-modal-dialog';

import { Status } from '../_storage/index';
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
    private canvasBuffer = [];

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
    patientId: string;
    procedureId: number;
    procedureStarted = false;
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
            this.procedureStarted = ('ON_PROCEDURE' === this.item.talon.activity);

            setTimeout(() => {
                this.canvasInit();
                if (   this.item.patient.therapy 
                    && this.item.patient.therapy.assignments[0]
                    && this.item.patient.therapy.assignments[0].picture) {
                    this.restoreCanvas(this.item.patient.therapy.assignments[0].picture);
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

    public clearCanvas() {
        this.restoreCanvas([]);
    }
    
    public setColor(color) {
        this.cx.strokeStyle = color;
    }
    
    public restoreCanvas(picture) {
        this.canvasInit();
        this.canvasBuffer = picture;
        this.canvasBuffer.forEach(x => { this.drawOnCanvas(x[0], x[1]); });
    }
        
    updateStatus(id: string, value: string) {
        if (confirm('Встановити статус "' + Status[value].text + '" ?')) {
            this.subTemp = this.patientsQueueService.updateStatus(id, value).subscribe(() => {
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
        this.alertService.subject.subscribe(() => { this.load(); });
    }

    startProcedure() {
        this.loading = true;
        this.subProcedure = this.service.startProcedure(this.item.talon.id).subscribe(data => {
            if (data == null) {
              this.router.navigate(['workplace']);
            } else {
              this.alertService.success('Процедуру розпочато.');
              this.load();
            }
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
        this.subProcedure = this.service.executeProcedure(
            this.item.talon.id, this.item.talon.zones, this.canvasBuffer).subscribe(() => {
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
            () => {
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
            () => {
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
            () => {
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
