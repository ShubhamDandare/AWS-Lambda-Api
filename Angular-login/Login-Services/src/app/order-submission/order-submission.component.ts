import { Component, OnInit, VERSION, ViewChild } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { Observable, ReplaySubject } from 'rxjs';
import { SaveOrderService } from '../save-order.service';
export class CsvData {
  public make: any;
  public model: any;
  public year: any;
  public fuelType: any;
  public customerId: any;
  public customerName: any;
  public city: any;
  public orderId: any;
  public orderStatus: any;
}
@Component({
  selector: 'app-order-submission',
  templateUrl: './order-submission.component.html',
  styleUrls: ['./order-submission.component.css']
})
export class OrderSubmissionComponent  {
  
  name = 'Angular ' + VERSION.major;
  public records: any[] = [];
  @ViewChild('csvReader') csvReader: any;
  jsondatadisplay:any;

  uploadListener(files: any) {

    
    // let fileList = (<HTMLInputElement>files.target).files;
   
    let fileList = (<HTMLInputElement>files.target).files;
    if (fileList && fileList.length > 0) {
      let file: File = fileList[0];
      console.log(file.name);
      console.log(file.size);
      console.log(file.type);

      let reader: FileReader = new FileReader();
      reader.readAsText(file);
      console.log(reader)
    
      reader.onload = e => {
       

        let csvData:any = reader.result;
          let csvRecordsArray = (<string>csvData).split(/\r\n|\n/);
          let headersRow = this.getHeaderArray(csvRecordsArray);

          this.records = this.getDataRecordsArrayFromCSVFile(csvRecordsArray, headersRow.length);
       
       
      };

      reader.onerror = function () {
        console.log('error is occured while reading file!');
      };

    } else {
      alert("Please import valid .csv file.");
      this.fileReset();
    }
  }

  getDataRecordsArrayFromCSVFile(csvRecordsArray: any, headerLength: any) {
    let csvArr = [];

    for (let i = 1; i < csvRecordsArray.length; i++) {
      let curruntRecord = (csvRecordsArray[i]).split(',');
      if (curruntRecord.length == headerLength) {
        let csvRecord: CsvData = new CsvData();
        csvRecord.make = curruntRecord[0].trim();
        csvRecord.model = curruntRecord[1].trim();
        csvRecord.year = curruntRecord[2].trim();
        csvRecord.fuelType = curruntRecord[3].trim();
        csvRecord.customerId = curruntRecord[4].trim();
        csvRecord.customerName = curruntRecord[5].trim();
        csvRecord.city = curruntRecord[6].trim();
       // csvRecord.orderId = curruntRecord[7].trim();
       // csvRecord.orderStatus = curruntRecord[8].trim();
        csvArr.push(csvRecord);
      }
    }
    return csvArr;
  }

//check etension
  isValidCSVFile(file: any) {
    return file.name.endsWith(".csv");
  }

  getHeaderArray(csvRecordsArr: any) {
    let headers = (csvRecordsArr[0]).split(',');
    let headerArray = [];
    for (let j = 0; j < headers.length; j++) {
      headerArray.push(headers[j]);
    }
    return headerArray;
  }

  fileReset() {
    this.csvReader.nativeElement.value = "";
    this.records = [];
    this.jsondatadisplay = '';
  }

  getJsonData(){
    this.jsondatadisplay = JSON.stringify(this.records);
  }
constructor(private  orderService:SaveOrderService, private router: Router){}

}
 




 
