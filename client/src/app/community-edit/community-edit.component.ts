

import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-community-edit',
  templateUrl: './community-edit.component.html',
  styleUrls: ['./community-edit.component.css']
})
export class CommunityEditComponent {
  @Input() community: any = {
    id: '1',
    name: 'Sample Community',
    description: 'This is a sample community.',
    ageRestriction: true,
    photoId: 123,
    postIds: ['post1', 'post2'],
    creatorId: 'user123',
    memberIds: ['member1', 'member2', 'member3']
  };

  selectedPhoto: File | null = null;

  saveChanges() {
    console.log('Changes saved:', this.community, 'Selected photo:', this.selectedPhoto);
  }

  onPhotoChange(event: any) {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.selectedPhoto = fileList[0];
    }
  }
}
