import com.mycompany.paniclab.Model.Board;
import com.mycompany.paniclab.Model.Enums.Direzione;
import com.mycompany.paniclab.Utils.GeneratoreCarte;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class PJTest
{
    @Test
    public void testProssimaPosizioneBoard() {
        Board board = new Board();
        
        int prossimoOrario = board.prossimaPosizione(24, Direzione.ORARIO);
        assertEquals(0, prossimoOrario, "L'indice orario oltre il bordo deve tornare a 0");
        
        int prossimoAntiorario = board.prossimaPosizione(0, Direzione.ANTIORARIO);
        assertEquals(24, prossimoAntiorario, "L'indice antiorario sotto lo zero deve tornare a 24");
    }

    @Test
    public void testGenerazioneMazzo() {
        var mazzo = GeneratoreCarte.creaMazzoRandom();
        
        assertNotNull(mazzo);
        assertEquals(25, mazzo.size(), "Il mazzo generato deve contenere esattamente 25 carte");
    }    
}