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
  convertedToOrder : Array <any> = [];
  // isprofileActivate :false | undefined;
  uploadFile() {
    this.router.navigate(['uploadfile']);
  }

  edit(order: any){
    let navigationExtras: NavigationExtras = {
      state: {
        order: order
      }
    };
    this.router.navigate(['updateorder'], navigationExtras);
  }
  fetchOrder(){
    this.router.navigate(['searchorder']);
  }
  showAll(){
    const observable = this.service.fetchOrders("", "", true);
    observable.subscribe(
        (response: any) => {
        let navigationExtras: NavigationExtras = {
          state: {
            convertedToOrder: response
          }
        };
        this.router.navigate(['orderlist'], navigationExtras);
      } ,(error) => {
      alert(error);
      }
    )
  }
  constructor(private router : Router, private service :SaveOrderService) {
    if(history.state.convertedToOrder){
      this.convertedToOrder = history.state.convertedToOrder;
    }
   }
  ngOnInit(): void {
  }

}
