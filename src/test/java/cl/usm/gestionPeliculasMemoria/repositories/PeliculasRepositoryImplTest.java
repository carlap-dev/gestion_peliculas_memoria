package cl.usm.gestionPeliculasMemoria.repositories;

import cl.usm.gestionPeliculasMemoria.entities.Comentario;
import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeliculasRepositoryImplTest {

    PeliculasRepositoryImpl peliculasRepository;

    private Pelicula crearPeliculaTest() {
        Pelicula pelicula = new Pelicula();
        pelicula.setId("1");
        pelicula.setTitulo("Terminator");
        pelicula.setDirector("James Cameron");
        pelicula.setTokenDescarga("token123");
        pelicula.setComentarios(new Comentario[0]);

        return pelicula;
    }

    private Pelicula crearPeliculaSinIdTest() {
        Pelicula pelicula = new Pelicula();
        pelicula.setId(null);
        pelicula.setTitulo("Terminator 2");
        pelicula.setDirector("James Cameron");
        pelicula.setTokenDescarga("token456");
        pelicula.setComentarios(new Comentario[0]);

        return pelicula;
    }

    private Pelicula crearPeliculaDosTest() {
        Pelicula pelicula = new Pelicula();
        pelicula.setId("2");
        pelicula.setTitulo("Matrix");
        pelicula.setDirector("Hermanas Wachowski");
        pelicula.setTokenDescarga("token789");
        pelicula.setComentarios(new Comentario[0]);

        return pelicula;
    }

    @BeforeEach
    void setUp() {
        this.peliculasRepository = new PeliculasRepositoryImpl();
    }

    @Test
    void insertOk() {
        Pelicula pelicula = crearPeliculaTest();
        Pelicula result = peliculasRepository.insert(pelicula);

        assertNotNull(result);
        assertEquals("1", result.getId());

        List<Pelicula> todas = peliculasRepository.findAll();
        assertEquals(1, todas.size());
    }

    @Test
    void insertNokIdNulo() {
        Pelicula pelicula = crearPeliculaSinIdTest();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            peliculasRepository.insert(pelicula);
        });

        assertEquals("El ID de la pelicula no puede ser nulo", ex.getMessage());
    }

    @Test
    void insertNokIdDuplicado() {
        Pelicula pelicula = crearPeliculaTest();
        peliculasRepository.insert(pelicula);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            peliculasRepository.insert(pelicula);
        });

        assertEquals("La pelicula con ID 1 ya existe", ex.getMessage());
    }

    @Test
    void findAllOk() {
        peliculasRepository.insert(crearPeliculaTest());
        peliculasRepository.insert(crearPeliculaDosTest());

        List<Pelicula> result = peliculasRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findAllNok() {
        List<Pelicula> result = peliculasRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByIdOk() {
        peliculasRepository.insert(crearPeliculaTest());
        Pelicula result = peliculasRepository.findById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void findByIdNokNoEncontrado() {
        Pelicula result = peliculasRepository.findById("999");

        assertNull(result);
    }

    @Test
    void findByIdNokIdNulo() {
        Pelicula result = peliculasRepository.findById(null);

        assertNull(result);
    }
}
