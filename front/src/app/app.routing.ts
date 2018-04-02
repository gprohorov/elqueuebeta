import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './_guards/index';

import { UsersComponent } from './users/users.component';
import { PersonListComponent } from './person/list.component';
import { PersonFormComponent } from './person/form.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';


const appRoutes: Routes = [
    { path: '', redirectTo: 'persons', pathMatch: 'full', canActivate: [AuthGuard] },
    { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
    
    { path: 'persons', component: PersonListComponent, canActivate: [AuthGuard] },
    { path: 'person-form', component: PersonFormComponent, canActivate: [AuthGuard] },
    
    { path: 'doctors', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'doctor-form', component: HomeComponent, canActivate: [AuthGuard] },
    
    { path: 'procedures', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'procedure-form', component: HomeComponent, canActivate: [AuthGuard] },
        
    { path: 'patients', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'patient-form', component: HomeComponent, canActivate: [AuthGuard] },
    
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);