import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { Doctor } from '../_models/index';
import { DoctorService, AlertService } from '../_services/index';

@Component({
    moduleId: module.id,
    templateUrl: './form.component.html'
})

export class DoctorFormComponent {
    model: any = {};
    sub: Subscription;
    loading = false;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private service: DoctorService,
        private alertService: AlertService) { }

    ngOnInit() {
      const id = this.route.snapshot.paramMap.get('id');
      if (id) this.load(+id);
    }

    ngOnDestroy() {
      if (this.sub) this.sub.unsubscribe();
    }

    load(id: number) {
        this.loading = true;
        this.sub = this.service.get(id)
            .subscribe(
                data => {
                    this.model = data;
                    this.loading = false;
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }

    submit() {
        this.loading = true;
          this.service.update(this.model)
              .subscribe(
                  data => {
                      this.alertService.success('Операція пройшла успішно', true);
                      this.router.navigate(['doctors']);
                  },
                  error => {
                      this.alertService.error(error);
                      this.loading = false;
                  });
    }
}
