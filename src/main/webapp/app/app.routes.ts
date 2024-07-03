import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { UserListComponent } from './user/user-list.component';
import { UserAddComponent } from './user/user-add.component';
import { UserEditComponent } from './user/user-edit.component';
import { PatientListComponent } from './patient/patient-list.component';
import { PatientAddComponent } from './patient/patient-add.component';
import { PatientEditComponent } from './patient/patient-edit.component';
import { AddressListComponent } from './address/address-list.component';
import { AddressAddComponent } from './address/address-add.component';
import { AddressEditComponent } from './address/address-edit.component';
import { AuthenticationComponent } from './security/authentication.component';
import { ErrorComponent } from './error/error.component';
import { AuthenticationService, ADMIN, USER } from 'app/security/authentication.service';


export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: $localize`:@@home.index.headline:Welcome to your new app!`,
    data: {
      roles: [ADMIN, USER]
    }
  },
  {
    path: 'users',
    component: UserListComponent,
    title: $localize`:@@user.list.headline:Users`,
    data: {
      roles: [ADMIN]
    }
  },
  {
    path: 'users/add',
    component: UserAddComponent,
    title: $localize`:@@user.add.headline:Add User`,
    data: {
      roles: [ADMIN]
    }
  },
  {
    path: 'users/edit/:id',
    component: UserEditComponent,
    title: $localize`:@@user.edit.headline:Edit User`,
    data: {
      roles: [ADMIN]
    }
  },
  {
    path: 'patients',
    component: PatientListComponent,
    title: $localize`:@@patient.list.headline:Patients`,
    data: {
      roles: [ADMIN, USER]
    }
  },
  {
    path: 'patients/add',
    component: PatientAddComponent,
    title: $localize`:@@patient.add.headline:Add Patient`,
    data: {
      roles: [ADMIN]
    }
  },
  {
    path: 'patients/edit/:id',
    component: PatientEditComponent,
    title: $localize`:@@patient.edit.headline:Edit Patient`,
    data: {
      roles: [ADMIN]
    }
  },
  {
    path: 'addresses',
    component: AddressListComponent,
    title: $localize`:@@address.list.headline:Addresses`,
    data: {
      roles: [ADMIN]
    }
  },
  {
    path: 'addresses/add',
    component: AddressAddComponent,
    title: $localize`:@@address.add.headline:Add Address`,
    data: {
      roles: [ADMIN]
    }
  },
  {
    path: 'addresses/edit/:id',
    component: AddressEditComponent,
    title: $localize`:@@address.edit.headline:Edit Address`,
    data: {
      roles: [ADMIN]
    }
  },
  {
    path: 'login',
    component: AuthenticationComponent,
    title: $localize`:@@authentication.login.headline:Login`
  },
  {
    path: 'error',
    component: ErrorComponent,
    title: $localize`:@@error.headline:Error`
  },
  {
    path: '**',
    component: ErrorComponent,
    title: $localize`:@@notFound.headline:Page not found`
  }
];

// add authentication check to all routes
for (const route of routes) {
  route.canActivate = [(route: ActivatedRouteSnapshot) => inject(AuthenticationService).checkAccessAllowed(route)];
}
