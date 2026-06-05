package cl.usm.gestionPeliculasMemoria;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestionPeliculasMemoriaApplicationTest {

    @Test
    void main() {
        assertThrows(IllegalArgumentException.class,()->{
            GestionPeliculasMemoriaApplication.main(null);
        });

    }
}