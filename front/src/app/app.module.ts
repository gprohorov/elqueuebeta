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
import { JwtInterceptor, LoaderComponent } from './_helpers/index';
import { CashType, TokenStorage, UserStorage } from './_storage/index';
import {
    AlertService,
    AuthService,
    UserService,
    SettingsService,
    SortService,
    PatientService,
    DoctorService,
    ProcedureService,
    PatientsQueueService,
    StatisticService,
    WorkplaceMainService,
    WorkplaceCommonService,
    WorkplaceDiagnosticService,
    HotelService,
    FinanceService,
    OutcomeService
} from './_services/index';

import { LoginComponent } from './login/login.component';
import { NavComponent } from './nav/nav.component';
import { HomeComponent } from './home/home.component';

import { SettingsFormComponent } from './settings/form.component';

import { HotelMainComponent } from './hotel/main.component';

import { PatientIncomeModalComponent } from './patient/income.modal.component';
import { PatientAssignProcedureModalComponent } from './patient/assign-procedure.modal.component';
import { PatientAssignHotelModalComponent } from './patient/assign-hotel.modal.component';
import { PatientAssignProceduresOnDateModalComponent } from './patient/assign-procedures-on-date.modal.component';
import { PatientListComponent } from './patient/list.component';
import { PatientFormComponent } from './patient/form.component';

import { UserListComponent } from './user/list.component';
import { UserFormComponent } from './user/form.component';

import { DoctorListComponent } from './doctor/list.component';
import { DoctorFormComponent } from './doctor/form.component';

import { ProcedureListComponent } from './procedure/list.component';
import { ProcedureFormComponent } from './procedure/form.component';

import { PatientsQueueListComponent } from './patients-queue/list.component';

import { ProceduresQueueListComponent } from './procedures-queue/list.component';

import { CreatePatientModalComponent } from './patient/create-patient.modal.component';

import { GeneralStatisticFromToComponent } from './statistic/general-statistic-from-to.component';
import { CashSummaryComponent } from './statistic/cash-summary.component';
import { DoctorsProceduresFromToComponent } from './statistic/doctors-procedures-from-to.component';
import { ProceduresStatisticsComponent } from './statistic/procedures-statistics.component';
import { PatientsDebetorsComponent } from './statistic/patients-debetors.component';
import { PatientsDebetorsExtComponent } from './statistic/patients-debetors-ext.component';
import { PatientStatisticsComponent } from './statistic/patient-statistics.component';
import { PatientsStatisticsComponent } from './statistic/patients-statistics.component';
import { DoctorsStatisticsComponent } from './statistic/doctors-statistics.component';

import { ReceiptComponent } from './receipt/main.component';
import { CheckComponent } from './check/main.component';

import { FinanceSalaryComponent } from './finance/salary.component';
import { FinanceSalarySummaryComponent } from './finance/salary-summary.component';
import { DoctorSalaryHistoryModalComponent } from './finance/doctor-salary-history.modal.component';
import { GiveSalaryModalComponent } from './finance/give-salary.modal.component';
import { SetSalaryModalComponent } from './finance/set-salary.modal.component';
import { KassaTozeroModalComponent } from './finance/kassa-tozero.modal.component';
import { KassaAddOutcomeModalComponent } from './finance/kassa-add-outcome.modal.component';

import { FinanceOutcomeComponent } from './finance/outcome.component';
import { FinanceOutcomeCategoryModalComponent } from './finance/outcome/category.modal.component';
import { FinanceOutcomeItemModalComponent } from './finance/outcome/item.modal.component';

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
                except: ['ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_HEAD'],
                redirectTo: {
                    ROLE_SUPERADMIN: 'procedures-queue',
                    ROLE_ADMIN: 'patients-queue',
                    ROLE_DOCTOR: 'workplace',
                    default: 'login'
                }
            }
        }
    },
    { path: 'login', component: LoginComponent },
    { path: 'home', component: HomeComponent },

    {
        path: 'hotel', component: HotelMainComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN'], redirectTo: 'login' } }
    },

    {
        path: 'patients', component: PatientListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_HEAD'], redirectTo: 'login' } }
    },
    {
        path: 'patient-form', component: PatientFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_HEAD'], redirectTo: 'login' } }
    },

    {
        path: 'settings', component: SettingsFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    
    {
        path: 'users', component: UserListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'user-form', component: UserFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    
    {
        path: 'doctors', component: DoctorListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'doctor-form', component: DoctorFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },

    {
        path: 'procedures', component: ProcedureListComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'procedure-form', component: ProcedureFormComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
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
        path: 'statistic/general-from-to', component: GeneralStatisticFromToComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'statistic/cash-summary', component: CashSummaryComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'statistic/doctors-procedures-from-to', component: DoctorsProceduresFromToComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'statistic/procedures-statistics', component: ProceduresStatisticsComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'statistic/patients-debetors', component: PatientsDebetorsComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'statistic/patients-debetors-ext', component: PatientsDebetorsExtComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'statistic/patient/:patientId', component: PatientStatisticsComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'statistic/doctors', component: DoctorsStatisticsComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'statistic/patients', component: PatientsStatisticsComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },

    {
        path: 'finance/salary', component: FinanceSalaryComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'finance/salary-summary', component: FinanceSalarySummaryComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },
    {
        path: 'finance/outcome', component: FinanceOutcomeComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN'], redirectTo: 'login' } }
    },

    {
        path: 'workplace', component: WorkplaceMainComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_DOCTOR'], redirectTo: 'login' } }
    },
    {
        path: 'workplace/common/:patientId/:procedureId', component: WorkplaceCommonComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_DOCTOR'], redirectTo: 'login' } }
    },
    {
        path: 'workplace/diagnostic/:patientId', component: WorkplaceDiagnosticComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_DOCTOR'], redirectTo: 'login' } }
    },
    
    {
        path: 'receipt/:patientId', component: ReceiptComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_HEAD'], redirectTo: 'login' } }
    },
    {
        path: 'check/:patientId', component: CheckComponent,
        canActivate: [AuthGuard, NgxPermissionsGuard],
        data: { permissions: { only: ['ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_HEAD'], redirectTo: 'login' } }
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
        RouterModule.forRoot(appRoutes, {useHash: true})
    ],
    exports: [
        LoaderComponent
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        AlertComponent,
        LoaderComponent,
        SortableTableDirective,
        SortableColumnComponent,
        LoginComponent,
        SettingsFormComponent,
        NavComponent,
        HotelMainComponent,
        PatientIncomeModalComponent,
        PatientAssignProcedureModalComponent,
        PatientAssignProceduresOnDateModalComponent,
        PatientAssignHotelModalComponent,
        CreatePatientModalComponent,
        PatientListComponent, PatientFormComponent,
        DoctorListComponent, DoctorFormComponent,
        UserListComponent, UserFormComponent,
        ProcedureListComponent, ProcedureFormComponent,
        PatientsQueueListComponent,
        ProceduresQueueListComponent,
        GeneralStatisticFromToComponent,
        CashSummaryComponent,
        DoctorsProceduresFromToComponent,
        ProceduresStatisticsComponent,
        PatientsDebetorsComponent,
        PatientsDebetorsExtComponent,
        PatientStatisticsComponent,
        PatientsStatisticsComponent,
        DoctorsStatisticsComponent,
        ReceiptComponent,
        CheckComponent,
        WorkplaceMainComponent,
        WorkplaceCommonComponent,
        WorkplaceDiagnosticComponent,
        FinanceSalaryComponent,
        FinanceSalarySummaryComponent,
        DoctorSalaryHistoryModalComponent,
        GiveSalaryModalComponent,
        SetSalaryModalComponent,
        KassaTozeroModalComponent,
        KassaAddOutcomeModalComponent,
        FinanceOutcomeComponent,
        FinanceOutcomeCategoryModalComponent,
        FinanceOutcomeItemModalComponent
    ],
    providers: [
        AuthGuard,
        NgxPermissionsGuard,
        AlertService,
        AuthService,
        UserService,
        TokenStorage,
        UserStorage,
        SettingsService,
        SortService,
        PatientService,
        DoctorService,
        ProcedureService,
        PatientsQueueService,
        StatisticService,
        WorkplaceMainService,
        WorkplaceCommonService,
        WorkplaceDiagnosticService,
        HotelService,
        FinanceService,
        OutcomeService,
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
    ],
    entryComponents: [
        PatientIncomeModalComponent,
        PatientAssignProcedureModalComponent,
        PatientAssignProceduresOnDateModalComponent,
        PatientAssignHotelModalComponent,
        CreatePatientModalComponent,
        GiveSalaryModalComponent,
        DoctorSalaryHistoryModalComponent,
        SetSalaryModalComponent,
        KassaTozeroModalComponent,
        KassaAddOutcomeModalComponent,
        FinanceOutcomeCategoryModalComponent,
        FinanceOutcomeItemModalComponent        
    ],
    schemas: [NO_ERRORS_SCHEMA],
    bootstrap: [AppComponent]
})

export class AppModule { }
