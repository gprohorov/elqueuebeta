export class Patient {
    id: string;
    person: {
        firstName: string;
        patronymic: string;
        lastName: string;
        cellPhone: string;
        town: string;
        address: string;
        gender: string;
        dateOfBirth: string;
    };
    therapy: {};
    talons: [{}];
    lastActivity: string;
    startActivity: string;
}
