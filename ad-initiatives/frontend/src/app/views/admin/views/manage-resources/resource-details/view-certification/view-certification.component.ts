import { AfterContentInit, Component, inject } from '@angular/core';
import jsPDF from 'jspdf';
// @ts-ignore 
import * as PDFObject from 'pdfobject';
import { DataService } from './data.service';

@Component({
  selector: 'app-view-certification',
  standalone: true,
  imports: [],
  templateUrl: './view-certification.component.html',
  styleUrl: './view-certification.component.css'
})
export class ViewCertificationComponent implements AfterContentInit {
  dataService = inject(DataService);
  trustedUrl: any = '';
  
  ngAfterContentInit(): void {
    this.generatePDF();
  }
  
  generatePDF() {
    var image = new Image();
    image.src = 'assets/img/profile-image.png';
    const doc = new jsPDF();
    doc.addImage(
      image,
      'PNG',
      10,
      10,
      50,
      50
    );
    doc.setFont('Helvetica', 'bold');
    doc.setFontSize(23);
    doc.setTextColor(0,0,255);
    doc.text(
      this.dataService.clientData.name,
      70,
      20
    );
    doc.setFont('Helvetica','normal');
    doc.setFontSize(12);
    doc.setTextColor(0,0,0);
    doc.text(this.dataService.clientData.profession, 70, 30);

    doc.setFont('Helvetica','italic');
    var splitInfo_1 = doc.splitTextToSize(this.dataService.clientData.info_1, 120);
    doc.text(splitInfo_1, 70, 40);
    doc.setFont('Helvetica','normal');
    var splitInfo_2 = doc.splitTextToSize(this.dataService.clientData.info_2, 120);
    doc.text(splitInfo_2, 70, 50);
    
    doc.setFont('Helvetica', 'bold');
    doc.setFontSize(23);
    doc.text(
      this.dataService.section1,
      10,
      90
    );
    doc.text(
      this.dataService.section2,
      10,
      190
    );
    doc.setFont('Helvetica','normal');
    doc.setFontSize(12);
    var splitProfile_1 = doc.splitTextToSize(this.dataService.clientData.profile_1, 180);
    doc.text(
      splitProfile_1,
      10,
      100
    );
    var splitProfile_2 = doc.splitTextToSize(this.dataService.clientData.profile_2, 180);
    doc.text(
      splitProfile_2,
      10,
      130
    );
    let yPos = 200;
    this.dataService.clientData.certifications.forEach((certification) => {
      doc.text(`${certification.date} - ${certification.description} $${certification.amount.toFixed(2)}`,
        10,
        yPos
      );
      yPos += 10;
    });
    var imgTel = new Image();
    imgTel.src = this.dataService.imgTel;
    doc.addImage(
      imgTel,
      'png',
      70,
      60,
      5,
      5,
      undefined,
      'FAST'
    );
    doc.text(
      this.dataService.clientData.mobile,
      75,
      65
    );
    var imgMail = new Image();
    imgMail.src = this.dataService.imgMail;
    doc.addImage(
      imgMail,
      'png',
      70,
      70,
      5,
      5,
      undefined,
      'FAST'
    );
    doc.text(
      this.dataService.clientData.email,
      75,
      75
    );
    var imgBag = new Image();
    imgBag.src = this.dataService.imgBag;
    doc.addImage(
      imgBag,
      'png',
      130,
      60,
      5,
      5,
      undefined,
      'FAST'
    );
    doc.text(
      this.dataService.clientData.skills,
      135,
      65
    );
    var imgBldg = new Image();
    imgBldg.src = this.dataService.imgBldg;
    doc.addImage(
      imgBldg,
      'png',
      130,
      70,
      5,
      5,
      undefined,
      'FAST'
    );
    doc.text(
      this.dataService.clientData.office,
      135,
      75
    );
    doc.setFontSize(14);
    doc.text(
      `Total Price: $${this.dataService.totalPrice.toFixed(2)}`,
      10,
      yPos + 10
    );
    var blobPDF = new Blob([doc.output('blob')], { type: 'application/pdf' });
    PDFObject.embed(window.URL.createObjectURL(blobPDF), "#pdfRenderer");
    // doc.save('monthly-report.pdf');

  }
}
