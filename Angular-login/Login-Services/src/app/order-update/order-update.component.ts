import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SaveOrderService } from '../save-order.service';

@Component({
  selector: 'app-order-update',
  templateUrl: './order-update.component.html',
  styleUrls: ['./order-update.component.css']
})
export class OrderUpdateComponent implements OnInit {

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
  edit() {
    console.log(this.order);
    const observable = this.orderService.editOrder(this.order);
    observable.subscribe(
      (response : any) => {
        console.log(response);
       alert("order updated successfully");
       this.router.navigate(['orderdashboard']);
     
      },(error) => {
        alert(error);
      })
  }
  constructor(private  orderService:SaveOrderService, private router: Router) { }

  ngOnInit(): void {
  }

}
