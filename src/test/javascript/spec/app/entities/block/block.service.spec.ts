import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BlockService } from 'app/entities/block/block.service';
import { IBlock, Block } from 'app/shared/model/block.model';

describe('Service Tests', () => {
  describe('Block Service', () => {
    let injector: TestBed;
    let service: BlockService;
    let httpMock: HttpTestingController;
    let elemDefault: IBlock;
    let expectedResult: IBlock | IBlock[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BlockService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Block(0, currentDate, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            minedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Block', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            minedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            minedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Block()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Block', () => {
        const returnedFromService = Object.assign(
          {
            minedAt: currentDate.format(DATE_TIME_FORMAT),
            block: 1,
            available: 1,
            estimated: 1,
            availableSpendable: 1,
            estimatedSpendable: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            minedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Block', () => {
        const returnedFromService = Object.assign(
          {
            minedAt: currentDate.format(DATE_TIME_FORMAT),
            block: 1,
            available: 1,
            estimated: 1,
            availableSpendable: 1,
            estimatedSpendable: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            minedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Block', () => {
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
