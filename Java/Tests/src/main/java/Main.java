import platformy_technologiczne.Mage;
import platformy_technologiczne.MageController;
import platformy_technologiczne.MageRepository;

public class Main {

    public static void main(String[] args) {
        MageRepository mageRepository = new MageRepository();
        MageController mageController = new MageController(mageRepository);

        Mage firstMage = new Mage("mage", 10);
        Mage secondMage = new Mage("mage1", 10);
        Mage thirdMage = new Mage("mage3", 10);
        /*mageRepository.save(firstMage);
        mageRepository.save(secondMage);
        mageRepository.delete("unknownMage");
        mageRepository.delete("unknownMage");*/
    }
}
