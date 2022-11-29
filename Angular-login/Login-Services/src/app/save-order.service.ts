import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SaveOrderService {

 
  BASE_URL= "https://yumszrjbyg.execute-api.ap-northeast-1.amazonaws.com/v1"
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
 
  uploadFile(objectKey : string | undefined ){
    const fileInput = {
      objectKey : objectKey,
    
    
      dealerId : this.dealer.id

    }
   
    return this.http.post(this.BASE_URL+'/saveorder', fileInput).pipe(catchError(this.errorHandler));
  }
 
  editOrder(order: any) {
    return this.http.put(this.BASE_URL + '/updateorder', order).pipe(catchError(this.errorHandler));
  }

  // getOrder(order: any) {
  //   return this.http.put(this.BASE_URL + '/updateorder', order).pipe(catchError(this.errorHandler));
  // }

  fetchOrders(customerId : string , orderId : string, showAll : Boolean) {
    const orderInput = {
      "customerId":customerId,
      "orderId":orderId,
      "dealerId":this.dealer.id,
      "showAll": showAll
    }
    console.log("orderinput ="+orderInput)
    return this.http.post(this.BASE_URL+'/orderlist', orderInput).pipe(catchError(this.errorHandler));
  }


  errorHandler(error: HttpErrorResponse) {
    return throwError(error.error.message || 'server Error');
  }

  constructor(private http: HttpClient) { }
}
