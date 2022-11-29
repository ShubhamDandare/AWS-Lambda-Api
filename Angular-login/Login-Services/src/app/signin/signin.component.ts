import { Component, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { SigninServiceService } from '../signin-service.service';
//import { NotificationService } from "./notification.service";
import { NotificationsService, SimpleNotificationsModule } from 'angular2-notifications';
import { SaveOrderService } from '../save-order.service';
import { Observable, ReplaySubject } from 'rxjs';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit{
  

  

  constructor(private  orderService: SaveOrderService,
    private router: Router) { }

  
  ngOnInit(): void {
  }
}


  

 


