import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BtcPaymentSystemTestModule } from '../../../test.module';
import { ConfidenceUpdateComponent } from 'app/entities/confidence/confidence-update.component';
import { ConfidenceService } from 'app/entities/confidence/confidence.service';
import { Confidence } from 'app/shared/model/confidence.model';

describe('Component Tests', () => {
  describe('Confidence Management Update Component', () => {
    let comp: ConfidenceUpdateComponent;
    let fixture: ComponentFixture<ConfidenceUpdateComponent>;
    let service: ConfidenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BtcPaymentSystemTestModule],
        declarations: [ConfidenceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConfidenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConfidenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConfidenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Confidence(123);
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
        const entity = new Confidence();
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
