import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';

interface CertTracking {
  certification_name: string;
  skill: string;
  requester: string;
  status: string;
  certification_details: string;
  training_completion_date : string;
}


@Component({
  selector: 'app-cert-track-full-dtls',
  standalone: true,
  imports: [TableComponent, TableModule, CommonModule],
  templateUrl: './cert-track-full-dtls.component.html',
  styleUrl: './cert-track-full-dtls.component.css'
})
export class CertTrackFullDtlsComponent {
  // certTrackFDtls: CertTracking[] = [];
  // certTrackingList: any[] = [];

  certTrackFDtls: CertTracking | undefined;

  constructor(private router: Router) {
    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as { data: CertTracking };
    this.certTrackFDtls = state?.data;
  }

  ngOnInit() {
  }

}
