import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

import { NgxPermissionsModule, NgxPermissionsGuard } from 'ngx-permissions';
import { NgxMasonryModule } from 'ng5-masonry';
import { ModalDialogModule } from 'ngx-modal-dialog';

import { AppComponent } from './app.component';
import { AlertComponent, SortableTableDirective, SortableColumnComponent } from './_directives/index';
import { AuthGuard } from './_guards/index';
import { JwtInterceptor } from './_helpers/index';
import { TokenStorage, UserStorage } from './_storage/index';
import {
    AlertService,
    AuthService,
    UtilService,
    SortService,
    PatientService,
    DoctorService,
    ProcedureService,
    PatientsQueueService,
    DoctorInterfaceService
} from './_services/index';

import { LoginComponent } from './login/login.component';
import { NavComponent } from './nav/nav.component';
import { HomeComponent } from './home/home.component';

import { PatientAssignProcedureModalComponent } from './patient/assign-procedure.modal.component';
import { PatientListComponent } from './patient/list.component';
import { PatientFormComponent } from './patient/form.component';

import { DoctorListComponent } from './doctor/list.component';
import { DoctorFormComponent } from './doctor/form.component';

import { ProcedureListComponent } from './procedure/list.component';
import { ProcedureFormComponent } from './procedure/form.component';

import { PatientsQueueListComponent } from './patients-queue/list.component';

import { ProceduresQueueListComponent } from './procedures-queue/list.component';

import { DoctorInterfaceMainComponent } from './doctor-interface/main.component';

const appRoutes: Routes = [
    {
        path: '', component: HomeComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: {
            permissions: {
                except: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_MASSAGE'],
                redirectTo: {
                    ROLE_ADMIN: 'patients',
                    ROLE_USER: 'patients',
                    ROLE_MASSAGE: 'doctor-interface',
                    default: 'login'
                }
            }
        }
    },
    { path: 'login', component: LoginComponent },

    {
        path: 'patients', component: PatientListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo: 'login' } }
    },
    {
        path: 'patient-form', component: PatientFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo: 'login' } }
    },

    {
        path: 'doctors', component: DoctorListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo: 'login' } }
    },
    {
        path: 'doctor-form', component: DoctorFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo: 'login' } }
    },

    {
        path: 'procedures', component: ProcedureListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo: 'login' } }
    },
    {
        path: 'procedure-form', component: ProcedureFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo: 'login' } }
    },

    {
        path: 'patients-queue', component: PatientsQueueListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo: 'login' } }
    },

    {
        path: 'procedures-queue', component: ProceduresQueueListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo: 'login' } }
    },

    {
        path: 'doctor-interface', component: DoctorInterfaceMainComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_MASSAGE'], redirectTo: 'login' } }
    },

    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        NgxMasonryModule,
        NgbModule.forRoot(),
        NgxPermissionsModule.forRoot(),
        ModalDialogModule.forRoot(),
        RouterModule.forRoot(appRoutes)
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        AlertComponent,
        SortableTableDirective,
        SortableColumnComponent,
        LoginComponent,
        NavComponent,
        PatientAssignProcedureModalComponent,
        PatientListComponent, PatientFormComponent,
        DoctorListComponent, DoctorFormComponent,
        ProcedureListComponent, ProcedureFormComponent,
        PatientsQueueListComponent,
        ProceduresQueueListComponent,
        DoctorInterfaceMainComponent
    ],
    providers: [
        AuthGuard,
        NgxPermissionsGuard,
        AlertService,
        AuthService,
        TokenStorage,
        UserStorage,
        UtilService,
        SortService,
        PatientService,
        DoctorService,
        ProcedureService,
        PatientsQueueService,
        DoctorInterfaceService,
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
    ],
    entryComponents: [PatientAssignProcedureModalComponent],
    schemas: [NO_ERRORS_SCHEMA],
    bootstrap: [AppComponent]
})

export class AppModule { }
