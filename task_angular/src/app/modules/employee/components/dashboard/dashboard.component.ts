import { Component } from '@angular/core';
import { AdminService } from '../../../admin/services/admin.service';
import { EmployeeService } from '../../services/employee.service';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  listOfTasks: any=[];

  constructor(private service:EmployeeService,
    private snackbar:MatSnackBar
  ){
    this.getTasks();
  }
  getTasks() {
    this.service.getEmployeeTasksById().subscribe((res)=> {
      console.log(res);
      this.listOfTasks = res;
    })
  }
  
  updateStatus(id:number,status:string) {
    this.service.updateStatus(id,status).subscribe((res)=>{
      if(res.id != null) {
        this.snackbar.open("task status updated successfully", "Close", { duration:5000});
        this.getTasks();
      } else{
        this.snackbar.open("task status update failed", "Close", { duration:5000});
      }
    })
  }
 
}
