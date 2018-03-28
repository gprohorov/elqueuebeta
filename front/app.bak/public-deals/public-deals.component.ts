import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { Deal } from '../deal';
// We haven't defined these services yet
//import { AuthService } from '../auth.service';
import { DealService } from '../deal.service';

@Component({
  selector: 'app-public-deals',
  // We'll use an external file for both the CSS styles and HTML view
  templateUrl: 'public-deals.component.html',
  styleUrls: ['public-deals.component.css']
})
export class PublicDealsComponent implements OnInit, OnDestroy {
  dealsSub: Subscription;
  publicDeals: Deal[];
  error: any;

  // Note: We haven't implemented the Deal or Auth Services yet.
  constructor(
    public dealService: DealService,
    //public authService: AuthService
    ) {
  }

  // When this component is loaded, we'll call the dealService and get our public deals.
  ngOnInit() {
    this.dealsSub = this.dealService
      .getPublicDeals()
      .subscribe(
        deals => this.publicDeals = deals,
        err => this.error = err
      );
  }

  ngOnDestroy() {
    this.dealsSub.unsubscribe();
  }
}