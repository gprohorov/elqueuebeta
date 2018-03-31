import { Routes, RouterModule } from '@angular/router';

import { UsersComponent } from './users/users.component';
import { PersonsComponent } from './persons/persons.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/index';
import { RegisterComponent } from './register/index';
import { AuthGuard } from './_guards/index';

const appRoutes: Routes = [
    { path: '', redirectTo: 'persons', pathMatch: 'full', canActivate: [AuthGuard] },
    { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'users', component: UsersComponent, canActivate: [AuthGuard] },
    { path: 'persons', component: PersonsComponent, canActivate: [AuthGuard] },
    { path: 'queue', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);