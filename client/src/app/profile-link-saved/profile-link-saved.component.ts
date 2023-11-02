import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-link-saved',
  templateUrl: './profile-link-saved.component.html',
  styleUrls: ['./profile-link-saved.component.css']
})
export class ProfileLinkSavedComponent {
  constructor(private router: Router) { }
  myArr: any[] = [
    {
      id: 1,
      comunity: 'CoMuniTate',
      username: 'Autor123',
      time: "2 ore în urmă",
      img: "assets/images/avatar.png",
      title: "Noul smartphone revoluționar: Ce aduce în plus?",
      points: 10,
      comments: 2,
      saved: false
    },
    {
      id: 2,
      comunity: 'CoMuniTate',
      username: 'CălătorAventurier',
      time: "3 zile în urmă",
      img: "assets/images/avatar.png",
      title: "Explorarea Muntelui Everest: O aventură epică",
      points: 16,
      comments: 23,
      saved: true
    },
    {
      id: 3,
      comunity: 'CoMuniTate',
      username: 'Gurmand123',
      time: "1 zi în urmă",
      img: "assets/images/avatar.png",
      title: "Cele mai bune restaurante din Paris",
      points: 23,
      comments: 0,
      saved: false
    },
    {
      id: 4,
      comunity: 'CoMuniTate',
      username: 'TehnoGeek',
      time: "6 ore în urmă",
      img: "assets/images/avatar.png",
      title: "Tendințe în tehnologie pentru anul 2023",
      points: 55,
      comments: 10,
      saved: true
    },
    {
      id: 5,
      comunity: 'CoMuniTate',
      username: 'ModăPassionată',
      time: "4 zile în urmă",
      img: "assets/images/avatar.png",
      title: "Tendințe de modă pentru sezonul primăvară-vară 2023",
      points: 156,
      comments: 56,
      saved: true
    },
    {
      id: 7,
      comunity: 'CoMuniTate',
      username: 'FotografProfesionist',
      time: "5 zile în urmă",
      img: "assets/images/avatar.png",
      title: "Arta fotografiei de peisaj: Sfaturi și tehnici",
      points: 5500,
      comments: 402,
      saved: false
    },
    {
      id: 7,
      comunity: 'TOPic',
      username: 'CălătorCurios',
      time: "2 săptămâni în urmă",
      img: "assets/images/avatar.png",
      title: "Descoperă frumusețile Asiei de Sud-Est",
      points: 1,
      comments: 9,
      saved: true
    }
  ];
  navigateToPost(postId: number) {
    this.router.navigate(['/full-post', postId]);
  }
}
