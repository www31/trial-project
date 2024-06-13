import { Component, OnInit } from '@angular/core';
import { CustomBottonComponent } from '../../../../shared/components/custom-button/custom-button.component';
import { CommonModule } from '@angular/common';
import { DropdownModule } from 'primeng/dropdown';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { ApproverService } from '../../../../service/approver.service';
import { TableModule } from 'primeng/table';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

interface CertTracking {
  certification_name: string;
  skill: string;
  requester: string;
  status: string;
}

interface DropdownItem {
  // label: string;
  value: string;
}

@Component({
  selector: 'app-cert-tracking',
  standalone: true,
  imports: [CommonModule, CustomBottonComponent, DropdownModule, TableComponent, TableModule, FormsModule],
  templateUrl: './cert-tracking.component.html',
  styleUrl: './cert-tracking.component.css'
})

export class CertTrackingComponent implements OnInit {
  certTrackingList: CertTracking[] = [];
  filteredCertTrackingList: CertTracking[] = [];
  // certNameFilter: string = '';
  // requesterFilter: string = '';
  // skillFilter: any | null = null;

  filters: {
    certification_name: string,
    requester: string,
    skill: any,
    status: any
  } = {
      certification_name: '',
      requester: '',
      skill: '',
      status: ''
    };
  skillList: DropdownItem[] = [];
  statusList: DropdownItem[] = [];

  constructor(private router: Router, private approverService: ApproverService) { }

  sampleColumns = [
    { header: 'Certification Name', field: 'certification_name' },
    { header: 'Skill', field: 'skill' },
    { header: 'Requester', field: 'requester' }
  ];

  ngOnInit() {
    this.getCertTrackList();
    this.getSkillList();
    this.getStatusList();
    this.certTrackingList = [];
    this.filteredCertTrackingList = this.certTrackingList;
  };

  getCertTrackList() {
    this.approverService.getCertTrackList().subscribe(
      (res: any) => {
        this.certTrackingList = res.data;
        this.filteredCertTrackingList = res.data
        console.log('->res', res);
      }
    )
  };

  getSkillList() {
    this.approverService.getSkills().subscribe(
      (res: any) => {
        this.skillList = res.data;
        // console.log('this.skillList', this.skillList);
      }
    )
  };

  getStatusList() {
    this.approverService.getStatus().subscribe(
      (res: any) => {
        this.statusList = res.data;
        // console.log('this.statusList', this.statusList);
      }
    )
  };

  onRowSelect(event: any) {
    // this.approverService.setCertTrackList(event.certTrackData);
    // this.approverService.getCertTrackingDtls(event.data);
    // this.router.navigate(['cert-track-full-dtls']);
    this.router.navigate(['approver-cert-tracking-full-details'], { state: { data: event.data } });
  };

  clearFilters() {
    this.filters = {
      certification_name: '',
      requester: '',
      skill: '',
      status: '',
    }
    this.filteredCertTrackingList = this.certTrackingList;
  };

  applyAllFilters() {
    this.filteredCertTrackingList = this.certTrackingList.filter(certTrack => {
      return (this.filters.certification_name ? certTrack.certification_name.toLowerCase().includes(this.filters.certification_name.toLowerCase()) : true) &&
        (this.filters.requester ? certTrack.requester.toLowerCase().includes(this.filters.requester.toLowerCase()) : true) &&
        (this.filters.skill ? String(certTrack.skill) === this.filters.skill.skill : true) &&
        (this.filters.status ? String(certTrack.status) === this.filters.status.statusName : true);
    })
  };


}
