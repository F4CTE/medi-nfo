import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { AddressService } from 'app/address/address.service';
import { AddressDTO } from 'app/address/address.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-address-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './address-add.component.html'
})
export class AddressAddComponent implements OnInit {

  addressService = inject(AddressService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  userValues?: Map<number,string>;

  addForm = new FormGroup({
    street: new FormControl(null, [Validators.required, Validators.maxLength(200)]),
    zipcode: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
    country: new FormControl(null, [Validators.required, Validators.maxLength(255)]),
    city: new FormControl(null, [Validators.maxLength(100)]),
    user: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@address.create.success:Address was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.addressService.getUserValues()
        .subscribe({
          next: (data) => this.userValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new AddressDTO(this.addForm.value);
    this.addressService.createAddress(data)
        .subscribe({
          next: () => this.router.navigate(['/addresses'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
