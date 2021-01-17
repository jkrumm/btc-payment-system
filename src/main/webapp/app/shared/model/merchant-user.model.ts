export interface IMerchantUser {
  id?: number;
  userId?: number;
  merchantId?: number;
}

export class MerchantUser implements IMerchantUser {
  constructor(public id?: number, public userId?: number, public merchantId?: number) {}
}
