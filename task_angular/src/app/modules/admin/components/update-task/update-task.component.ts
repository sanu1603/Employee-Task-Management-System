import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-task',
  standalone: false,
  templateUrl: './update-task.component.html',
  styleUrl: './update-task.component.scss'
})
export class UpdateTaskComponent {

  id:number;
  updateTaskForm!: FormGroup;
  listOfEmployee: any=[];
  listOfPriorities: any=["LOW","MEDIUM","HIGH"];
  listOfTaskStatus: any=["INPROGRESS","COMPLETED"];

  constructor(private service: AdminService,
    private route: ActivatedRoute,
     private fb: FormBuilder,
     private adminService:AdminService,
     private snackBar: MatSnackBar,
     private router:Router
  ) { 
    this.id=this.route.snapshot.params['id'];
    this.getTaskById();
    this.getUsers();
     this.updateTaskForm = this.fb.group({
      employeeId:[null,[Validators.required]],
      title:[null,[Validators.required]],
      description:[null,[Validators.required]],
      dueDate:[null,[Validators.required]],
      priority:[null,[Validators.required]],
      //taskStatus:[null,[Validators.required]]
    })
  }

  getTaskById(){
    this.service.getTaskById(this.id).subscribe((res)=>{
      this.updateTaskForm.patchValue(res);
      console.log(res);
    })
  }

   getUsers(){
    this.adminService.getUsers().subscribe(res => {
       this.listOfEmployee = res;
       console.log("Employees:", this.listOfEmployee);
       console.log(res);
    })
  }

  updateTask(){
    console.log("Form Submitted",this.updateTaskForm.value);
    this.adminService.updateTask(this.id,this.updateTaskForm.value).subscribe((res)=>{
      if(res.id != null){
        this.snackBar.open('Task Updated Successfully', 'close', {duration : 5000});
        this.router.navigateByUrl("/admin/dashboard");
  } else{
    this.snackBar.open('Something Went Wrong', 'Error', {duration : 5000});

  }
})
}

}
