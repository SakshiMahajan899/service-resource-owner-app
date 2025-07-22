import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ServiceCreateComponent } from './service-create.component';
import { ServiceManager } from './../service.manager';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

describe('ServiceCreateComponent', () => {
  let component: ServiceCreateComponent;
  let fixture: ComponentFixture<ServiceCreateComponent>;
  let mockServiceManager: jasmine.SpyObj<ServiceManager>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockHttp: jasmine.SpyObj<HttpClient>;

  beforeEach(() => {
    mockServiceManager = jasmine.createSpyObj('ServiceManager', ['create', 'showMessage']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);
    mockHttp = jasmine.createSpyObj('HttpClient', ['post']);

    TestBed.configureTestingModule({
      declarations: [ServiceCreateComponent],
      providers: [
        { provide: ServiceManager, useValue: mockServiceManager },
        { provide: Router, useValue: mockRouter },
        { provide: HttpClient, useValue: mockHttp }
      ]
    });

    fixture = TestBed.createComponent(ServiceCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should show message and not submit if service has no valid owner details', () => {
    // Clear owner data
    component.service.resources[0].owners[0].name = '';
    component.service.resources[0].owners[0].accountNumber = '';

    component.createService();

    expect(mockServiceManager.showMessage).toHaveBeenCalledWith('Please fill service details before saving');
    expect(mockServiceManager.create).not.toHaveBeenCalled();
  });

  it('should submit service when valid owner data is present', () => {
    component.service.resources[0].owners[0].name = 'Alice';
    component.service.resources[0].owners[0].accountNumber = 'ACC123';
    mockServiceManager.create.and.returnValue(of({}));

    component.createService();

    expect(mockServiceManager.create).toHaveBeenCalledWith(component.service);
    expect(mockServiceManager.showMessage).toHaveBeenCalledWith('Service created!');
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/services']);
  });

  it('should navigate to /services when cancel is called', () => {
    component.cancel();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/services']);
  });

  it('should add a new resource with one owner', () => {
    const initialLength = component.service.resources.length;
    component.addResource();

    expect(component.service.resources.length).toBe(initialLength + 1);
    expect(component.service.resources[initialLength].owners.length).toBe(1);
  });

  it('should add a new owner to a given resource index', () => {
    const initialOwners = component.service.resources[0].owners.length;
    component.addOwner(0);

    expect(component.service.resources[0].owners.length).toBe(initialOwners + 1);
  });
});
