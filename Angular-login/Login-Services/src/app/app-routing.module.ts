import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CsvparserComponent } from './csvparser/csvparser.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { OrderDashboardComponent } from './order-dashboard/order-dashboard.component';
import { OrderSearchComponent } from './order-search/order-search.component';
import { OrderSubmissionComponent } from './order-submission/order-submission.component';
import { OrderUpdateComponent } from './order-update/order-update.component';
// import { OrderSubmissionComponent } from './order-submission/order-submission.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
  { path: '', component: HeaderComponent},
  { path: 'signin', component: SigninComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'login', component: LoginComponent },
  { path: 'uploadfile', component: OrderSubmissionComponent },
  { path: 'orderdashboard', component: OrderDashboardComponent },
  { path: 'csv', component: CsvparserComponent },
  { path: 'updateorder', component: OrderUpdateComponent },
  { path: 'searchorder', component: OrderSearchComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
