import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BtcPaymentSystemTestModule } from '../../../test.module';
import { MerchantUpdateComponent } from 'app/entities/merchant/merchant-update.component';
import { MerchantService } from 'app/entities/merchant/merchant.service';
import { Merchant } from 'app/shared/model/merchant.model';

describe('Component Tests', () => {
  describe('Merchant Management Update Component', () => {
    let comp: MerchantUpdateComponent;
    let fixture: ComponentFixture<MerchantUpdateComponent>;
    let service: MerchantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BtcPaymentSystemTestModule],
        declarations: [MerchantUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MerchantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MerchantUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MerchantService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Merchant(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Merchant();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
