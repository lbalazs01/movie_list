package prog5.assignment.movielist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prog5.assignment.movielist.bean.ActorDTO;
import prog5.assignment.movielist.service.ActorService;
import prog5.assignment.movielist.exception.custom.EntityNotFoundException;

import java.util.List;

@RestController // indicating it as a REST controller
@RequestMapping("/api/actors") // specifying URL for navigation
public class ActorController {

    private final ActorService actorService;

    @Autowired // Dependency Injection
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public ResponseEntity<ActorDTO> createActor(@RequestBody ActorDTO actorDTO) {
        // creating new actor
        ActorDTO savedActor = actorService.saveActor(actorDTO);
        return new ResponseEntity<>(savedActor, HttpStatus.CREATED);
    }

    @GetMapping
    // getting all actors
    public List<ActorDTO> getAllActors() {
        return actorService.getAllActors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDTO> getActorById(@PathVariable Long id) {
        // getting actors by id
        try {
            ActorDTO actorDTO = actorService.getActorById(id);
            return new ResponseEntity<>(actorDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // handling with custom exception
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}") // mapping put request to a specific id
    // updating actor
    public ResponseEntity<ActorDTO> updateActor(@PathVariable Long id, @RequestBody ActorDTO actorDTO) {
        try {
            ActorDTO updatedActor = actorService.updateActor(id, actorDTO);
            return new ResponseEntity<>(updatedActor, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    // deleting actor
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        try {
            actorService.deleteActor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
