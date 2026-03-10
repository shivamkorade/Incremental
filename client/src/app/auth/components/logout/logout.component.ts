import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent {

  constructor(private router: Router) {}

  logout(): void {
    // clear token and any other user data
    localStorage.removeItem('token');
    localStorage.clear();

    // navigate to login page
    this.router.navigate(['/login']);
  }
}
