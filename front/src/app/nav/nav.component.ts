import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, UtilService, AlertService } from '../_services/index';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {

  user: any;

  constructor(public authService: AuthService,
              private utilService: UtilService,
              private alertService: AlertService,
              private router: Router
            ) {
    if (this.authService.isAuth()) {
      this.user = this.authService.getUserInfo();
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

}
