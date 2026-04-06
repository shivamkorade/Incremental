import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  constructor(private router: Router) {}

  goToLogin(): void {
    this.router.navigate(['/auth/login']);
  }

  goToRegister(): void {
    this.router.navigate(['/auth/register']);
  }

  // Optional: redirect logged-in users directly to dashboard
  goToDashboardIfLoggedIn(): void {
    const role = localStorage.getItem('role');
    if (role) {
      this.router.navigate(['/mediconnect/dashboard']);
    }
  }
}