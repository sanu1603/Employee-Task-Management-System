import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: false,
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {

  signupForm!: FormGroup;
  hidePassword=true;

  constructor(private fb:FormBuilder ,
    private authService:AuthService,
    private snackbar:MatSnackBar,
    private router:Router
  ){
    this.signupForm = this.fb.group({
      name: [null, [Validators.required]],
      email:[null,[Validators.required,
        Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)
      ]],
      password:[null,[Validators.required]],
      confirmPassword:[null,[Validators.required]],
  })
}

togglePasswordVisibility(){
    this.hidePassword=!this.hidePassword;
}

onSubmit(){
  console.log(this.signupForm.value)
  const password=this.signupForm.get("password")?.value;
  const confirmPassword=this.signupForm.get("confirmPassword")?.value;
  if(password !==confirmPassword){
    this.snackbar.open("Passwords do not match", "Close", { duration: 5000, panelClass:"error-snackbar"});
    return;
    }   

    this.authService.signup(this.signupForm.value).subscribe((res)=>{
      console.log(res);
      if(res.id != null){
        this.snackbar.open("Signup successfully", "Close", { duration: 5000 });
        this.router.navigateByUrl("/login");
      } else{
        this.snackbar.open("Signup failed. Try again","Close", { duration: 5000, panelClass:"error-snackbar"})
      }
    })
  }
}