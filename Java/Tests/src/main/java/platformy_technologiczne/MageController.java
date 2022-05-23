package platformy_technologiczne;

import java.util.Optional;

public class MageController {
    private MageRepository repository;

    public MageController(MageRepository repository) {
        this.repository = repository;
    }
    public String find(String name) {
        Optional<Mage> mage =  repository.find(name);
        if (mage.isEmpty()) return "Not found";
        else return mage.get().toString();
    }
    public String delete(String name) {
        try {
            repository.delete(name);
            return "Done";
        } catch(IllegalArgumentException ex) {
            return "Not found";
        }
    }
    public String save(String name, int level) {
            Mage mage = Mage.builder()
                    .name(name)
                    .level(level)
                    .build();
        try {
            repository.save(mage);
            return "Done";
        } catch(IllegalArgumentException ex) {
            return "Bad request";
        }
    }
}
