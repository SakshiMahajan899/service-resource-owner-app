import { ServiceManager } from "./../service.manager";
import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";

import { HttpClient } from "@angular/common/http";

@Component({
  selector: "app-service-create",
  templateUrl: "./service-create.component.html",
  styleUrls: ["./service-create.component.css"],
})
export class ServiceCreateComponent implements OnInit {

  constructor(private serviceManager: ServiceManager, private router: Router,private http: HttpClient) {}

  ngOnInit(): void {}

    /**
     * Submits the service object to the backend via POST.
     */

  createService(): void {
//     if (!this.service.id.trim()) {
//       this.serviceManager.showMessage("Please enter a valid Service Details !!!");
//       return;
//     }

    const hasValidOwners = this.service.resources.some(resource =>
//       resource.id.trim() &&
      resource.owners.some(owner =>
        owner.name.trim() && owner.accountNumber.trim()
      )
    );

    if (!hasValidOwners) {
      this.serviceManager.showMessage("Please fill service details before saving");
      return;
    }

    this.serviceManager.create(this.service).subscribe(() => {
      this.serviceManager.showMessage("Service created!");
      this.router.navigate(["/services"]);
    });
  }


  cancel(): void {
    this.router.navigate(["/services"]);
  }

service = {
    id: '',
    resources: [
      {
        id: '',
        owners: [
          {
            id: '',
            name: '',
            accountNumber: '',
            level: 1
          }
        ]
      }
    ]
  };

  /**
   * Adds a new empty resource to the service.
   */
  addResource(): void {
    this.service.resources.push({
      id: '',
      owners: [
        {
          id: '',
          name: '',
          accountNumber: '',
          level: 1
        }
      ]
    });
  }

  /**
   * Adds a new empty owner to the specified resource index.
   * @param index The index of the resource in the array
   */
  addOwner(index: number): void {
    this.service.resources[index].owners.push({
      id: '',
      name: '',
      accountNumber: '',
      level: 1
    });
  }

}
