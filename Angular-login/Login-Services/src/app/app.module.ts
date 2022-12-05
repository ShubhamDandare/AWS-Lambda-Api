import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { HeaderComponent } from './header/header.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { OrderSubmissionComponent } from './order-submission/order-submission.component';
import { OrderDashboardComponent } from './order-dashboard/order-dashboard.component';
import { CsvparserComponent } from './csvparser/csvparser.component';
import { OrderUpdateComponent } from './order-update/order-update.component';
import { OrderSearchComponent } from './order-search/order-search.component';
import { OrderListComponent } from './order-list/order-list.component';
import { RouterModule } from '@angular/router';
import { SimpleNotificationsModule } from 'angular2-notifications';
import { AuthGuardService } from './auth-guard.service';
//import { NgxCsvParserModule } from 'ngx-csv-parser';
//import { NgxCsvParserModule } from 'ngx-csv-parser';
// import { OrderSubmissionComponent } from './order-submission/order-submission.component';
//import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    SigninComponent,
    SignupComponent,
    HeaderComponent,
    LoginComponent,
    OrderSubmissionComponent,
    OrderDashboardComponent,
    CsvparserComponent,
    OrderUpdateComponent,
    OrderSearchComponent,
    OrderListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    SimpleNotificationsModule.forRoot({ timeOut: 3500})
  ],
  providers: [AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
