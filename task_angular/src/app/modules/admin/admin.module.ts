import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DemoAngularMaterialModule } from '../../DemoAngularMaterialModule';
import { MatFormField, MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostTaskComponent } from './components/post-task/post-task.component';
import { UpdateTaskComponent } from './components/update-task/update-task.component';
import { ViewTaskDetailsComponent } from './components/view-task-details/view-task-details.component';
@NgModule({
  declarations: [
    DashboardComponent,
    PostTaskComponent,
    UpdateTaskComponent,
    ViewTaskDetailsComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule,HttpClientModule,
    DemoAngularMaterialModule,
    MatFormFieldModule,
    MatInputModule
  ]
})
export class AdminModule { }
