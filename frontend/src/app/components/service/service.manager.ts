import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { HttpClient } from "@angular/common/http";
import { Service } from "./service.model";

import { BackendResponse } from "./response.model";
import { Observable, EMPTY } from "rxjs";
import { map, catchError } from "rxjs/operators";

@Injectable({
  providedIn: "root",
})
export class ServiceManager {
baseUrl = "http://localhost:8080/v1/services";

  constructor(private snackBar: MatSnackBar, private http: HttpClient) {}

  showMessage(msg: string, isError: boolean = false): void {
    this.snackBar.open(msg, "X", {
      duration: 3000,
      horizontalPosition: "right",
      verticalPosition: "top",
      panelClass: isError ? ["msg-error"] : ["msg-success"],
    });
  }


 create(service: Service): Observable<Service> {
    return this.http.post<Service>(this.baseUrl, service).pipe(
      map((obj) => obj),
      catchError((e) => this.errorHandler(e))
    );
  }

readById(id: number): Observable<Service> {
  const url = `${this.baseUrl}/${id}`;
  return this.http.get<BackendResponse<Service>>(url).pipe(
    map((response) => response.data),
    catchError((e) => this.errorHandler(e))
  );
}


  update(service: Service): Observable<Service> {
    const url = `${this.baseUrl}/${service.id}`;
    return this.http.put<Service>(url, service).pipe(
      map((obj) => obj),
      catchError((e) => this.errorHandler(e))
    );
  }



  errorHandler(e: any): Observable<any> {
    this.showMessage("An error has occurred!", true);
    return EMPTY;
  }
}
