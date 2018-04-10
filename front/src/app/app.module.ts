import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserXhr } from '@angular/http';

import { AppComponent } from './app.component';
import { Routes, RouterModule } from '@angular/router';

import { NgxMasonryModule } from 'ng5-masonry'; 
import { AlertComponent } from './_directives/index';
import { AuthGuard } from './_guards/index';
import { JwtInterceptor, fakeBackendProvider, CustExtBrowserXhr } from './_helpers/index';
import {  AlertService,
          AuthenticationService, 
          UserService, 
          PersonService, 
          DoctorService, 
          ProcedureService, 
          PatientsQueueService,
          DoctorInterfaceService
       } from './_services/index';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { NavComponent } from './nav/nav.component';

import { HomeComponent } from './home/home.component';
import { UsersComponent } from './users/users.component';

import { PersonListComponent } from './person/list.component';
import { PersonFormComponent } from './person/form.component';

import { DoctorListComponent } from './doctor/list.component';
import { DoctorFormComponent } from './doctor/form.component';

import { ProcedureListComponent } from './procedure/list.component';
import { ProcedureFormComponent } from './procedure/form.component';

import { PatientsQueueListComponent } from './patients-queue/list.component';

import { ProceduresQueueListComponent } from './procedures-queue/list.component';

import { DoctorInterfaceComponent } from './doctor-interface/main.component';
import { DoctorInterfaceProcedureComponent } from './doctor-interface/procedure.component';

const appRoutes: Routes = [
    { path: '', redirectTo: 'persons', pathMatch: 'full', canActivate: [AuthGuard] },
    { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
    
    { path: 'persons', component: PersonListComponent, canActivate: [AuthGuard] },
    { path: 'person-form', component: PersonFormComponent, canActivate: [AuthGuard] },
    
    { path: 'doctors', component: DoctorListComponent, canActivate: [AuthGuard] },
    { path: 'doctor-form', component: DoctorFormComponent, canActivate: [AuthGuard] },
    
    { path: 'procedures', component: ProcedureListComponent, canActivate: [AuthGuard] },
    { path: 'procedure-form', component: ProcedureFormComponent, canActivate: [AuthGuard] },
    
    { path: 'patients-queue', component: PatientsQueueListComponent, canActivate: [AuthGuard] },
    
    { path: 'procedures-queue', component: ProceduresQueueListComponent, canActivate: [AuthGuard] },

    { path: 'doctor-interface', component: DoctorInterfaceComponent, canActivate: [AuthGuard] },
    { path: 'doctor-interface-procedure', component: DoctorInterfaceProcedureComponent, canActivate: [AuthGuard] },
    
    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        NgxMasonryModule,
        RouterModule.forRoot(appRoutes)
    ], 
    declarations: [
        AppComponent,
        AlertComponent,
        HomeComponent,
        LoginComponent,
        RegisterComponent,
        NavComponent,
        UsersComponent,
        PersonListComponent, PersonFormComponent,
        DoctorListComponent, DoctorFormComponent,
        ProcedureListComponent, ProcedureFormComponent,
        PatientsQueueListComponent,
        ProceduresQueueListComponent,
        DoctorInterfaceComponent,
        DoctorInterfaceProcedureComponent
    ],
    providers: [
        AuthGuard,
        AlertService,
        AuthenticationService,
        UserService,
        PersonService,
        DoctorService,
        ProcedureService,
        PatientsQueueService,
        DoctorInterfaceService,
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        { provide: BrowserXhr, useClass: CustExtBrowserXhr },
        fakeBackendProvider
    ],
    schemas: [ NO_ERRORS_SCHEMA ],
    bootstrap: [AppComponent]
})

export class AppModule { }
