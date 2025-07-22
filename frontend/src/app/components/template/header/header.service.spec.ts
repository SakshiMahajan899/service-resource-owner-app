import { TestBed } from '@angular/core/testing';
import { HeaderService } from './header.service';
import { HeaderData } from './header-data.model';

describe('HeaderService', () => {
  let service: HeaderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HeaderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should have default header data', () => {
    expect(service.headerData).toEqual({
      title: 'Home',
      icon: 'home',
      routeUrl: ''
    });
  });

  it('should update header data via setter', () => {
    const newHeader: HeaderData = {
      title: 'Dashboard',
      icon: 'dashboard',
      routeUrl: '/dashboard'
    };

    service.headerData = newHeader;
    expect(service.headerData).toEqual(newHeader);
  });

  it('should emit header data changes', (done) => {
    const newHeader: HeaderData = {
      title: 'Profile',
      icon: 'person',
      routeUrl: '/profile'
    };

    service['_headerData'].subscribe(data => {
      expect(data).toEqual(newHeader);
      done();
    });

    service.headerData = newHeader;
  });
});
