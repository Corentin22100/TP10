import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutConcertComponent } from './ajout-concert.component';

describe('AjoutConcertComponent', () => {
  let component: AjoutConcertComponent;
  let fixture: ComponentFixture<AjoutConcertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AjoutConcertComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AjoutConcertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
