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
import { FullPostComponent } from './full-post/full-post.component';
import { FullPostPageComponent } from './full-post-page/full-post-page.component';
import { CommentsComponent } from './comments/comments.component';
import { SavedPostsComponent } from './saved-posts/saved-posts.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { RegistrationSuccessfulComponent } from './registration-successful/registration-successful.component';
import { InternalServerErrorComponent } from './internal-server-error/internal-server-error.component';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { LinksComponent } from './links/links.component';
import { CommunityComponent } from './community/community.component';
import { CommunityDetailsComponent } from './community-details/community-details.component';
import { ProfileCardComponent } from './profile-card/profile-card.component';
import { ProfileNavComponent } from './profile-nav/profile-nav.component';
import { ProfileComponent } from './profile/profile.component';
import { ProfilePostComponent } from './profile-post/profile-post.component';
import { ProfileLinkProfileComponent } from './profile-link-profile/profile-link-profile.component';
import { ProfileLinkPostsComponent } from './profile-link-posts/profile-link-posts.component';
import { ProfileLinkCommentsComponent } from './profile-link-comments/profile-link-comments.component';
import { ProfileLinkSavedComponent } from './profile-link-saved/profile-link-saved.component';
import { LoginComponent } from './login/login.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { ProfileCommentComponent } from './profile-comment/profile-comment.component';
import { CommunityEditComponent } from './community-edit/community-edit.component';
import { HelpSiteComponent } from './help-site/help-site.component';
import { TestareStorageComponent } from './testare-storage/testare-storage.component';
import { ViewTopicsComponent } from './view-topics/view-topics.component';
import { TopicShortComponent } from './topic-short/topic-short.component';
import { CommunityCreateComponent } from './community-create/community-create.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent }, // ok
  { path: 'home-menu', component: HomeMenuComponent }, // ok
  { path: '', component: HomeComponent }, // ok
  { path: 'post', component: PostComponent }, // ok
  { path: 'about', component: AboutComponent }, // ok
  { path: 'contact', component: ContactComponent }, // ok
  { path: 'saved-posts', component: SavedPostsComponent }, // ok
  { path: 'not-found', component: NotFoundComponent },  // OK
  { path: 'registration-successful', component: RegistrationSuccessfulComponent },  // ok
  { path: 'internal-server-error', component: InternalServerErrorComponent }, // ok
  { path: 'unauthorized', component: UnauthorizedComponent },  // ok
  { path: "full-post/:id", component: FullPostPageComponent },
  { path: "links", component: LinksComponent },
  { path: "community", component: CommunityComponent },
  { path: "p-c", component: ProfileCardComponent },
  { path: "p-n", component: ProfileNavComponent },
  { path: "p", component: ProfileComponent },
  { path: "see-profile", component: ProfileLinkProfileComponent },
  { path: "see-posts", component: ProfileLinkPostsComponent },
  { path: "login", component: LoginComponent },
  { path: "create-account", component: CreateAccountComponent },
  { path: "see-posts", component: ProfileLinkPostsComponent},
  { path: "p-com", component: ProfileCommentComponent},
  { path: "c-e", component: CommunityEditComponent},
  { path: "help", component: HelpSiteComponent},
  { path: "testare-storage", component: TestareStorageComponent},
  { path: "view-topics", component: ViewTopicsComponent},
  { path: "create-topics", component: CommunityCreateComponent}
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
    FullPostComponent,
    FullPostPageComponent,
    CommentsComponent,
    SavedPostsComponent,
    NotFoundComponent,
    RegistrationSuccessfulComponent,
    InternalServerErrorComponent,
    UnauthorizedComponent,
    LinksComponent,
    CommunityComponent,
    CommunityDetailsComponent,
    ProfileCardComponent,
    ProfileNavComponent,
    ProfileComponent,
    ProfilePostComponent,
    ProfileLinkProfileComponent,
    ProfileLinkPostsComponent,
    ProfileLinkCommentsComponent,
    ProfileLinkSavedComponent,
    LoginComponent,
    CreateAccountComponent,
    ProfileCommentComponent,
    CommunityEditComponent,
    HelpSiteComponent,
    TestareStorageComponent,
    ViewTopicsComponent,
    TopicShortComponent,
    CommunityCreateComponent
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
