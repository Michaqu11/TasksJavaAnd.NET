package platformy_technologiczne;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MageControllerTests {

    @Test
    public void deleteNotFound (){
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);
        String name = "mage";

        when(mageRepository.find(name)).thenReturn(Optional.empty());
        doThrow(new IllegalArgumentException("IllegalArgumentException")).when(mageRepository).delete("mage");
        String result = mageController.delete("mage");
        verify(mageRepository, times(1)).delete(eq(name));
        assertThat(result).isEqualTo("Not found");
    }


    @Test
    public void deleteFound() {

        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);
        String name = "mage";
        int level = 5;

        when(mageRepository.find(name)).thenReturn(Optional.of(Mage.builder().name(name).level(level).build()));
        String result = mageController.delete(name);
        verify(mageRepository,times(1)).delete(eq(name));
        assertThat(result).isEqualTo("Done");
    }

    @Test
    public void findNotExist(){

        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);
        String name = "mage";
        when(mageRepository.find(name)).thenReturn(Optional.empty());
        String result = mageController.find(name);

        verify(mageRepository,times(1)).find(eq(name));
        assertThat(result).isEqualTo("Not found");
    }

    @Test
    public void findExist(){
        String name = "mage";
        int level = 5;
        Mage mage = Mage.builder().name(name).level(level).build();
        String resultExpect = mage.toString();

        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);

        when(mageRepository.find(name)).thenReturn(Optional.of(mage));
        String result = mageController.find(name);
        verify(mageRepository,times(1)).find(eq(name));
        assertThat(result).isEqualTo(resultExpect);
    }

    @Test
    public void saveCorrect(){
        String name = "mage";
        int level = 5;
        Mage mage = Mage.builder().name(name).level(level).build();

        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);
        when(mageRepository.find(name)).thenReturn(Optional.empty());
        String result = mageController.save(mage.getName(),mage.getLevel());
        verify(mageRepository,times(1)).save(eq(mage));
        assertThat(result).isEqualTo("Done");
    }

    @Test
    public void saveIncorrect(){

        String name = "mage";
        int level = 5;
        Mage mage = Mage.builder().name(name).level(level).build();
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);

        when(mageRepository.find(name)).thenReturn(Optional.of(mage));
        doThrow(new IllegalArgumentException("IllegalArgumentException")).when(mageRepository).save(mage);
        String result = mageController.save(name,level);
        verify(mageRepository,times(1)).save(eq(mage));
        assertThat(result).isEqualTo("Bad request");
    }
}
