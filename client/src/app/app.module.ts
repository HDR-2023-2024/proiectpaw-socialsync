import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarLoggendComponent } from './navbar-loggend/navbar-loggend.component';
import { RouterModule, Routes } from '@angular/router';
import { NavbarDisconnectedComponent } from './navbar-disconnected/navbar-disconnected.component';
import { PostShortComponent } from './post-short/post-short.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { NavbarHorizontallyComponent } from './navbar-horizontally/navbar-horizontally.component';
import { NavbarHorizontallyItemComponent } from './navbar-horizontally-item/navbar-horizontally-item.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent }  ,
  { path: '', component: HomeComponent }  
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarLoggendComponent,
    NavbarDisconnectedComponent,
    PostShortComponent,
    HomeComponent,
    AboutComponent,
    ContactComponent,
    NavbarHorizontallyComponent,
    NavbarHorizontallyItemComponent,
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
