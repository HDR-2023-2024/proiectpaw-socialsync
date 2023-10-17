import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarLoggendComponent } from './navbar-loggend/navbar-loggend.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'navbar-logged', component: NavbarLoggendComponent }  /*Pentru teste*/
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarLoggendComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
