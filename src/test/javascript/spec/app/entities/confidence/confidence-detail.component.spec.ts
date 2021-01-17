import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BtcPaymentSystemTestModule } from '../../../test.module';
import { ConfidenceDetailComponent } from 'app/entities/confidence/confidence-detail.component';
import { Confidence } from 'app/shared/model/confidence.model';

describe('Component Tests', () => {
  describe('Confidence Management Detail Component', () => {
    let comp: ConfidenceDetailComponent;
    let fixture: ComponentFixture<ConfidenceDetailComponent>;
    const route = ({ data: of({ confidence: new Confidence(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BtcPaymentSystemTestModule],
        declarations: [ConfidenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConfidenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConfidenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load confidence on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.confidence).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
