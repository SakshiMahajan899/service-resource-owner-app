import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { HeaderService } from './../../components/template/header/header.service';
import { HeaderData } from './../../components/template/header/header-data.model';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let mockHeaderService: jasmine.SpyObj<HeaderService>;

  beforeEach(() => {
    mockHeaderService = jasmine.createSpyObj('HeaderService', [], {
      headerData: {
        title: '',
        icon: '',
        routeUrl: ''
      }
    });

    TestBed.configureTestingModule({
      declarations: [HomeComponent],
      providers: [{ provide: HeaderService, useValue: mockHeaderService }]
    });

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should set headerData to Home on construction', () => {
    const expectedHeader: HeaderData = {
      title: 'Home',
      icon: 'home',
      routeUrl: ''
    };

    expect(mockHeaderService.headerData).toEqual(expectedHeader);
  });

  it('should execute ngOnInit without error', () => {
    expect(() => component.ngOnInit()).not.toThrow();
  });
});
