import { TestBed } from '@angular/core/testing';
import { ServiceManager } from './service.manager';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Service } from './service.model';
import { BackendResponse } from './response.model';

describe('ServiceManager', () => {
  let service: ServiceManager;
  let httpMock: HttpTestingController;
  let snackBarSpy: jasmine.SpyObj<MatSnackBar>;

  beforeEach(() => {
    snackBarSpy = jasmine.createSpyObj('MatSnackBar', ['open']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        ServiceManager,
        { provide: MatSnackBar, useValue: snackBarSpy }
      ]
    });

    service = TestBed.inject(ServiceManager);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should show a success message', () => {
    service.showMessage('Success');
    expect(snackBarSpy.open).toHaveBeenCalledWith('Success', 'X', jasmine.any(Object));
  });

  it('should make POST request to create service', () => {
    const dummyService: Service = { id: '123', resources: [] };

    service.create(dummyService).subscribe(result => {
      expect(result).toEqual(dummyService);
    });

    const req = httpMock.expectOne('http://localhost:8080/v1/services');
    expect(req.request.method).toBe('POST');
    req.flush(dummyService);
  });

  it('should make GET request to fetch service by ID', () => {
    const dummyService: Service = { id: '456', resources: [] };
    const response: BackendResponse<Service> = {
      success: true,
      data: dummyService,
      message: 'Fetched'
    };

    service.readById(456).subscribe(result => {
      expect(result).toEqual(dummyService);
    });

    const req = httpMock.expectOne('http://localhost:8080/v1/services/456');
    expect(req.request.method).toBe('GET');
    req.flush(response);
  });

  it('should make PUT request to update service', () => {
    const updatedService: Service = { id: '789', resources: [] };

    service.update(updatedService).subscribe(result => {
      expect(result).toEqual(updatedService);
    });

    const req = httpMock.expectOne('http://localhost:8080/v1/services/789');
    expect(req.request.method).toBe('PUT');
    req.flush(updatedService);
  });

  it('should handle errors and show error message', () => {
    const dummyService: Service = { id: '999', resources: [] };

    service.update(dummyService).subscribe(result => {
      expect(result).toBeUndefined(); // EMPTY returns undefined
    });

    const req = httpMock.expectOne('http://localhost:8080/v1/services/999');
    req.flush('Error occurred', { status: 500, statusText: 'Server Error' });

    expect(snackBarSpy.open).toHaveBeenCalledWith('An error has occurred!', 'X', jasmine.any(Object));
  });
});
