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
    OrderSearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
  

    HttpClientModule
     
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
