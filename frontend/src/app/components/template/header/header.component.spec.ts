import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HeaderComponent } from './header.component';
import { HeaderService } from './header.service';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let headerServiceStub: Partial<HeaderService>;

  beforeEach(() => {
    headerServiceStub = {
      headerData: {
        title: 'Dashboard',
        icon: 'home',
        routeUrl: '/dashboard'
      }
    };

    TestBed.configureTestingModule({
      declarations: [HeaderComponent],
      providers: [{ provide: HeaderService, useValue: headerServiceStub }]
    });

    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create HeaderComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should return title from HeaderService', () => {
    expect(component.title).toBe('Dashboard');
  });

  it('should return icon from HeaderService', () => {
    expect(component.icon).toBe('home');
  });

  it('should return routeUrl from HeaderService', () => {
    expect(component.routeUrl).toBe('/dashboard');
  });
});
