import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ServiceUpdateComponent } from './service-update.component';
import { ServiceManager } from './../service.manager';
import { Router, ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';
import { Service } from './../service.model';

describe('ServiceUpdateComponent', () => {
  let component: ServiceUpdateComponent;
  let fixture: ComponentFixture<ServiceUpdateComponent>;
  let mockServiceManager: jasmine.SpyObj<ServiceManager>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockRoute: jasmine.SpyObj<ActivatedRoute>;

  beforeEach(() => {
    mockServiceManager = jasmine.createSpyObj('ServiceManager', ['readById', 'update', 'showMessage']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);
    mockRoute = jasmine.createSpyObj('ActivatedRoute', [], { snapshot: { params: {} } });

    TestBed.configureTestingModule({
      declarations: [ServiceUpdateComponent],
      providers: [
        { provide: ServiceManager, useValue: mockServiceManager },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockRoute }
      ]
    });

    fixture = TestBed.createComponent(ServiceUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should show a message when serviceId is not entered', () => {
    component.enteredId = null as any;
    component.loadService();
    expect(mockServiceManager.showMessage).toHaveBeenCalledWith('Please enter a valid Service ID');
  });

  it('should load service by entered ID', () => {
    const mockService: Service = { id: '123', resources: [] };
    component.enteredId = 123;
    mockServiceManager.readById.and.returnValue(of(mockService));

    component.loadService();

    expect(mockServiceManager.readById).toHaveBeenCalledWith(123);
    expect(component.service).toEqual(mockService);
  });

  it('should handle error if service not found', () => {
    component.enteredId = 999;
    mockServiceManager.readById.and.returnValue(throwError(() => new Error('Not found')));

    component.loadService();

    expect(mockServiceManager.showMessage).toHaveBeenCalledWith('Service not found');
    expect(component.service).toBeNull();
  });

  it('should update the service and navigate', () => {
    component.service = { id: '123', resources: [] };
    mockServiceManager.update.and.returnValue(of({}));

    component.updateService();

    expect(mockServiceManager.update).toHaveBeenCalledWith(component.service);
    expect(mockServiceManager.showMessage).toHaveBeenCalledWith('Service updated successfully!');
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/services']);
  });

  it('should navigate away on cancel', () => {
    component.cancel();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/services']);
  });

  it('should add a new resource with one owner', () => {
    component.service.resources = [];
    component.addResource();

    expect(component.service.resources.length).toBe(1);
    expect(component.service.resources[0].owners.length).toBe(1);
  });

  it('should add an owner to existing resource', () => {
    component.service.resources = [
      { id: 'res1', owners: [] }
    ];

    component.addOwner(0);

    expect(component.service.resources[0].owners.length).toBe(1);
  });
});
