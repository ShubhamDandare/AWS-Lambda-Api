import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SigninServiceService } from '../signin-service.service';
//import { NotificationService } from "./notification.service";
import { NotificationsService, SimpleNotificationsModule } from 'angular2-notifications';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {
  //success : boolean = false ;
  dealer={
    username:"",
    password:""
 }
 signin(){
  const observable=this.signinservice.signin(this.dealer);
  observable.subscribe(
    
    (Response:any)=>{
      console.log(Response);
      sessionStorage.setItem("dealer",JSON.stringify(Response));
      this.notification.success("Login success")
    }
 ,
 function(error){
  alert(error)
 },()=>{


})
}
  constructor(private signinservice:SigninServiceService,private router:Router,private notification:NotificationsService) { }

  ngOnInit(): void {
  }

}
