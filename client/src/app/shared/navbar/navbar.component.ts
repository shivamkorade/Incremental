// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-navbar',
//   templateUrl: './navbar.component.html',
//   styleUrls: ['./navbar.component.scss']
// })
// export class NavBarComponent {}


import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  role: string | null | undefined;

  constructor(private router: Router) { }

  ngOnInit(): void {
      console.log(localStorage.getItem("role"));
      this.role = localStorage.getItem("role");
  }

  logout(): void {
      localStorage.removeItem('token')
      this.router.navigate(["/auth"]);
  }

}