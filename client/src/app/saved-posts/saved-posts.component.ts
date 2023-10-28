import { Component } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-saved-posts',
  templateUrl: './saved-posts.component.html',
  styleUrls: ['./saved-posts.component.css']
})
export class SavedPostsComponent {
 constructor(public authService: AuthService) { }

  myArr: any[] = [
    {
      id: 1,
      username: 'Autor123',
      time: "2 ore în urmă",
      img: "assets/images/avatar.png",
      title: "Noul smartphone revoluționar: Ce aduce în plus?",
      content: "În era rapidă a tehnologiei, noul smartphone de la XYZ Corp a stârnit interesul pasionaților de gadgeturi din întreaga lume. Cu promisiunea de a redefine experiența utilizatorului, acest dispozitiv aduce o serie de caracteristici deosebite care îl disting de competiție.\n\nUnul dintre punctele forte ale acestui smartphone este noul său procesor cu arhitectură avansată, care promite performanțe excepționale. Cu acest procesor puternic la bază, sarcinile multitasking devin o joacă de copii, iar jocurile și aplicațiile rulează neted.\n\nÎn plus, telefonul dispune de o cameră foto inovatoare cu multiple lentile, oferind imagini clare și detaliate în orice condiții de iluminare. Fotografii, selfie-uri și videoclipuri de calitate superioară sunt la îndemâna utilizatorilor.\n\nNoul smartphone vine cu un ecran OLED uimitor, care oferă culori vibrante și neguri profunde, transformând experiența de vizionare a conținutului multimedia într-o adevărată plăcere. Fie că urmăriți filme, jocuri sau navigați pe internet, calitatea imaginii este uimitoare.\n\nÎn ceea ce privește securitatea, acest dispozitiv integrează tehnologii avansate de recunoaștere facială și senzori de amprentă, garantând confidențialitatea datelor dumneavoastră.\n\nÎn concluzie, noul smartphone de la XYZ Corp este cu adevărat revoluționar, aducând tehnologie de vârf la îndemâna utilizatorilor obișnuiți. Cu caracteristici de top, performanțe superioare și un design elegant, acest telefon promite să devină preferatul pasionaților de tehnologie din întreaga lume.",
      points: 10,
      comments: 2,
      saved: true
    },
    {
      id: 2,
      username: 'CălătorAventurier',
      time: "3 zile în urmă",
      img: "assets/images/avatar.png",
      title: "Explorarea Muntelui Everest: O aventură epică",
      content: "În urmă cu doar câteva zile, am avut privilegiul de a face parte dintr-o expediție epică către Muntele Everest, cea mai înaltă vârf din lume. Această aventură a fost mai mult decât o simplă călătorie, a fost o experiență de viață fără egal.\n\nDe la baza muntelui, privind în sus către vârfurile acoperite de zăpadă, am simțit o amestecare de anticipație și reverență față de natură. Începutul călătoriei noastre a fost un marș obositor, dar fiecare pas în sus ne-a adus mai aproape de acel vârf legendare.\n\nPe măsură ce ne apropiam de zona de altitudine înaltă, am simțit cum aerul subțire ne punea la încercare rezistența. Cu toate acestea, dorința de a ajunge în vârf ne-a ținut motivați.\n\nOdată ajunși pe vârful Everest, panorama care se întindea în fața noastră era pur și simplu uluitoare. Albastrul cerului, munții înconjurători și silueta stâncoasă a vârfului erau cu adevărat impresionante. A fost un moment de contemplare și recunoștință.\n\nCu toate acestea, drumul în jos nu a fost mai puțin provocator. Coborârea de la această altitudine a fost solicitantă, iar riscul avalanșelor era mereu prezent. Cu ajutorul echipei noastre și a ghizilor experimentați, am reușit să ne întoarcem în siguranță la baza muntelui.\n\nAceastă expediție a fost o adevărată aventură epică, plină de momente incredibile și provocări. Imaginile capturate pe parcursul călătoriei ne amintesc mereu de frumusețea și fragilitatea naturii. Este o experiență pe care o vom purta în inimile noastre pentru tot restul vieții."
      , points: 16,
      comments: 23,
      saved: true
    }
    ,
    {
      id: 3,
      username: 'Gurmand123',
      time: "1 zi în urmă",
      img: "assets/images/avatar.png",
      title: "Cele mai bune restaurante din Paris",
      content: "Parisul este cunoscut nu doar pentru frumusețea sa arhitecturală și cultura sa bogată, ci și pentru gastronomia sa rafinată. Orașul este plin de restaurante de top, fiecare oferind o experiență culinară de neuitat. Iată o listă cu cele mai renumite restaurante din capitala Franței, unde poți savura bucate delicioase și vinuri excepționale.\n\n1. Le Jules Verne: Aflat în Turnul Eiffel, acest restaurant cu o vedere panoramică uimitoare oferă preparate gourmet și vinuri fine. Este locul perfect pentru o cină romantică cu vedere la Parisul iluminat.\n\n2. Le Bernardin: Cu o stea Michelin, acest restaurant de fructe de mare renumit servește preparate proaspete și delicioase. Specialitățile lor de pește sunt cu adevărat remarcabile.\n\n3. L'Ambroisie: Acest restaurant cu trei stele Michelin oferă o experiență culinară de excepție. Bucătăria lor franceză tradițională este la superlativ.\n\n4. Guy Savoy: Cu trei stele Michelin, Guy Savoy este celebru pentru mâncarea sa sofisticată și atmosfera elegantă. Aici vei găsi cele mai bune mâncăruri franțuzești.\n\n5. Le Comptoir du Relais: Acest restaurant tradițional parizian oferă preparate autentice și un ambient autentic. Este locul ideal pentru a experimenta bucatele clasice franceze.\n\nIndiferent de restaurantul pe care îl alegi, vei fi răsfățat cu arome și gusturi extraordinare. Parisul este cu siguranță un paradis pentru gurmanzi, oferind o gamă variată de opțiuni culinare, de la mâncăruri tradiționale la cele mai sofisticate preparate. Așadar, pregătește-ți papilele gustative pentru o aventură culinară memorabilă în capitala Franței."
      , points: 23,
      comments: 0,
      saved: true
    }
    ,
    {
      id: 4,
      username: 'TehnoGeek',
      time: "6 ore în urmă",
      img: "assets/images/avatar.png",
      title: "Tendințe în tehnologie pentru anul 2023",
      content: "Te-ai întrebat ce aduce viitorul în ceea ce privește tehnologia? Anul 2023 promite să fie plin de inovații remarcabile, care vor influența semnificativ modul în care trăim și lucrăm. Iată câteva dintre cele mai recente tendințe în domeniul tehnologic și cum vor schimba acestea lumea noastră.\n\n1. Inteligența Artificială (IA) în creștere: IA continuă să evolueze rapid, aducând cu ea aplicații mai avansate în domenii precum sănătatea, transportul și asistența virtuală. Asistenții vocali și mașinile autonome devin tot mai omniprezente.\n\n2. 5G revoluționar: Expansiunea rețelelor 5G va transforma conectivitatea, facilitând internetul rapid și fiabil în întreaga lume. Aceasta va permite dezvoltarea realității virtuale și a Internetului Lucrurilor (IoT).\n\n3. Realitatea Virtuală și Augmentată: Aplicațiile de realitate virtuală și augmentată devin mai accesibile și se extind în industrii precum gaming-ul, educația și medicina. Aceste tehnologii vor schimba modul în care interacționăm cu lumea.\n\n4. Sănătatea și tehnologia: Dispozitivele inteligente pentru monitorizarea sănătății devin tot mai comune. De la ceasurile inteligente care măsoară parametrii vitali până la aplicații de monitorizare a stării de sănătate, tehnologia ajută oamenii să-și îmbunătățească viața.\n\n5. Energie durabilă și tehnologie verde: În 2023, se anticipează o creștere a inovațiilor în energie verde, cu accent pe surse de energie regenerabilă și soluții de eficiență energetică.\n\nAcestea sunt doar câteva dintre tendințele majore pe care le putem anticipa pentru anul 2023. Cu inovații continue în tehnologie, lumea noastră se schimbă și se adaptează într-un ritm accelerat. Este important să rămânem conectați la aceste tendințe pentru a ne bucura de avantajele pe care le aduc și pentru a fi pregătiți pentru viitor."
      , points: 55,
      comments: 10,
      saved: true
    }
    ,
    {
      id: 5,
      username: 'ModăPassionată',
      time: "4 zile în urmă",
      img: "assets/images/avatar.png",
      title: "Tendințe de modă pentru sezonul primăvară-vară 2023",
      content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi viverra lorem id molestie fermentum. Donec efficitur posuere justo, vel efficitur erat hendrerit id. Pellentesque sed leo dui. Morbi pretium dui id posuere egestas. Fusce lorem urna, pulvinar at suscipit eget, ultricies vel nulla. Aliquam mi orci, porta sit amet quam sit amet, aliquet egestas eros. Duis semper efficitur purus, ut posuere nunc interdum non. Donec eleifend mattis ligula, vel scelerisque sem pretium quis. Nulla nec nisi tellus. Praesent felis sapien, dictum non quam at, aliquam dapibus dui. Phasellus et purus tempor, dictum enim nec, dignissim arcu. Vivamus a tortor in dolor ultricies eleifend.Vivamus non ornare metus. Fusce viverra cursus odio, ac vestibulum metus semper non. Etiam odio mi, cursus quis libero a, interdum convallis orci. Fusce eget commodo neque. Pellentesque ac diam nisl. Nam faucibus enim et neque porttitor, eget tincidunt erat finibus. Nullam ut tellus diam. Sed lacinia, ligula vitae sollicitudin iaculis, lacus nulla condimentum leo, et accumsan augue diam id purus. Aliquam erat volutpat. Quisque congue quam aliquam nisl tincidunt porttitor. Vestibulum ornare dictum nunc id accumsan. Fusce varius odio sem. Cras venenatis id ligula eget convallis. Ut suscipit eget augue quis feugiat. Proin a nulla vel magna accumsan aliquam vitae eu urna. Sed est lorem, faucibus ullamcorper porta vel, semper ac augue. Vivamus dolor tellus, fermentum ut dolor ac, gravida eleifend mauris. Mauris sit amet risus id augue lobortis maximus sed sit amet enim.In ultrices lobortis sapien. Praesent bibendum sapien at dui sagittis, ac aliquam urna feugiat. Cras ullamcorper mattis turpis. Morbi vestibulum aliquam feugiat. Quisque et sodales arcu. Ut eleifend turpis molestie felis scelerisque gravida. Etiam in egestas lacus. Morbi molestie lobortis venenatis. Morbi vel arcu nec felis ornare porta. Sed posuere gravida neque, in porttitor odio venenatis vitae. Ut id lorem nunc. Aenean blandit, justo in ornare vestibulum, sem diam accumsan est, in tempor lacus diam et leo. Ut non nunc sem. Suspendisse potenti. Etiam accumsan augue in tellus ullamcorper, a maximus enim accumsan. Phasellus condimentum et eros in rutrum. Curabitur convallis mollis felis nec porttitor. Aenean suscipit lectus sit amet erat congue, eu ullamcorper quam molestie. Nam facilisis nulla at dignissim ullamcorper. Donec vulputate turpis eu nisl gravida lobortis. Quisque varius tortor aliquet elit pharetra fringilla. Mauris lobortis tincidunt erat, vitae sodales est. Vivamus congue vestibulum urna ut consectetur. Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus ultrices lacus ut quam imperdiet, eget ultrices turpis pretium."
      , points: 156,
      comments: 56,
      saved: true
    }
    , {
      id: 7,
      username: 'FotografProfesionist',
      time: "5 zile în urmă",
      img: "assets/images/avatar.png",
      title: "Arta fotografiei de peisaj: Sfaturi și tehnici",
      content: "Învață cum să capturezi imagini uluitoare ale peisajelor naturale cu ajutorul acestui ghid de fotografie de peisaj, care include sfaturi și tehnici de la un fotograf profesionist."
      , points: 5500,
      comments: 402,
      saved: true
    },
    {
      id: 7,
      username: 'CălătorCurios',
      time: "2 săptămâni în urmă",
      img: "assets/images/avatar.png",
      title: "Descoperă frumusețile Asiei de Sud-Est",
      content: "Asia de Sud-Est este o regiune minunată, bogată în cultură, peisaje uimitoare și aventuri fascinante. Dacă visezi să explorezi această parte a lumii, iată câteva dintre cele mai pitorești destinații și experiențe culturale de neuitat.\n\n1. Bali, Indonezia: Cunoscută pentru plajele sale spectaculoase, templele hinduiste și cultura vibrantă, Bali este o destinație de vis. Poți închiria o vilă privată cu vedere la ocean sau să te relaxezi în băile termale de la Ubud.\n\n2. Angkor Wat, Cambodgia: Acest templu uriaș din secolul XII este unul dintre cele mai mari și mai bine conservate complexe religioase din lume. Explorează aceste ruine antice și admira arhitectura lor impresionantă.\n\n3. Halong Bay, Vietnam: Cu stânci calcaroase și ape limpezi, Halong Bay este un loc de basm. O croazieră pe apă îți va permite să descoperi peisajele minunate ale acestei destinații.\n\n4. Chiang Mai, Thailanda: Cunoscut pentru templele sale frumoase și cultura sa locală, Chiang Mai oferă experiențe autentice, cum ar fi învățarea gătitului tradițional thai sau vizite la templele budiste.\n\n5. Luang Prabang, Laos: Acest oraș istoric este situat pe malul fluviului Mekong și este renumit pentru arhitectura sa colonială franceză și peisajele sale pline de verdeață.\n\n6. Yangon, Myanmar: Descoperă cultura și istoria bogată a Myanmar vizitând locuri precum Pagoda Shwedagon și complexul templului Bagan.\n\nAcestea sunt doar câteva dintre destinațiile uimitoare pe care le poți explora în Asia de Sud-Est. Indiferent dacă îți dorești aventuri în natură, experiențe culturale sau relaxare pe plajă, această regiune are ceva de oferit pentru fiecare călător curios."
      , points: 1,
      comments: 9,
      saved: true
    }


  ]
}
