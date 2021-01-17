import { ConfidenceType } from 'app/shared/model/enumerations/confidence-type.model';

export interface IConfidence {
  id?: number;
  confidenceType?: ConfidenceType;
  confirmations?: number;
  transactionId?: number;
}

export class Confidence implements IConfidence {
  constructor(public id?: number, public confidenceType?: ConfidenceType, public confirmations?: number, public transactionId?: number) {}
}
