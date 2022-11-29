import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
//import { throwError } from 'rxjs';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
//import { catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SigninServiceService {

  Login_URL= "https://yumszrjbyg.execute-api.ap-northeast-1.amazonaws.com/v1/dealer-login";
  Sign_Up_URL="";
  Sign_In_URL="";

  login(dealer:{username:String,password:String}){
    return this.http.post(this.Login_URL,dealer).pipe(catchError(this.errorHandler))}

    // signin(dealer:{username:String,password:String}){
    //   return this.http.post(this.Sign_In_URL,dealer)}

    errorHandler(error: HttpErrorResponse) {
      return throwError(error.error.message || 'server Error');
    }


    signup(dealer:{username:String,password:String}){
      return this.http.post(this.Sign_Up_URL,dealer)}

      
      constructor(private http: HttpClient) { }
}
