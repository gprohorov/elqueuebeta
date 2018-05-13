import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { AlertService, AuthService } from '../_services/index';

@Component({
    moduleId: module.id,
    templateUrl: 'login.component.html'
})

export class LoginComponent implements OnInit {
    model: any = {};
    loading = false;
    returnUrl: string;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private authService: AuthService,
        private alertService: AlertService) {
      this.authService.deAuth();
    }

    ngOnInit() {
        // reset login status
        this.authService.deAuth();
    }

    login() {
        this.loading = true;
        this.authService.attemptAuth(this.model.username, this.model.password)
            .subscribe(
                data => {
                    this.authService.setAuth(data);
                    console.log(data.authorities);
                    this.router.navigate(['']);
                },
                error => {
                    const mes = error.status === 401 
                          ? 'Помилка авторизації' 
                          : 'Помилка сервера';
                    this.alertService.error(mes);
                    this.loading = false;
                });
    }
}
