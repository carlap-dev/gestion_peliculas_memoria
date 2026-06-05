package cl.usm.gestionPeliculasMemoria.services;

import cl.usm.gestionPeliculasMemoria.entities.Comentario;
import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import cl.usm.gestionPeliculasMemoria.repositories.PeliculasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PeliculasServiceImplTest {

    PeliculasServiceImpl peliculasService;

    @Mock
    PeliculasRepository peliculasRepository ;

    private Pelicula crearPeliculaTest() {
        Pelicula pelicula = new Pelicula();
        pelicula.setId("1");
        pelicula.setTitulo("Terminator");
        pelicula.setDirector("James Cameron");
        pelicula.setTokenDescarga("token123");
        pelicula.setComentarios(new Comentario[0]);

        return pelicula;
    }

    @BeforeEach
    void setUp() {
        this.peliculasService = new PeliculasServiceImpl(peliculasRepository);
    }

    @Test
    void createPeliculaOk() {

        Pelicula pelicula = crearPeliculaTest();
        when(peliculasRepository.insert(any(Pelicula.class))).thenReturn(pelicula);
        Pelicula result = peliculasService.createPelicula(pelicula);

        assertNotNull(result);
        assertEquals("Terminator", result.getTitulo());
        assertNotNull(result.getTokenDescarga());
    }

    @Test
    void createPeliculaNok() {
        Pelicula pelicula = crearPeliculaTest();
        when(peliculasRepository.insert(any(Pelicula.class))).thenThrow(new RuntimeException("Error en la base de datos"));
        Pelicula result = peliculasService.createPelicula(pelicula);

        assertNull(result);
    }

    @Test
    void getAll() {
        Pelicula pelicula = crearPeliculaTest();
        when(peliculasRepository.findAll()).thenReturn(List.of(pelicula));
        List<Pelicula> result = peliculasService.getAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllNok() {
        when(peliculasRepository.findAll()).thenThrow(new RuntimeException("Error en base de datos"));
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            peliculasService.getAll();
        });

        assertEquals("Error en base de datos", ex.getMessage());
    }

    @Test
    void findByIdOk() {
        Pelicula pelicula = crearPeliculaTest();
        when(peliculasRepository.findById(anyString())).thenReturn(pelicula);
        Pelicula result = peliculasService.findById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    void findByIdNok() {
        when(peliculasRepository.findById(anyString())).thenThrow(new RuntimeException("Id no válido"));
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            peliculasService.findById("1");
        });

        assertEquals("Id no válido", ex.getMessage());
    }

    @Test
    void filterOk() {
        Pelicula pelicula = crearPeliculaTest();
        when(peliculasRepository.findAll()).thenReturn(List.of(pelicula));
        List<Pelicula> result = peliculasService.filter("Termi");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void filterNok() {
        when(peliculasRepository.findAll()).thenThrow(new RuntimeException("Error filtrando"));
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            peliculasService.filter("termi");
        });

        assertEquals("Error filtrando", ex.getMessage());
    }
}