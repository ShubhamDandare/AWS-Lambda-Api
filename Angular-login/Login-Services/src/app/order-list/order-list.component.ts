import { Component, Input, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  @Input()
  list : Array <any> = [];
  constructor(private router : Router) { 
    if(history.state.list.length > 0 ){
      this.list = history.state.list;
    }
  }

  edit(order :any){
    let navigationExtras: NavigationExtras = {
      state: {
        order: order
      }
    };
    this.router.navigate(['updateorder'], navigationExtras);
  }
  ngOnInit(): void {
  }

}
