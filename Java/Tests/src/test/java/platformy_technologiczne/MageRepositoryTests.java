package platformy_technologiczne;

import org.junit.After;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MageRepositoryTests {

    @Test
    public void Find_NotExistObject_OptionalEmpty() {

        // Given:
        MageRepository mageRepository = new MageRepository();

        // When:
        Optional<Mage> mage = mageRepository.find("Mage");

        // Then:
        assertThat(mage).isEqualTo(Optional.empty())
                .as("Should be Empty");


    }

    @Test
    public void findExist() {

        // Given:
        ArrayList<Mage> mages = new ArrayList<>();
        String name = "mage";
        int level = 5;
        Mage mage = Mage.builder().name(name).level(level).build();
        mages.add(mage);
        MageRepository mageRepository = new MageRepository(mages);

        // When:
        Optional<Mage> mageCheck = mageRepository.find(name);

        // Then:
        assertThat(mageCheck).isEqualTo(Optional.of(mage))
                .as("Should be equal");
    }

    @Test
    public void deleteNotFound() {

        // Given:
        String name = "mage";
        MageRepository mageRepository = new MageRepository();

        // When:
        Throwable thrown = catchThrowable(()-> { mageRepository.delete(name); });

        // Then
        assertThat(thrown).hasMessage("IllegalArgumentException");
    }

    @Test
    public void saveIncorrect() {

        // Given:
        ArrayList<Mage> mages = new ArrayList<>();
        String name = "mage";
        int level = 5;
        Mage mage = Mage.builder().name(name).level(level).build();
        mages.add(mage);
        MageRepository mageRepository = new MageRepository(mages);
        // When:
        Throwable thrown = catchThrowable(()-> { mageRepository.save(mage); });

        // Then
        assertThat(thrown).hasMessage("IllegalArgumentException");
    }

}
