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
    private canvasBuffer = [];
    private canvasMemory = [];

    loading = false;
    sub: Subscription;
    subPatient: Subscription;
    subProcedure: Subscription;
    subProcedures: Subscription;
    procedures: Procedure[];

    item: any;
    patientId: string;
    procedureId: number;
    lastCabinet: number = 0;
    procedureStarted: boolean = false;
    model: any = {
//        codeDiag: 'AF 358',
//        diag: 'Діагноз пацієнта...',
//        notes: 'Нотатки про пацієнта...'
    };

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private procedureService: ProcedureService,
        private service: WorkplaceDiagnosticService
    ) { }

    ngOnInit() {
        this.patientId = this.route.snapshot.paramMap.get('patientId');
        this.procedureId = +this.route.snapshot.paramMap.get('procedureId');
        this.sub = this.procedureService.getAll().subscribe(data => {
            this.procedures = data.sort(function(a, b) {
                var x = a.cabinet; var y = b.cabinet;
                return ((x < y) ? -1 : ((x > y) ? 1 : 0));
            });
            let cab = 0;
            this.procedures.forEach((p) => {
                p.header = (p.cabinet > cab) ? 'Кабінет № ' + p.cabinet : '';
                cab = p.cabinet;
            });
        });
        this.load();
    }

    ngOnDestroy() {
        if (this.sub) this.sub.unsubscribe();
        if (this.subPatient) this.subPatient.unsubscribe();
        if (this.subProcedure) this.subProcedure.unsubscribe();
        if (this.subProcedures) this.subProcedures.unsubscribe();
    }
    
    public clearCanvas() {
        this.canvasBuffer = [];
        this.canvasInit();
    }
    
    public saveCanvas() {
        this.canvasMemory = this.canvasBuffer;
        console.log(this.canvasMemory);
    }
    
    public restoreCanvas() {
        this.canvasMemory.forEach(x => { this.drawOnCanvas(x[0], x[1]) });
    }
    
    public setColor(color) {
        this.cx.strokeStyle = color;
    }
    
    private canvasInit() {
        if (this.canvasDiag) {
            const canvasEl: HTMLCanvasElement = this.canvasDiag.nativeElement;
            this.cx = canvasEl.getContext('2d');

            this.cx.lineWidth = 6;
            this.cx.lineCap = 'round';
            this.cx.strokeStyle = 'blue';

            const img = new Image;
            img.onload = () => {
                this.cx.drawImage(img, 0, 0);
            };
            img.src = '/assets/skeleton.jpg';

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
        this.subPatient = this.service.getPatient(this.patientId).subscribe(data => {
            this.item = data;
            this.model = data.therapy ? data.therapy : {};
            this.loading = false;
            this.procedureStarted = ('ON_PROCEDURE' == this.item.talon.activity)
            setTimeout(() => { this.canvasInit() }, 0);
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
            if (p.selected) this.model.assignments.push({procedureId: p.id, desc: '', picture: this.canvasBuffer }) 
        });
        
        this.subProcedure = this.service.executeProcedure(this.item.talon.id, this.model).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.router.navigate(['workplace']);
        });
    }
}
