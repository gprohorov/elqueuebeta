import { Component, OnInit, OnDestroy, ElementRef, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Observable, pipe } from 'rxjs';
import { fromEvent } from 'rxjs/observable/fromEvent';
import { switchMap, takeUntil, pairwise } from 'rxjs/operators';

import { Patient, Procedure } from '../_models/index';
import { AlertService, ProcedureService, WorkplaceDiagnosticService } from '../_services/index';

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
    procedures: any;
    currentProcedureId: number = null;
    currentProcedureName: string = '';

    item: any;
    patientId: string;
    procedureId: number;
    lastCabinet: number = 0;
    procedureStarted: boolean = false;
    model: any = {};

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: WorkplaceDiagnosticService
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
    }

    public toggleProcedure(procedure, val: boolean = null) {
        if (!procedure.selected || val === true) {
            if (this.canvasBuffer.length > 0 && (!procedure.picture || procedure.picture.length == 0)) {
                procedure.picture = this.canvasBuffer;
            }
            procedure.selected = true;
        } else {
            procedure.selected = false;
        }
    }

    public saveProcedure() {
        let p = this.procedures.find(x => { return (x.id == this.currentProcedureId) ? x : false; });
        p.picture = this.canvasBuffer;
        this.currentProcedureId = null;
        this.currentProcedureName = '';
        this.clearCanvas();
    }

    public clearCanvas() {
        this.restoreCanvas([]);
        if (this.currentProcedureId != null) {
            let p = this.procedures.find(x => { return (x.id == this.currentProcedureId) ? x : false; });
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
        this.canvasBuffer.forEach(x => { this.drawOnCanvas(x[0], x[1]) });
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

    load() {
        this.loading = true;
        this.sub = this.service.getProcedures().subscribe(data => {
            this.procedures = data.sort(function (a, b) {
                var x = a.cabinet; var y = b.cabinet;
                return ((x < y) ? -1 : ((x > y) ? 1 : 0));
            });

            let cab = 0;
            this.procedures.forEach((p) => {
                p.header = (p.cabinet > cab) ? 'Кабінет № ' + p.cabinet : '';
                cab = p.cabinet;
            });

            this.subPatient = this.service.getPatient(this.patientId).subscribe(data => {
                this.loading = false;

                this.item = data;
                this.model = data.therapy ? data.therapy : { days: 10 };
                this.model.manualTherapy = true;

                this.procedureStarted = ('ON_PROCEDURE' == this.item.talon.activity)

                setTimeout(() => {
                    this.canvasInit();
                    if (this.model.assignments) {
                        this.procedures.forEach((p) => {
                            this.model.assignments.forEach((sp) => {
                                if (sp.procedureId == p.id) {
                                    p.picture = sp.picture;
                                    this.toggleProcedure(p, true);
                                }
                            });
                        });
                        if (this.canvasBuffer.length == 0) {
                            for (let i = 0; i <= this.procedures.length; i++) {
                                if (this.procedures[i].picture && this.procedures[i].picture.length > 0) {
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
        // form assignment
        this.model.assignments = [];
        this.procedures.forEach((p) => {
            if (p.selected) this.model.assignments.push({ procedureId: p.id, desc: '', picture: p.picture })
        });

        this.subProcedure = this.service.executeProcedure(this.item.talon.id, this.model).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.router.navigate(['workplace']);
        });
    }
}
