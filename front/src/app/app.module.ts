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
    WorkplaceMainService,
    WorkplaceCommonService,
    WorkplaceDiagnosticService
} from './_services/index';

import { LoginComponent } from './login/login.component';
import { NavComponent } from './nav/nav.component';
import { HomeComponent } from './home/home.component';

import { HotelMainComponent } from './hotel/main.component';

import { PatientIncomeModalComponent } from './patient/income.modal.component';
import { PatientAssignProcedureModalComponent } from './patient/assign-procedure.modal.component';
import { PatientListComponent } from './patient/list.component';
import { PatientFormComponent } from './patient/form.component';

import { DoctorListComponent } from './doctor/list.component';
import { DoctorFormComponent } from './doctor/form.component';

import { ProcedureListComponent } from './procedure/list.component';
import { ProcedureFormComponent } from './procedure/form.component';

import { PatientsQueueListComponent } from './patients-queue/list.component';

import { ProceduresQueueListComponent } from './procedures-queue/list.component';

import { 
    WorkplaceMainComponent,
    WorkplaceCommonComponent,
    WorkplaceDiagnosticComponent
} from './workplace/index';

const appRoutes: Routes = [
    {
        path: '', component: HomeComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: {
            permissions: {
                except: ['ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_DOCTOR'],
                redirectTo: {
                    ROLE_SUPERADMIN: 'patients-queue',
                    ROLE_ADMIN: 'patients-queue',
                    ROLE_DOCTOR: 'workplace',
                    default: 'login'
                }
            }
        }
    },
    { path: 'login', component: LoginComponent },

    {
        path: 'hotel', component: HotelMainComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'], redirectTo: 'login' } }
    },
    
    {
        path: 'patients', component: PatientListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'patient-form', component: PatientFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'], redirectTo: 'login' } }
    },

    {
        path: 'doctors', component: DoctorListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'doctor-form', component: DoctorFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'], redirectTo: 'login' } }
    },

    {
        path: 'procedures', component: ProcedureListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'procedure-form', component: ProcedureFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'], redirectTo: 'login' } }
    },

    {
        path: 'patients-queue', component: PatientsQueueListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'], redirectTo: 'login' } }
    },

    {
        path: 'procedures-queue', component: ProceduresQueueListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'], redirectTo: 'login' } }
    },

    {
        path: 'workplace', component: WorkplaceMainComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_DOCTOR'], redirectTo: 'login' } }
    },
    {
        path: 'workplace/common/:patientId/:procedureId', component: WorkplaceCommonComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_DOCTOR'], redirectTo: 'login' } }
    },
    {
        path: 'workplace/diagnostic/:patientId', component: WorkplaceDiagnosticComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_DOCTOR'], redirectTo: 'login' } }
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
        HotelMainComponent,
        PatientIncomeModalComponent,
        PatientAssignProcedureModalComponent,
        PatientListComponent, PatientFormComponent,
        DoctorListComponent, DoctorFormComponent,
        ProcedureListComponent, ProcedureFormComponent,
        PatientsQueueListComponent,
        ProceduresQueueListComponent,
        WorkplaceMainComponent,
        WorkplaceCommonComponent,
        WorkplaceDiagnosticComponent
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
        WorkplaceMainService,
        WorkplaceCommonService,
        WorkplaceDiagnosticService,
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
    ],
    entryComponents: [PatientIncomeModalComponent, PatientAssignProcedureModalComponent],
    schemas: [NO_ERRORS_SCHEMA],
    bootstrap: [AppComponent]
})

export class AppModule { }
