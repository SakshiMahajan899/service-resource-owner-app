import { Router, ActivatedRoute } from "@angular/router";
import { ServiceManager } from "./../service.manager";
import { Component, OnInit } from "@angular/core";
import { Service } from "./../service.model";

@Component({
  selector: "app-service-update",
  templateUrl: "./service-update.component.html",
  styleUrls: ["./service-update.component.css"],
})
export class ServiceUpdateComponent implements OnInit {
enteredId: number;
  constructor(
    private serviceManager: ServiceManager,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
  }

  /**
     * Fetch service details from backend by entered ID
     */
    loadService(): void {
      if (!this.enteredId) {
        this.serviceManager.showMessage('Please enter a valid Service ID');
        return;
      }

      this.serviceManager.readById(this.enteredId).subscribe({
        next: data => {
          this.service = data;
        },
        error: err => {
          this.serviceManager.showMessage('Service not found');
          this.service = null;
        }
      });
    }

  updateService(): void {
    this.serviceManager.update(this.service).subscribe(() => {
      this.serviceManager.showMessage("Service updated successfully!");
      this.router.navigate(["/services"]);
    });
  }

  cancel(): void {
    this.router.navigate(["/services"]);
  }
  service: Service = {
    id: '',
    resources: []
  };

  addResource() {
    this.service.resources.push({
      id: '',
      owners: [{
        id: '',
        name: '',
        accountNumber: '',
        level: 1
      }]
    });
  }

  addOwner(resIdx: number) {
    this.service.resources[resIdx].owners.push({
      id: '',
      name: '',
      accountNumber: '',
      level: 1
    });
  }


}
