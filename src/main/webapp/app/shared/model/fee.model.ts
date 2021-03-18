import { FeeType } from 'app/shared/model/enumerations/fee-type.model';

export interface IFee {
  id?: number;
  feeType?: FeeType;
  percent?: number;
  percentSecure?: number;
}

export const defaultValue: Readonly<IFee> = {};
