import { Component } from '@angular/core';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent {

  users: any[] = [
    { name: 'Bob', date: '2023-11-10', message: "Hi there, I'm Bob!", img: "assets/images/avatar.png" },
    { name: 'Charlie', date: '2023-11-10', message: 'Greetings from Charlie!', img: "assets/images/avatar.png" },
    { name: 'David', date: '2023-11-10', message: "Hey, I'm David!", img: "assets/images/avatar.png" },
    { name: 'Eva', date: '2023-11-10', message: 'Hello, this is Eva!', img: "assets/images/avatar.png" },
    { name: 'Frank', date: '2023-11-10', message: 'Greetings from Frank!', img: "assets/images/avatar.png" },
    { name: 'Grace', date: '2023-11-10', message: 'Hello, I am Grace!', img: "assets/images/avatar.png" },
    { name: 'Henry', date: '2023-11-10', message: "Hey, it's Henry!", img: "assets/images/avatar.png" },
    { name: 'Isaac', date: '2023-11-10', message: 'Message from Isaac', img: "assets/images/avatar.png" },
    { name: 'Jane', date: '2023-11-10', message: 'Message from Jane', img: "assets/images/avatar.png" },
    { name: 'Kevin', date: '2023-11-10', message: 'Message from Kevin', img: "assets/images/avatar.png" },
    { name: 'Lily', date: '2023-11-10', message: 'Message from Lily', img: "assets/images/avatar.png" },
    { name: 'Mike', date: '2023-11-10', message: 'Message from Mike', img: "assets/images/avatar.png" },
    { name: 'Nina', date: '2023-11-10', message: 'Message from Nina', img: "assets/images/avatar.png" },
    { name: 'Oliver', date: '2023-11-10', message: 'Message from Oliver', img: "assets/images/avatar.png" },
    { name: 'Pam', date: '2023-11-10', message: 'Message from Pam', img: "assets/images/avatar.png" },
    { name: 'Quincy', date: '2023-11-10', message: 'Message from Quincy', img: "assets/images/avatar.png" },
    { name: 'Riley', date: '2023-11-10', message: 'Message from Riley', img: "assets/images/avatar.png" },
  ];

  messages: { text: string; sender: string }[] = [
    { text: "Hello!", sender: "user" },
    { text: "Hi there!", sender: "receiver" },
    { text: "How are you?", sender: "user" },
    { text: "I'm doing well, thanks!", sender: "receiver" },
    { text: "What about you?", sender: "receiver" },
    { text: "I'm good too.", sender: "user" },
    { text: "Nice to hear!", sender: "receiver" },
  ];  

  menuOpen = false;

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }
   
  
}
