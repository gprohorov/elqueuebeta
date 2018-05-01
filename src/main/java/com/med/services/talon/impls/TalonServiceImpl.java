package com.med.services.talon.impls;

import com.med.services.talon.interfaces.ITalonService;
import org.springframework.stereotype.Component;

/**
 * Created by george on 3/9/18.
 */
@Component
public class TalonServiceImpl implements ITalonService {
/*


    private List<Generic> generics = new ArrayList<>();

    @Autowired
    TalonRepository repository;

    @PostConstruct
    void init(){
       // generics = dataStorage.getGenerics();
    }


    @Override
    public Talon createTalon(Talon talon) {

        return repository.save(talon);
    }

    @Override
    public Talon updateTalon(Talon talon) {
        return null;
    }

    @Override
    public Talon getTalon(ObjectId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Talon deleteTalon(long id) {

        Talon talon = repository.findById(id).orElse(null);
        repository.deleteById(id);

        return talon;
    }

    @Override
    public List<Talon> getAll() {
        return repository.findAll();
    }

    public List<Talon> getAlForToday() {

        return this.getAll()
                .stream().filter(talon -> talon.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public List<Talon> getAlForPatient(Patient patient) {

        return this.getAll()
                .stream().filter(talon ->talon.getPatientId()==patient.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Talon> getAllTalonsForPatientForToday(Patient patient) {

        return this.getAlForToday().stream()
                .filter(talon -> talon.getPatientId()==patient.getId())
                .collect(Collectors.toList());

    }

*/

}
