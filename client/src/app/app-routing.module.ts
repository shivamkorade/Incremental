// import { NgModule } from '@angular/core';
// import { RouterModule, Routes } from '@angular/router';
// import { MediConnectModule } from './mediconnect/mediconnect.module';

// const routes: Routes = [
//   {
//     path: 'auth',
//     loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
//   },
//   {
//     path: 'mediconnect',
//     loadChildren: () => import('./mediconnect/mediconnect.module').then((m) => m.MediConnectModule),
//   },
//   {
//     path: '',
//     pathMatch: 'full',
//     redirectTo: '/auth',  // Redirect to 'auth' route by default
//   }
// ];

// @NgModule({
//   imports: [RouterModule.forRoot(routes)],
//   exports: [RouterModule],
// })
// export class AppRoutingModule {}


import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'mediconnect',
    loadChildren: () => import('./mediconnect/mediconnect.module').then((m) => m.MediConnectModule),
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/home',  // Redirect to 'auth' route by default
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}