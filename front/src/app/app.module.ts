﻿import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

import { NgxMasonryModule } from 'ng5-masonry';

import { AppComponent } from './app.component';
import { AlertComponent, SortableTableDirective, SortableColumnComponent } from './_directives/index';
import { AuthGuard } from './_guards/index';
import { JwtInterceptor } from './_helpers/index';
import { TokenStorage, UserStorage } from './_storage/index';
import {  AlertService,
          AuthService,
          UtilService,
          SortService,
          PersonService,
          DoctorService,
          ProcedureService,
          PatientsQueueService,
          DoctorInterfaceService
       } from './_services/index';

import { LoginComponent } from './login/login.component';
import { NavComponent } from './nav/nav.component';

import { PersonListComponent } from './person/list.component';
import { PersonFormComponent } from './person/form.component';

import { DoctorListComponent } from './doctor/list.component';
import { DoctorFormComponent } from './doctor/form.component';

import { ProcedureListComponent } from './procedure/list.component';
import { ProcedureFormComponent } from './procedure/form.component';

import { PatientsQueueListComponent } from './patients-queue/list.component';

import { ProceduresQueueListComponent } from './procedures-queue/list.component';

import { DoctorInterfaceMassageComponent } from './doctor-interface/massage.component';
import { DoctorInterfaceDiagnoseComponent } from './doctor-interface/diagnose.component';

const appRoutes: Routes = [
    { path: '', redirectTo: 'procedures-queue', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },

    { path: 'persons', component: PersonListComponent, canActivate: [AuthGuard] },
    { path: 'person-form', component: PersonFormComponent, canActivate: [AuthGuard] },

    { path: 'doctors', component: DoctorListComponent, canActivate: [AuthGuard] },
    { path: 'doctor-form', component: DoctorFormComponent, canActivate: [AuthGuard] },

    { path: 'procedures', component: ProcedureListComponent, canActivate: [AuthGuard] },
    { path: 'procedure-form', component: ProcedureFormComponent, canActivate: [AuthGuard] },

    { path: 'patients-queue', component: PatientsQueueListComponent, canActivate: [AuthGuard] },

    { path: 'procedures-queue', component: ProceduresQueueListComponent, canActivate: [AuthGuard] },

    { path: 'doctor-interface-massage', component: DoctorInterfaceMassageComponent, canActivate: [AuthGuard] },
    { path: 'doctor-interface-diagnose', component: DoctorInterfaceDiagnoseComponent, canActivate: [AuthGuard] },

    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        NgxMasonryModule,
        NgbModule.forRoot(),
        RouterModule.forRoot(appRoutes)
    ],
    declarations: [
        AppComponent,
        AlertComponent,
        SortableTableDirective,
        SortableColumnComponent,
        LoginComponent,
        NavComponent,
        PersonListComponent, PersonFormComponent,
        DoctorListComponent, DoctorFormComponent,
        ProcedureListComponent, ProcedureFormComponent,
        PatientsQueueListComponent,
        ProceduresQueueListComponent,
        DoctorInterfaceMassageComponent,
        DoctorInterfaceDiagnoseComponent
    ],
    providers: [
        AuthGuard,
        AlertService,
        AuthService,
        TokenStorage,
        UserStorage,
        UtilService,
        SortService,
        PersonService,
        DoctorService,
        ProcedureService,
        PatientsQueueService,
        DoctorInterfaceService,
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
    ],
    schemas: [ NO_ERRORS_SCHEMA ],
    bootstrap: [AppComponent]
})

export class AppModule { }
