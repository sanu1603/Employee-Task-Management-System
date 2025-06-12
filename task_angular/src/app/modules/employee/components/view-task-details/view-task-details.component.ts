import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
//import { AdminService } from '../../../admin/services/admin.service';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-view-task-details',
  standalone: false,
  templateUrl: './view-task-details.component.html',
  styleUrl: './view-task-details.component.scss'
})
export class ViewTaskDetailsComponent {

  taskId !: number;
  taskdata: any;
  comments : any;
  commentForm !:FormGroup

  constructor (private service:EmployeeService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private snackbar:MatSnackBar
  ) {   }

  ngOnInit(){
    this.taskId = this.activatedRoute.snapshot.params['id'];
    this.getTaskById();
    this.getComments();
    this.commentForm = this.fb.group({
      content : [null, Validators.required]
    })
    
  }

  getTaskById(){
    this.service.getTaskById(this.taskId).subscribe((res) =>{
      this.taskdata=res;
    })
  }

  getComments(){
    this.service.getCommentsByTask(this.taskId).subscribe((res) =>{
      this.comments=res;
    })
  }

  publichComment(){
    this.service.createComment(this.taskId,this.commentForm.get("content")?.value).subscribe((res)=>{
      if(res.id != null){
        this.snackbar.open("Comment Posted Successfully", "Close", {duration:5000});
        this.getComments();
      } else{
          this.snackbar.open("Something Went Wrong", "Close", {duration:5000});      
      }
    })
  }
}
