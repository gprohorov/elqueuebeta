import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { AlertService, AuthService } from '../_services/index';
import { TokenStorage } from "../_storage/index";

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
        private token: TokenStorage,
        private alertService: AlertService) {
      this.authService.deAuth();
    }

    ngOnInit() {
        // reset login status
        this.authService.deAuth();

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    login() {
        this.loading = true;
        this.authService.attemptAuth(this.model.username, this.model.password)
            .subscribe(
                data => {
                    this.token.saveToken(data.token);
                    this.router.navigate([this.returnUrl]);
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }
}
