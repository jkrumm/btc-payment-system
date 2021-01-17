import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BtcPaymentSystemTestModule } from '../../../test.module';
import { MerchantDetailComponent } from 'app/entities/merchant/merchant-detail.component';
import { Merchant } from 'app/shared/model/merchant.model';

describe('Component Tests', () => {
  describe('Merchant Management Detail Component', () => {
    let comp: MerchantDetailComponent;
    let fixture: ComponentFixture<MerchantDetailComponent>;
    const route = ({ data: of({ merchant: new Merchant(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BtcPaymentSystemTestModule],
        declarations: [MerchantDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MerchantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MerchantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load merchant on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.merchant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
