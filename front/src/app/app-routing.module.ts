import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CallbackComponent } from './callback.component';
import { PublicDealsComponent } from './public-deals/public-deals.component';
import { PrivateDealsComponent } from './private-deals/private-deals.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'deals',
    pathMatch: 'full'
  },
  {
    path: 'deals',
    component: PublicDealsComponent
  },
  {
    path: 'special',
    component: PrivateDealsComponent
  },
  {
    path: 'callback',
    component: CallbackComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
