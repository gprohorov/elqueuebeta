import { Component, OnInit } from '@angular/core';
import { AuthService } from './_services/index';
import { NgxPermissionsService } from 'ngx-permissions';

@Component({
    moduleId: module.id,
    selector: 'app-root',
    templateUrl: 'app.component.html'
})

export class AppComponent implements OnInit {
  
  constructor(private permissionsService: NgxPermissionsService, public authService: AuthService) { }
  
  ngOnInit(): void {
      const user: any = this.authService.getUserInfo();
      if (user && user.authorities) {
          this.permissionsService.loadPermissions(user.authorities);
      }
  }
}
