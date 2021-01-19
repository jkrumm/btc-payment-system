import { IUser } from 'app/shared/model/user.model';
import { IMerchant } from 'app/shared/model/merchant.model';

export interface IMerchantUser {
  id?: number;
  user?: IUser | null;
  merchant?: IMerchant | null;
}

export const defaultValue: Readonly<IMerchantUser> = {};
