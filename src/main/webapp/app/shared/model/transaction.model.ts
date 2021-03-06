import dayjs from 'dayjs';
import { IConfidence } from 'app/shared/model/confidence.model';
import { IUser } from 'app/shared/model/user.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';

export interface ITransaction {
  id?: number;
  initiatedAt?: string;
  transactionType?: TransactionType | null;
  txHash?: string | null;
  expectedAmount?: number | null;
  actualAmount?: number | null;
  transactionFee?: number | null;
  serviceFee?: number | null;
  btcEuro?: number | null;
  address?: string | null;
  amount?: number | null;
  confidences?: IConfidence[] | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<ITransaction> = {};
