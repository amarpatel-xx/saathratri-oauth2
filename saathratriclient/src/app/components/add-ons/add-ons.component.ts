import { Component, OnInit } from '@angular/core';
import { AddOn } from '../../models/add-on.model';
import { AddOnService } from '../../services/add-on.service';

@Component({
  selector: 'app-addons',
  templateUrl: './add-ons.component.html',
  styleUrls: ['./add-ons.component.scss']
})
export class AddOnsComponent implements OnInit {
  addOns: AddOn[] = [];

  constructor(private addOnService: AddOnService) {}

  ngOnInit(): void {
    this.getAddOns();
  }

  getAddOns(): void {
    this.addOnService.getAddOns().subscribe({
      next: (data: AddOn[]) => {
        this.addOns = data;
      },
      error: (error) => {
        console.error('Error fetching add-ons', error);
      }
    });
  }
}
