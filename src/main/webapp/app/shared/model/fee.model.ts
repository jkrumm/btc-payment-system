import { FeeType } from 'app/shared/model/enumerations/fee-type.model';

export interface IFee {
  id?: number;
  feeType?: FeeType;
  percent?: number;
  percentSecure?: number;
}

export class Fee implements IFee {
  constructor(public id?: number, public feeType?: FeeType, public percent?: number, public percentSecure?: number) {}
}
