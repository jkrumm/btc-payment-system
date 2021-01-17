import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BtcPaymentSystemTestModule } from '../../../test.module';
import { FeeUpdateComponent } from 'app/entities/fee/fee-update.component';
import { FeeService } from 'app/entities/fee/fee.service';
import { Fee } from 'app/shared/model/fee.model';

describe('Component Tests', () => {
  describe('Fee Management Update Component', () => {
    let comp: FeeUpdateComponent;
    let fixture: ComponentFixture<FeeUpdateComponent>;
    let service: FeeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BtcPaymentSystemTestModule],
        declarations: [FeeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FeeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FeeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fee(123);
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
        const entity = new Fee();
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
