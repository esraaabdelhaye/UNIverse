import { bootstrapApplication } from '@angular/platform-browser';
import { App } from './app/app';
import { routes } from './app/features/rep-dash-board/Views/red-dash-board/red-dash-board-routing-module';
import { provideRouter } from '@angular/router';
import { ApplicationConfig } from '@angular/core';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes)],
};

bootstrapApplication(App, appConfig).catch((err) => console.error(err));
