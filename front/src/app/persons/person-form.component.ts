import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription'; 

import { Person } from '../_models/index';
import { AlertService, PersonService } from '../_services/index';

@Component({
    moduleId: module.id,
    templateUrl: 'person-form.component.html'
})

export class PersonFormComponent {
    model: any = {};
    personsSub: Subscription;
    loading = false;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private personService: PersonService,
        private alertService: AlertService) { }

    ngOnInit() {
      let id = this.route.snapshot.paramMap.get('id');
      if (id) this.load(+id);
    }
    
    ngOnDestroy() {
      if (this.personsSub) this.personsSub.unsubscribe();
    } 
  
    load(id: number) {
        this.loading = true;
        this.personsSub = this.personService.getPerson(id)
            .subscribe(
                data => {
                    data.gender = data.gender.toString();
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
        if (this.model.id) {
          this.personService.updatePerson(this.model)
              .subscribe(
                  data => {
                      this.alertService.success('Операція пройшла успішно', true);
                      this.router.navigate(['persons']);
                  },
                  error => {
                      this.alertService.error(error);
                      this.loading = false;
                  });
        } else {
          this.personService.createPerson(this.model)
              .subscribe(
                  data => {
                      this.alertService.success('Операція пройшла успішно', true);
                      this.router.navigate(['persons']);
                  },
                  error => {
                      this.alertService.error(error);
                      this.loading = false;
                  });
        }
    }
}
