import dayjs from 'dayjs';
import { ITransaction } from 'app/shared/model/transaction.model';
import { ConfidenceType } from 'app/shared/model/enumerations/confidence-type.model';

export interface IConfidence {
  id?: number;
  changeAt?: string;
  confidenceType?: ConfidenceType;
  confirmations?: number;
  transaction?: ITransaction | null;
}

export const defaultValue: Readonly<IConfidence> = {};
