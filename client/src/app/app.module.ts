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
import { NotFoundComponent } from './not-found/not-found.component';
import { RegistrationSuccessfulComponent } from './registration-successful/registration-successful.component';
import { InternalServerErrorComponent } from './internal-server-error/internal-server-error.component';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { CommunityComponent } from './community/community.component';
import { CommunityDetailsComponent } from './community-details/community-details.component';
import { ProfileCardComponent } from './profile-card/profile-card.component';
import { ProfileNavComponent } from './profile-nav/profile-nav.component';
import { ProfileComponent } from './profile/profile.component';
import { ProfileLinkProfileComponent } from './profile-link-profile/profile-link-profile.component';
import { ProfileLinkPostsComponent } from './profile-link-posts/profile-link-posts.component';
import { ProfileLinkCommentsComponent } from './profile-link-comments/profile-link-comments.component';
import { ProfileLinkSavedComponent } from './profile-link-saved/profile-link-saved.component';
import { LoginComponent } from './login/login.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { ProfileCommentComponent } from './profile-comment/profile-comment.component';
import { CommunityEditComponent } from './community-edit/community-edit.component';
import { HelpSiteComponent } from './help-site/help-site.component';
import { ViewTopicsComponent } from './view-topics/view-topics.component';
import { TopicShortComponent } from './topic-short/topic-short.component';
import { CommunityCreateComponent } from './community-create/community-create.component';
import { CarouselFullPostComponent } from './carousel-full-post/carousel-full-post.component';
import { HelpPageComponent } from './help-page/help-page.component';
import { EditPostComponent } from './edit-post/edit-post.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent }, // ok
  { path: 'home-menu', component: HomeMenuComponent }, // ok
  { path: '', component: HomeComponent }, // ok
  { path: 'post', component: PostComponent }, // ok
  { path: 'about', component: AboutComponent }, // ok
  { path: 'contact', component: ContactComponent }, // ok
  { path: 'not-found', component: NotFoundComponent },  // OK
  { path: 'registration-successful', component: RegistrationSuccessfulComponent },  // ok
  { path: 'internal-server-error', component: InternalServerErrorComponent }, // ok
  { path: 'unauthorized', component: UnauthorizedComponent },  // ok
  { path: "full-post/:id", component: FullPostPageComponent },
  { path: "community/:id", component: CommunityComponent },
  { path: "p-c", component: ProfileCardComponent },
  { path: "p-n", component: ProfileNavComponent },
  { path: "profile", component: ProfileComponent },
  { path: "see-profile", component: ProfileLinkProfileComponent },
  { path: "see-posts", component: ProfileLinkPostsComponent },
  { path: "login", component: LoginComponent },
  { path: "create-account", component: CreateAccountComponent },
  { path: "see-posts", component: ProfileLinkPostsComponent},
  { path: "c-e", component: CommunityEditComponent},
  { path: "view-topics", component: ViewTopicsComponent},
  { path: "create-topics", component: CommunityCreateComponent},
  { path: "create-post/:topicId/:topicName", component: CreatePostComponent },
  { path: "help", component: HelpPageComponent },
  { path: "help-s", component: HelpSiteComponent },
  { path: "edit-post/:postId", component: EditPostComponent }
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
    CreatePostComponent,
    CarouselComponent,
    PostComponent,
   PostComponent,
    HomeMenuComponent,
    FullPostComponent,
    FullPostPageComponent,
    CommentsComponent,
    NotFoundComponent,
    RegistrationSuccessfulComponent,
    InternalServerErrorComponent,
    UnauthorizedComponent,
    CommunityComponent,
    CommunityDetailsComponent,
    ProfileCardComponent,
    ProfileNavComponent,
    ProfileComponent,
    ProfileLinkProfileComponent,
    ProfileLinkPostsComponent,
    ProfileLinkCommentsComponent,
    ProfileLinkSavedComponent,
    LoginComponent,
    CreateAccountComponent,
    ProfileCommentComponent,
    CommunityEditComponent,
    HelpSiteComponent,
    ViewTopicsComponent,
    TopicShortComponent,
    CommunityCreateComponent,
    CarouselFullPostComponent,
    HelpPageComponent,
    EditPostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    AngularEditorModule,
    HttpClientModule,
    FormsModule
  ],
  exports: [RouterModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
