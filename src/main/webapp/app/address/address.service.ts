import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { AddressDTO } from 'app/address/address.model';
import { PagedModel } from 'app/common/list-helper/pagination.component';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class AddressService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/addresses';

  getAllAddresses(params?: Record<string,string>) {
    return this.http.get<PagedModel<AddressDTO>>(this.resourcePath, { params });
  }

  getAddress(id: number) {
    return this.http.get<AddressDTO>(this.resourcePath + '/' + id);
  }

  createAddress(addressDTO: AddressDTO) {
    return this.http.post<number>(this.resourcePath, addressDTO);
  }

  updateAddress(id: number, addressDTO: AddressDTO) {
    return this.http.put<number>(this.resourcePath + '/' + id, addressDTO);
  }

  deleteAddress(id: number) {
    return this.http.delete(this.resourcePath + '/' + id);
  }

  getUserValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/userValues')
        .pipe(map(transformRecordToMap));
  }

}
