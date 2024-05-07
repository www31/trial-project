import { Component, inject } from '@angular/core';
import jsPDF from 'jspdf';
import { DataService } from './data.service';
import { DomSanitizer } from '@angular/platform-browser';
import * as PDFObject from 'pdfobject';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  // private sanitizer = inject(DomSanitizer);
  dataService = inject(DataService);
  trustedUrl: any = '';
  // constructor() {
  //   this.trustedUrl = this.sanitizer.bypassSecurityTrustResourceUrl(
  //     'https://www.ayyaztech.com'
  //   );
  // }
  // updateIframeSrc(newUrl: string) {
  //   this.trustedUrl = this.sanitizer.bypassSecurityTrustResourceUrl(
  //     newUrl
  //   );
  //   console.log('trustedUrl', this.trustedUrl);
  // }
  generatePDF() {
    const doc = new jsPDF();
    doc.addImage(
      `https://miro.medium.com/v2/resize:fit:1400/1*1HXCJCOpzKdmQI33ZrEIlg.png`,
      'PNG',
      10,
      10,
      50,
      20
    );
    doc.setFont('Helvetica');
    doc.setFontSize(18);
    doc.text(
      `Monthly Sales Report for ${this.dataService.clientData.name}`,
      60,
      20
    );
    doc.setFontSize(12);
    doc.text(`Reporting Period ${this.dataService.clientData.name}`, 60, 30);
    let yPos = 50;
    this.dataService.clientData.transactions.forEach((transaction) => {
      doc.text(`${transaction.date} - ${transaction.description} $${transaction.amount.toFixed(2)}`,
        10,
        yPos
      );
      yPos += 10;
    });

    doc.setFontSize(14);
    doc.text(
      `Total Sales: $${this.dataService.totalSales.toFixed(2)}`,
      10,
      yPos + 20
    );
    var blobPDF = new Blob([doc.output('blob')], { type: 'application/pdf' });
    PDFObject.embed(window.URL.createObjectURL(blobPDF), "#pdfRenderer");
    // doc.save('monthly-report.pdf');

  }


}
