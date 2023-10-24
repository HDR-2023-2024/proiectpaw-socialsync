import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarLoggendComponent } from './navbar-loggend/navbar-loggend.component';
import { RouterModule, Routes } from '@angular/router';
import { NavbarDisconnectedComponent } from './navbar-disconnected/navbar-disconnected.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { NavbarHorizontallyComponent } from './navbar-horizontally/navbar-horizontally.component';
import { NavbarHorizontallyItemComponent } from './navbar-horizontally-item/navbar-horizontally-item.component';
import { ShortPostComponent } from './short-post/short-post.component';
import { CarouselComponent } from './carousel/carousel.component';
import { PostComponent } from './post/post.component';
import { CreatePostComponent } from './create-post/create-post.component';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { TestComponent } from './test/test.component';
import { HomeMenuComponent } from './home-menu/home-menu.component';
import {MatIconModule} from '@angular/material/icon';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';

library.add(fas);

const routes: Routes = [
  { path: 'home', component: HomeComponent }  ,
  { path: 'home-menu', component: HomeMenuComponent }  ,
  { path: '', component: HomeComponent }  ,
  { path: 'post', component: PostComponent }  ,
  { path: 'test', component: TestComponent }  ,
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarLoggendComponent,
    NavbarDisconnectedComponent,
    HomeComponent,
    AboutComponent,
    ContactComponent,
    NavbarHorizontallyComponent,
    NavbarHorizontallyItemComponent,
    ShortPostComponent,
    CarouselComponent,
    PostComponent,
    CreatePostComponent,
    TestComponent,
    HomeMenuComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    AngularEditorModule,
    HttpClientModule,
    FormsModule,
    FontAwesomeModule
  ],
  exports: [RouterModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
