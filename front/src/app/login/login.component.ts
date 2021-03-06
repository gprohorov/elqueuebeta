﻿import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { config } from '../../config';
import { AlertService, AuthService } from '../_services/index';

@Component({
    moduleId: module.id,
    templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {
  
    model: any = {};
    loading = false;
    returnUrl: string;
    project: string;
     
    constructor(
        private router: Router,
        private authService: AuthService,
        private alertService: AlertService) {
      
        switch(config.project) {
          case 'mg':
            this.project = 'МИГОВО';
            break;
          case 'kl': 
            this.project = 'КЛІШКІВЦІ';
            break;
          case 'cv':
          default:
            this.project = 'ЧЕРНІВЦІ';
            break;
        }
        this.authService.deAuth();
    }

    ngOnInit() {
        this.authService.deAuth();
    }

    login() {
        this.loading = true;
        this.authService.attemptAuth(this.model.username, this.model.password).subscribe(
            data => {
                this.authService.setAuth(data);
                this.router.navigate(['']);
            },
            error => {
                const mes = error.status === 401 ? 'Помилка авторизації' : 'Помилка сервера';
                this.alertService.error(mes);
                this.loading = false;
            }
        );
    }
}
