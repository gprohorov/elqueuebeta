export class Person {
    id: string;
    firstName: string;
    patronymic: string;
    lastName: string;
    cellPhone: string;
    town: string;
    address: string;
    gender: string;
    dateOfBirth: string;
}

export class Patient {
    id: string;
    person: Person;
    therapy: {};
    talons: [{}];
    lastActivity: string;
    startActivity: string;
}