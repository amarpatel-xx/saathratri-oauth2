// auth.service.ts
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { jwtDecode } from 'jwt-decode';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private oauthService: OAuthService) {}

  signIn() {
    return this.oauthService.initLoginFlow();
  }

  signOut() {
    this.oauthService.logOut();
  }

  getDecodedAccessToken(): any {
    const token = this.oauthService.getAccessToken();
    if (token) {
      return jwtDecode(token);
    }
    return null;
  }

  // getUserInfo(): any {
  //   console.log("User Info 1: ", this.oauthService.getIdentityClaims());
  //   return this.oauthService.getIdentityClaims();
  // }

  getUserInfo(): any {
    if (this.oauthService.hasValidAccessToken()) {
      const claims = this.oauthService.getIdentityClaims();
  
      if (claims) {  
        if (claims['https://www.jhipster.tech/roles']) {
          return {
            username: claims.preferred_username,
            email: claims.email,
            roles: claims['https://www.jhipster.tech/roles']?.join(', '),
          };
        } else {
          return {
            username: claims.preferred_username,
            email: claims.email,
            roles: this.getDecodedAccessToken().realm_access.roles.join(', ') || 'No roles assigned',
            // Add other fields as necessary
          };
        }
      } else {
        console.warn("No claims found.");
        return null;
      }
    } else {
      console.warn("User is not logged in.");
      return null;
    }
  }  
}