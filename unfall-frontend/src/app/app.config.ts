import {
  ApplicationConfig, provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection
} from '@angular/core';
import {provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {API_BASE_URL} from './core/tokens/api-base-url.token';
import {provideRouter} from '@angular/router';
import {routes} from './app';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideHttpClient(withInterceptorsFromDi()),
    {provide: API_BASE_URL, useValue: '/api'}
  ]
};
