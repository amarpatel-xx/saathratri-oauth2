import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { OAuthModule, OAuthService, OAuthStorage } from 'angular-oauth2-oidc';
import { FormsModule } from '@angular/forms';
import {
  LOCAL_STORAGE_TOKEN,
  localStorageFactory,
} from './services/local-storage.service';
import { isDefined } from './commons/commons';
import { from, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { provideHttpClient, withInterceptorsFromDi, HTTP_INTERCEPTORS } from '@angular/common/http';
import { environment } from '../environments/environment';

// Components:
import { HomeComponent } from './components/home/home.component';
import { UserComponent } from './components/user/user.component';
import { OrganizationsComponent } from './components/organizations/organizations.component';
import { AddOnsComponent } from './components/add-ons/add-ons.component';

// Services:
import { OrganizationService } from './services/organization.service';
import { AddOnService } from './services/add-on.service';

@NgModule({
  declarations: [AppComponent, HomeComponent, UserComponent, OrganizationsComponent, AddOnsComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    OAuthModule.forRoot({
      resourceServer: {
        allowedUrls: [
          environment.apiUriOrganizationsService,
        ],
        sendAccessToken: true,
      },
    })
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: applicationInitializerFactory,
      deps: [OAuthService],
      multi: true
    },
    { provide: LOCAL_STORAGE_TOKEN, useFactory: localStorageFactory },
    { provide: OAuthStorage, useFactory: localStorageFactory },
    provideHttpClient(withInterceptorsFromDi()), // Replaces HttpClientModule
    OrganizationService,
    AddOnService
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

export function applicationInitializerFactory(oauthService: OAuthService) {
  return () =>
    new Promise<boolean>((resolve) => {
      configure();
      oauthService.setupAutomaticSilentRefresh();

      // Load Discovery Document and then try to login the user
      oauthService
        .loadDiscoveryDocument()
        .then(() => checkIdentity().subscribe(() => resolve(true)));

      function checkIdentity(): Observable<boolean> {
        if (isDefined(oauthService.getRefreshToken())) {
          return from(oauthService.refreshToken()).pipe(
            map(() => oauthService.hasValidAccessToken())
          );
        }

        return from(oauthService.tryLogin());
      }

      function configure() {
        oauthService.configure({
          // URL of the SPA to redirect the user to after login
          redirectUri: environment.redirectUri,
          // The SPA's id. The SPA is registered with this id at the auth-server
          clientId: environment.clientId,
          // set the scope for the permissions the client should request
          scope: environment.scope,
          // url for  /.well-known/openid-configuration endpoint
          issuer: environment.issuer,
          disablePKCE: true,
          //initialize the code flow
          responseType: environment.responseType,
          showDebugInformation: true,
        });
      }
    });
}
