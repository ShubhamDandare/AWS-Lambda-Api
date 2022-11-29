import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { SigninServiceService } from '../signin-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  dealer={
    username:"",
    password:""
 }
login(){
  const observable=this.signinservice.login(this.dealer);
  observable.subscribe(
    
    (Response:any)=>{
      console.log(Response);
      sessionStorage.setItem("dealer", JSON.stringify(Response));
      // if(){
      //   this.router.navigate(['uploadfile'])
      // }
      //this.notification.success("Login Success")
      this.router.navigate(['uploadfile'])
     // alert("Login Success")
    }
,
function(error){
  alert("Something went wrong Please try again")
},()=>{
  

}
)
}



  constructor(private signinservice:SigninServiceService,private router:Router) { }

  ngOnInit(): void {
  }

}
