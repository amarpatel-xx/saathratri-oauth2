import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './commons/auth.guard';
import { HomeComponent } from './components/home/home.component';
import { OrganizationsComponent } from './components/organizations/organizations.component';
import { AddOnsComponent } from './components/add-ons/add-ons.component';
import { Authority } from './commons/authority.constants';

const routes: Routes = [
  {
    path: '',
    canActivate: [AuthGuard], // Full route protection
    component: HomeComponent,
  },
  {
    path: 'organizations',
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [AuthGuard],
    component: OrganizationsComponent,
  },
  {
    path: 'add-ons',
    data: {
      authorities: [Authority.USER],
    },
    canActivate: [AuthGuard],
    component: AddOnsComponent,
  },
  { 
    path: '**', 
    redirectTo: '' 
  } // Wildcard route for 404 page
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
