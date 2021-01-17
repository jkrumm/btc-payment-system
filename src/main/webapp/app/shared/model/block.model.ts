import { Moment } from 'moment';
import { ITransaction } from 'app/shared/model/transaction.model';

export interface IBlock {
  id?: number;
  minedAt?: Moment;
  block?: number;
  available?: number;
  estimated?: number;
  availableSpendable?: number;
  estimatedSpendable?: number;
  transactions?: ITransaction[];
}

export class Block implements IBlock {
  constructor(
    public id?: number,
    public minedAt?: Moment,
    public block?: number,
    public available?: number,
    public estimated?: number,
    public availableSpendable?: number,
    public estimatedSpendable?: number,
    public transactions?: ITransaction[]
  ) {}
}
