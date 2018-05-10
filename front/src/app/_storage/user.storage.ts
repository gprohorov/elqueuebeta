import { Injectable } from '@angular/core';

const USER_KEY = 'UserInfo';

@Injectable()
export class UserStorage {

  constructor() { }

  signOut() {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.clear();
  }

  public saveUser(user: object) {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): string {
    return JSON.parse(sessionStorage.getItem(USER_KEY));
  }
}
