import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

import { NgxPermissionsModule, NgxPermissionsGuard } from 'ngx-permissions';
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
import { HomeComponent } from './home/home.component';

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
    { path: '', component: HomeComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: {
            permissions: {
                except: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_MASSAGE'],
                redirectTo: {
                    ROLE_ADMIN:     'procedures-queue',
                    ROLE_USER:      'procedures-queue',
                    ROLE_MASSAGE:   'doctor-interface-massage',
                    default:        'login'
                }
            }
        }
    },
    { path: 'login', component: LoginComponent },

    { path: 'persons', component: PersonListComponent, 
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo:  'login' } } 
    },
    { path: 'person-form', component: PersonFormComponent, 
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo:  'login' } } 
    },

    { path: 'doctors', component: DoctorListComponent, 
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo:  'login' } } 
    },
    { path: 'doctor-form', component: DoctorFormComponent, 
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo:  'login' } } 
    },

    { path: 'procedures', component: ProcedureListComponent, 
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo:  'login' } } 
    },
    { path: 'procedure-form', component: ProcedureFormComponent, 
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo:  'login' } } 
    },

    { path: 'patients-queue', component: PatientsQueueListComponent, 
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo:  'login' } } 
    },

    { path: 'procedures-queue', component: ProceduresQueueListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER'], redirectTo:  'login' } } 
    },

    { path: 'doctor-interface-massage', component: DoctorInterfaceMassageComponent, 
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_MASSAGE'], redirectTo:  'login' } } 
    },
    { path: 'doctor-interface-diagnose', component: DoctorInterfaceDiagnoseComponent, 
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_DIAG'], redirectTo:  'login' } } 
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
        NgxPermissionsGuard,
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
