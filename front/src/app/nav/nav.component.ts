import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../_models/index';
import { UserService, AuthenticationService, UtilService, AlertService } from '../_services/index';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  currentUser: User;

  constructor(public authenticationService: AuthenticationService,
              private userService: UserService,
              private utilService: UtilService,
              private alertService: AlertService,
              private router: Router
            ) {
    if (this.authenticationService.isLoggedIn()) {
      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }
  }

  resetDB(id: number) {
    if (confirm('Reset DB?')) {
      this.utilService.resetDB().subscribe(() => {
         this.alertService.success('DB has been reseted.', true);
         this.router.navigate(['/']);
      });
    }
  }

  ngOnInit() {
  }

}
