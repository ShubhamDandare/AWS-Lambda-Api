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
  objectKey : string | undefined;
  
  name = 'Angular ' + VERSION.major;
  public records: any[] = [];
  @ViewChild('csvReader') csvReader: any;
  jsondatadisplay:any;

  uploadListener(event: any) {
this.filetostringconverter(event.target.files[0]).subscribe(( encoded: string | undefined)=>{
  this.objectKey = encoded;
  console.log(this.objectKey )
  this.UploadFile();
});

}



// subscribe((decode: string | undefined) =>
//  {
//   this.fileContent = decode;
//   console.log(this.fileContent )
  
 
// });
// }

  filetostringconverter(file:File) : Observable<string>{
  // let fileList = (<HTMLInputElement>files.target).files;
    // if (file.size>=0) {
     
      console.log(file.name);
      console.log(file.size);
      console.log(file.type);
      const result = new ReplaySubject<string>(1);
     
      let reader: FileReader = new FileReader();
      reader.readAsBinaryString(file);
      console.log(reader)
      reader.onload = (event:any) => result.next(btoa(event.target.result.toString()));
    return result;
    
      // reader.onload =(event:any) => {
      //   result.next(btoa(event.target.result.toString()));
        
      //   let csvData:any = reader.result;
      //   console.log(csvData)
      //     let csvRecordsArray = (<string>csvData).split(/\r\n|\n/);
      //     console.log(csvRecordsArray)
      //     let headersRow = this.getHeaderArray(csvRecordsArray);

      //     this.records = this.getDataRecordsArrayFromCSVFile(csvRecordsArray, headersRow.length);
       
      // //  if(this.records!=null){

      // //   this.fileContent=csvData
      // //   console.log('==============='+this.fileContent)
      // //     this.UploadFile();
      // //  }
       
      //  return result;
    
      // }
      

      // reader.onerror = function () {
      //   console.log('error is occured while reading file!');
      // };

    // } else {
    //   alert("Please import valid .csv file.");
    //   this.fileReset();
    // }
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
//   onFileSelected(event : any) {
//     this.uploadListener(event.target.files[0]).subscribe((decode: string | undefined) => {
// this.fileContent=decode;
//     });
//   }
  UploadFile(){
    console.log('*******'+this.objectKey)
    const observable = this.orderService.uploadFile(this.objectKey) ;
   
   // const observable = this.orderService.uploadFiles(this.records) ;
    observable.subscribe(
      (response : any) => {
        console.log('response__________'+response)
        let navigationExtras: NavigationExtras = {
          state: {
            convertedToOrder: response
          }
        };
        alert("File upload Success");
        this.router.navigate(['orderdashboard'],navigationExtras)
  },(error) => {
    alert(error);
  });
  }
constructor(private  orderService:SaveOrderService, private router: Router){}

}
 




 
function subscribe(arg0: (base64: string | undefined) => void) {
  throw new Error('Function not implemented.');
}

