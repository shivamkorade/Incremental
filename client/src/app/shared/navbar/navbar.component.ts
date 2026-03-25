import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  role: string | null = null;

  constructor(private router: Router) {}

  ngOnInit(): void {
    // ✅ Read role initially
    this.role = localStorage.getItem('role');
    console.log('Navbar initial role:', this.role);

    // ✅ Re-read role on every route change
    // This ensures navbar updates after login
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.role = localStorage.getItem('role');
      console.log('Navbar role updated:', this.role);
    });
  }

  bookAppointment(): void {
    this.router.navigate(['/mediconnect/appointment/create']);
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/auth/login']);
  }
}