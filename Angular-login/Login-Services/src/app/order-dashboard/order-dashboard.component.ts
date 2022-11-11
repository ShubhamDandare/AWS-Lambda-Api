import { Component, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
// import { OrderSubmissionComponent } from '../order-submission/order-submission.component';
import { SaveOrderService } from '../save-order.service';

@Component({
  selector: 'app-order-dashboard',
  templateUrl: './order-dashboard.component.html',
  styleUrls: ['./order-dashboard.component.css']
})
export class OrderDashboardComponent implements OnInit {
  ordersList : Array <any> = [];
  
  uploadFile() {
    this.router.navigate(['uploadfile']);
  }

  
  
  constructor(private router : Router, private service :SaveOrderService) {
    if(history.state.ordersList){
      this.ordersList = history.state.ordersList;
    }
   }
  ngOnInit(): void {
  }

}
