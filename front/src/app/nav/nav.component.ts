import { Component, OnInit } from '@angular/core';
import { User } from '../_models/index';
import { UserService, AuthenticationService } from '../_services/index';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  currentUser: User;
    
  constructor(private userService: UserService, public authenticationService: AuthenticationService) {
    if (authenticationService.isLoggedIn()) this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
  }

}
