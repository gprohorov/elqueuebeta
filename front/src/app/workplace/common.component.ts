import { Component, OnInit, OnDestroy, ElementRef, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Patient } from '../_models/index';
import { AlertService, WorkplaceCommonService } from '../_services/index';

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
    subPatient: Subscription;
    subProcedure: Subscription;

    item: any;
    talonId: string;
    zones: number = 1;
    patientId: string;
    procedureId: number;
    procedureStarted: boolean = false;
    model: any = { comment: '' };

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private service: WorkplaceCommonService
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
    }

    load() {
        this.loading = true;
        this.subPatient = this.service.getPatient(this.patientId, this.procedureId).subscribe(data => {
            this.item = data;
            this.loading = false;
            this.procedureStarted = ('ON_PROCEDURE' == this.item.talon.activity)

            setTimeout(() => {
                this.canvasInit();
                const procedure = this.item.patient.therapy.assignments.find(x => { 
                    return (x.procedureId == this.procedureId) ? x : false; 
                });
                if (procedure && procedure.picture) {
                    procedure.picture.forEach(x => { this.drawOnCanvas(x[0], x[1]) });
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
        this.subProcedure = this.service.executeProcedure(this.item.talon.id, this.zones).subscribe(data => {
            this.alertService.success('Процедуру завершено.');
            this.router.navigate(['workplace']);
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
