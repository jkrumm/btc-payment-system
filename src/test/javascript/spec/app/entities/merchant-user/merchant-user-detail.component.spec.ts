import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BtcPaymentSystemTestModule } from '../../../test.module';
import { MerchantUserDetailComponent } from 'app/entities/merchant-user/merchant-user-detail.component';
import { MerchantUser } from 'app/shared/model/merchant-user.model';

describe('Component Tests', () => {
  describe('MerchantUser Management Detail Component', () => {
    let comp: MerchantUserDetailComponent;
    let fixture: ComponentFixture<MerchantUserDetailComponent>;
    const route = ({ data: of({ merchantUser: new MerchantUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BtcPaymentSystemTestModule],
        declarations: [MerchantUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MerchantUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MerchantUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load merchantUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.merchantUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
