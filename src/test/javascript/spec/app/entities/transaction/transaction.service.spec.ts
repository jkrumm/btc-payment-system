import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TransactionService } from 'app/entities/transaction/transaction.service';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';

describe('Service Tests', () => {
  describe('Transaction Service', () => {
    let injector: TestBed;
    let service: TransactionService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransaction;
    let expectedResult: ITransaction | ITransaction[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TransactionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Transaction(
        0,
        currentDate,
        TransactionType.INCOMING_UNKNOWN,
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            initiatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Transaction', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            initiatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            initiatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Transaction()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Transaction', () => {
        const returnedFromService = Object.assign(
          {
            initiatedAt: currentDate.format(DATE_TIME_FORMAT),
            transactionType: 'BBBBBB',
            isMempool: true,
            txHash: 'BBBBBB',
            fromAddress: 'BBBBBB',
            toAddress: 'BBBBBB',
            expectedAmount: 1,
            amount: 1,
            serviceFee: 1,
            btcPrice: 1,
            isWithdrawed: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            initiatedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Transaction', () => {
        const returnedFromService = Object.assign(
          {
            initiatedAt: currentDate.format(DATE_TIME_FORMAT),
            transactionType: 'BBBBBB',
            isMempool: true,
            txHash: 'BBBBBB',
            fromAddress: 'BBBBBB',
            toAddress: 'BBBBBB',
            expectedAmount: 1,
            amount: 1,
            serviceFee: 1,
            btcPrice: 1,
            isWithdrawed: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            initiatedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Transaction', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
