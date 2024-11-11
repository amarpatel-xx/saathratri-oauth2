import { Component, OnInit } from '@angular/core';
import { Organization } from '../../models/organization.model';
import { OrganizationService } from '../../services/organization.service';

@Component({
  selector: 'app-organizations',
  templateUrl: './organizations.component.html',
  styleUrls: ['./organizations.component.scss']
})
export class OrganizationsComponent implements OnInit {
  organizations: Organization[] = [];

  constructor(private organizationService: OrganizationService) {}

  ngOnInit(): void {
    this.getOrganizations();
  }

  getOrganizations(): void {
    this.organizationService.getOrganizations().subscribe({
      next: (data: Organization[]) => {
        this.organizations = data;
      },
      error: (error) => {
        console.error('Error fetching organizations', error);
      }
    });
  }
}
