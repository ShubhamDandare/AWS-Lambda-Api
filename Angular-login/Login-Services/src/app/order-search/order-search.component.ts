import { Component, Input, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { SaveOrderService } from '../save-order.service';

@Component({
  selector: 'app-order-search',
  templateUrl: './order-search.component.html',
  styleUrls: ['./order-search.component.css']
})
export class OrderSearchComponent implements OnInit {
  @Input()
  convertedToOrder : Array <any> = [];
  customerId :string = "";
  orderId: string = "";

  fetchOrder() {
    const observable = this.orderService.fetchOrders(this.customerId, this.orderId, false);
    observable.subscribe(
        (response: any) => {
          console.log(response)
          let navigationExtras: NavigationExtras = {
            state: {
              convertedToOrder: response
            }
          };
       alert('orders fetch successfully')
       this.router.navigate(['orderlist'], navigationExtras);
      } ,(error) => {
        alert(error);
      }
    )
  }

  constructor(private  orderService:SaveOrderService, private router: Router) {
    
   }


  ngOnInit(): void {
  }

}
