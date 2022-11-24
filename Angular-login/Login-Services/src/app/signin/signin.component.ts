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
  fileContent : string | undefined;

  onFileSelected(event : any) {
    this.convertFile(event.target.files[0]).subscribe((base64: string | undefined) => {
      this.fileContent = base64;
      console.log(this.fileContent )
      
      this.uploadFile();
    });
  }

  uploadFile() {
    const observable = this.orderService.uploadFile(this.fileContent) ;
      observable.subscribe(
        (response : any) => {
          console.log('response***********'+response)
          let navigationExtras: NavigationExtras = {
            state: {
              ordersList: response
            }
          };
          this.router.navigate(['orderdashboard'], navigationExtras);
         alert("File upload Success");
        },(error) => {
          alert(error);
        }
      )
    
  }

  convertFile(file : File) : Observable<string> {
    const result = new ReplaySubject<string>(1);
    const reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = (event:any) => result.next(btoa(event.target.result.toString()));
    return result;
  }

  constructor(private  orderService: SaveOrderService,
    private router: Router) { }

  
  ngOnInit(): void {
  }
}


  

 


