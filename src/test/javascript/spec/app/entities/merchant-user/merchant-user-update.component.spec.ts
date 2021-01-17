import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BtcPaymentSystemTestModule } from '../../../test.module';
import { MerchantUserUpdateComponent } from 'app/entities/merchant-user/merchant-user-update.component';
import { MerchantUserService } from 'app/entities/merchant-user/merchant-user.service';
import { MerchantUser } from 'app/shared/model/merchant-user.model';

describe('Component Tests', () => {
  describe('MerchantUser Management Update Component', () => {
    let comp: MerchantUserUpdateComponent;
    let fixture: ComponentFixture<MerchantUserUpdateComponent>;
    let service: MerchantUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BtcPaymentSystemTestModule],
        declarations: [MerchantUserUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MerchantUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MerchantUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MerchantUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MerchantUser(123);
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
        const entity = new MerchantUser();
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
