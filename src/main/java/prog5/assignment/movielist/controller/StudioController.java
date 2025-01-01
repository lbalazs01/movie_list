package prog5.assignment.movielist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prog5.assignment.movielist.bean.StudioDTO;
import prog5.assignment.movielist.service.StudioService;
import prog5.assignment.movielist.exception.custom.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/studios")
public class StudioController {

    private final StudioService studioService;

    @Autowired
    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    @PostMapping
    public ResponseEntity<StudioDTO> createStudio(@RequestBody StudioDTO studioDTO) {
        StudioDTO savedStudio = studioService.saveStudio(studioDTO);
        return new ResponseEntity<>(savedStudio, HttpStatus.CREATED);
    }

    @GetMapping("/studios")
    public List<StudioDTO> getAllStudios() {
        return studioService.getAllStudios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudioDTO> getStudioById(@PathVariable Long id) {
        try {
            StudioDTO studioDTO = studioService.getStudioById(id);
            return new ResponseEntity<>(studioDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudioDTO> updateStudio(@PathVariable Long id, @RequestBody StudioDTO studioDTO) {
        try {
            StudioDTO updatedStudio = studioService.updateStudio(id, studioDTO);
            return new ResponseEntity<>(updatedStudio, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long id) {
        try {
            studioService.deleteStudio(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
