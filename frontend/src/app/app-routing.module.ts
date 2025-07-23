import { ServiceUpdateComponent } from './components/service/service-update/service-update.component';
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { HomeComponent } from "./views/home/home.component";
import { ServiceCreateComponent } from './components/service/service-create/service-create.component';
import { ServiceReadComponent } from './components/service/service-read/service-read.component';


const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "services/create",
    component: ServiceCreateComponent
  },
  {
      path: "services/read",
      component: ServiceReadComponent
  },
  {
    path: "services/update",
    component: ServiceUpdateComponent
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
