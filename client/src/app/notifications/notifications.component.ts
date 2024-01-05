import { Component } from '@angular/core';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent {
  notifications: { title: string; content: string; date: string }[] = [
  { title: "Răspuns la postarea ta", content: "Utilizatorul X a răspuns la postarea ta în thread-ul Y.", date: "2024-01-01" },
  { title: "Mesaj nou în thread-ul urmărit", content: "Thread-ul Z la care ești abonat/a are un mesaj nou.", date: "2024-01-02" },
  { title: "Postare marcată ca soluție", content: "Postarea ta în thread-ul W a fost marcată ca soluție.", date: "2024-01-03" },
  { title: "Feedback primit la postare", content: "Utilizatorul A a dat feedback la postarea ta în thread-ul B.", date: "2024-01-04" },
  { title: "Anunț important", content: "Administrare: Anunț important privind schimbări în regulile forumului.", date: "2024-01-05" },
  { title: "Întâlnire locală", content: "Comunitatea organizează o întâlnire locală pe data de 10 februarie.", date: "2024-01-06" }];

  notificationCount: number = this.notifications.length;
}
