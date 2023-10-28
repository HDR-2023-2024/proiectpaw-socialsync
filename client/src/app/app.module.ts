import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
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
import { HomeMenuComponent } from './home-menu/home-menu.component';
import { SavedPostsComponent } from './saved-posts/saved-posts.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { RegistrationSuccessfulComponent } from './registration-successful/registration-successful.component';
import { InternalServerErrorComponent } from './internal-server-error/internal-server-error.component';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent }  ,
  { path: 'home-menu', component: HomeMenuComponent }  ,
  { path: '', component: HomeComponent }  ,
  { path: 'post', component: PostComponent }  ,
  { path: 'about', component: AboutComponent }  ,
  { path: 'contact', component: ContactComponent }  ,
  { path: 'saved-posts', component: SavedPostsComponent }  ,
  { path: 'not-found', component: NotFoundComponent }  ,
  { path: 'registration-successful', component: RegistrationSuccessfulComponent }  ,
  { path: 'internal-server-error', component: InternalServerErrorComponent }  ,
  { path: 'unauthorized', component: UnauthorizedComponent }  ,
];

@NgModule({
  declarations: [
    AppComponent,
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
    HomeMenuComponent,
    SavedPostsComponent,
    NotFoundComponent,
    RegistrationSuccessfulComponent,
    InternalServerErrorComponent,
    UnauthorizedComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    AngularEditorModule,
    HttpClientModule,
    FormsModule,
  ],
  exports: [RouterModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
