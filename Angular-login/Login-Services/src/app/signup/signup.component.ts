import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SigninServiceService } from '../signin-service.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  registration : boolean = false ;

  dealer={
    username:"",
    password:""
 }

 signup(){
  const observable=this.signinservice.signup(this.dealer);
  observable.subscribe(
    (Response:any)=>{
      console.log(Response);
     if(this.registration=true) {
       alert("Dealer registration Successfully")
     }
    }
 ,
 function(error){
   alert("Something went wrong Please try again")
 },()=>{
  this.router.navigate(['signin']);

})
}

  constructor(private signinservice:SigninServiceService,private router:Router) { }

  ngOnInit(): void {
  }

}
