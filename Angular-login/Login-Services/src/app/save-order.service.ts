import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SaveOrderService {

  BASE_URL="https://ju47zm5mz4.execute-api.ap-northeast-1.amazonaws.com//saveorder";
  dealer = JSON.parse(sessionStorage.getItem("dealer") || '{}');

  order = {
    make : "",
    model : "",
    year : "",
    fuelType: "",
    customerId: "",
    customerName: "",
    city: "",
    orderId: "",
    orderStatus: ""
  }

  uploadFile(fileContent : string | undefined ){
    const fileInput = {
      fileContent: fileContent,
      dealerId : this.dealer.dealerId
    }
    return this.http.post(this.BASE_URL+'/saveorder', fileInput).pipe(catchError(this.errorHandler));
  }
  


  errorHandler(error: HttpErrorResponse) {
    return throwError(error.error.message || 'server Error');
  }

  constructor(private http: HttpClient) { }
}
