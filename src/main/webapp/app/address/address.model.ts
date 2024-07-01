export class AddressDTO {

  constructor(data:Partial<AddressDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  street?: string|null;
  zipcode?: string|null;
  country?: string|null;
  city?: string|null;
  user?: number|null;

}
