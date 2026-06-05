package cl.usm.gestionPeliculasMemoria.controllers;

import cl.usm.gestionPeliculasMemoria.entities.Comentario;
import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import cl.usm.gestionPeliculasMemoria.services.PeliculasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PeliculasControllerTest {

    PeliculasController peliculasController;

    @Mock
    PeliculasService peliculasService;

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
        this.peliculasController = new PeliculasController(peliculasService);
    }

    @Test
    void getAllOkSinFiltro() {
        Pelicula pelicula = crearPeliculaTest();
        when(peliculasService.getAll()).thenReturn(List.of(pelicula));

        ResponseEntity<List<Pelicula>> response = peliculasController.getAll(null);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllOkConFiltro() {
        Pelicula pelicula = crearPeliculaTest();
        when(peliculasService.filter("Term")).thenReturn(List.of(pelicula));
        ResponseEntity<List<Pelicula>> response = peliculasController.getAll("Term");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllNokErrorInterno() {
        when(peliculasService.getAll()).thenThrow(new RuntimeException("Falla catastrófica del sistema"));
        ResponseEntity<List<Pelicula>> response = peliculasController.getAll(null);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void createPeliculaOk() {
        Pelicula pelicula = crearPeliculaTest();
        when(peliculasService.createPelicula(any(Pelicula.class))).thenReturn(pelicula);
        ResponseEntity<?> response = peliculasController.createPelicula(pelicula);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createPeliculaNok() {
        Pelicula pelicula = crearPeliculaTest();
        when(peliculasService.createPelicula(any(Pelicula.class))).thenReturn(null);
        ResponseEntity<?> response = peliculasController.createPelicula(pelicula);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void findByIdOk() {
        Pelicula pelicula = crearPeliculaTest();
        when(peliculasService.findById("1")).thenReturn(pelicula);
        ResponseEntity<Pelicula> response = peliculasController.findById("1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().getId());
    }

    @Test
    void findByIdNokNoEncontrado() {
        when(peliculasService.findById("99")).thenReturn(null);
        ResponseEntity<Pelicula> response = peliculasController.findById("99");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findByIdNokErrorInterno() {
        when(peliculasService.findById(anyString())).thenThrow(new RuntimeException("Error db"));
        ResponseEntity<Pelicula> response = peliculasController.findById("1");

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    void getComentariosOk() {
        Pelicula pelicula = crearPeliculaTest();
        when(peliculasService.findById("1")).thenReturn(pelicula);
        ResponseEntity<?> response = peliculasController.getComentarios("1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getComentariosNokNoEncontrado() {
        when(peliculasService.findById("99")).thenReturn(null);
        ResponseEntity<?> response = peliculasController.getComentarios("99");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getComentariosNokErrorInterno() {
        when(peliculasService.findById(anyString())).thenThrow(new RuntimeException("Error db"));
        ResponseEntity<?> response = peliculasController.getComentarios("1");

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
