import { Moment } from 'moment';
import { IConfidence } from 'app/shared/model/confidence.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';

export interface ITransaction {
  id?: number;
  initiatedAt?: Moment;
  transactionType?: TransactionType;
  isMempool?: boolean;
  txHash?: string;
  fromAddress?: string;
  toAddress?: string;
  expectedAmount?: number;
  amount?: number;
  serviceFee?: number;
  btcPrice?: number;
  isWithdrawed?: boolean;
  confidences?: IConfidence[];
  userId?: number;
  blockId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public initiatedAt?: Moment,
    public transactionType?: TransactionType,
    public isMempool?: boolean,
    public txHash?: string,
    public fromAddress?: string,
    public toAddress?: string,
    public expectedAmount?: number,
    public amount?: number,
    public serviceFee?: number,
    public btcPrice?: number,
    public isWithdrawed?: boolean,
    public confidences?: IConfidence[],
    public userId?: number,
    public blockId?: number
  ) {
    this.isMempool = this.isMempool || false;
    this.isWithdrawed = this.isWithdrawed || false;
  }
}
