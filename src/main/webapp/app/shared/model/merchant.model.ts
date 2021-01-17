import { IMerchantUser } from 'app/shared/model/merchant-user.model';

export interface IMerchant {
  id?: number;
  name?: string;
  email?: string;
  feeId?: number;
  merchantUsers?: IMerchantUser[];
}

export class Merchant implements IMerchant {
  constructor(
    public id?: number,
    public name?: string,
    public email?: string,
    public feeId?: number,
    public merchantUsers?: IMerchantUser[]
  ) {}
}
