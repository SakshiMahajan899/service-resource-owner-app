import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ServiceReadComponent } from './service-read.component';
import { ServiceManager } from './../service.manager';
import { of } from 'rxjs';
import { Service } from './../service.model';

describe('ServiceReadComponent', () => {
  let component: ServiceReadComponent;
  let fixture: ComponentFixture<ServiceReadComponent>;
  let mockServiceManager: jasmine.SpyObj<ServiceManager>;

  beforeEach(() => {
    mockServiceManager = jasmine.createSpyObj('ServiceManager', ['readById']);

    TestBed.configureTestingModule({
      declarations: [ServiceReadComponent],
      providers: [{ provide: ServiceManager, useValue: mockServiceManager }]
    });

    fixture = TestBed.createComponent(ServiceReadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call serviceManager.readById and assign result to service', () => {
    const mockData: Service = {
      id: 1,
      name: 'Test Service',
      description: 'A mock service description'
    };

    component.serviceId = 1;
    mockServiceManager.readById.and.returnValue(of(mockData));

    component.readServiceById();

    expect(mockServiceManager.readById).toHaveBeenCalledWith(1);
    expect(component.service).toEqual(mockData);
  });
});
