package platformy_technologiczne;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Log
public class MageRepository {
    private Collection<Mage> collection;

    public MageRepository(){
        collection = new ArrayList<>();
    }
    public MageRepository(ArrayList<Mage> mages){
        collection = mages;
    }

    public Optional<Mage> find(String name) {
        Optional<Mage> mage = collection.stream()
                .filter(value-> name.equals(value.getName()))
                .findFirst();
        return mage;
    }
    public void delete(String name) {

        Optional<Mage> mage = this.find(name);

        if (!mage.isEmpty()) {
            collection.remove(mage.get());
        } else {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
    }
    public void save(Mage mage) {
            Optional<Mage> mage_ex = this.find(mage.getName());
                if(mage_ex.isEmpty()) {
                    collection.add(mage);
                }
                else {
                    throw new IllegalArgumentException("IllegalArgumentException");
                }
            }
}
