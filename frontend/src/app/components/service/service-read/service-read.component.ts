import { Component, OnInit } from '@angular/core';
import { ServiceManager } from "./../service.manager";
import { Service } from "./../service.model";

@Component({
  selector: 'app-service-read',
  templateUrl: './service-read.component.html',
  styleUrls: ['./service-read.component.css']
})
export class ServiceReadComponent implements OnInit {

    serviceId: number;
    service: Service | null = null;
  constructor(private serviceManager: ServiceManager) { }

  ngOnInit(): void {
  }


   readServiceById(): void {
      this.serviceManager.readById(this.serviceId).subscribe((service) => {
             this.service = service;
           });
    }




}
