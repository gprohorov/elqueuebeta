export class Patient {
    id: string;
    person: Person;
    therapy: {};
    talons: [{}];
    activity: string;
    lastActivity: string;
    startActivity: string;
    balance: number;
    appointed: number;
    recomendation: string;
    recomendationName: string;
    desc: string;
    
    constructor() {
        this.person = new Person();
    }
}

export class Person {
    fullName: string;
    cellPhone: string;
    town: string;
    address: string;
    gender: string;
    dateOfBirth: string;
}
