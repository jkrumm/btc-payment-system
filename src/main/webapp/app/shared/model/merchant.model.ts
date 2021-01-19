import { IFee } from 'app/shared/model/fee.model';
import { IMerchantUser } from 'app/shared/model/merchant-user.model';

export interface IMerchant {
  id?: number;
  name?: string;
  email?: string;
  fee?: IFee | null;
  merchantUsers?: IMerchantUser[] | null;
}

export const defaultValue: Readonly<IMerchant> = {};
