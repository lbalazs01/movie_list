package prog5.assignment.movielist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prog5.assignment.movielist.entity.Actor;
import prog5.assignment.movielist.entity.Movie;
import prog5.assignment.movielist.bean.MovieDTO;
import prog5.assignment.movielist.entity.Studio;
import prog5.assignment.movielist.exception.custom.EntityNotFoundException;
import prog5.assignment.movielist.exception.custom.BadRequestException;
import prog5.assignment.movielist.repository.MovieRepository;
import prog5.assignment.movielist.repository.ActorRepository;
import prog5.assignment.movielist.repository.StudioRepository;
import prog5.assignment.movielist.service.mapping.MovieListMapper;
import prog5.assignment.movielist.service.mapping.MovieMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final StudioRepository studioRepository;
    private final MovieMapper movieMapper;
    private final MovieListMapper movieListMapper;

    @Autowired
    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository,
                        StudioRepository studioRepository, MovieMapper movieMapper,
                        MovieListMapper movieListMapper) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.studioRepository = studioRepository;
        this.movieMapper = movieMapper;
        this.movieListMapper = movieListMapper;
    }

    public MovieDTO saveMovie(MovieDTO movieDTO) {
        if (movieDTO.getTitle() == null || movieDTO.getTitle().isEmpty()) {
            throw new BadRequestException("Movie title cannot be null or empty");
        }

        Movie movie = movieMapper.toEntity(movieDTO);
        movie = movieRepository.save(movie);
        return movieMapper.toDTO(movie);
    }

    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + id));
        return movieMapper.toDTO(movie);
    }

    public List<MovieDTO> getAllMovies() {
        Iterable<Movie> moviesIterable = movieRepository.findAll();
        List<Movie> movies = StreamSupport.stream(moviesIterable.spliterator(), false)
                .collect(Collectors.toList());
        return movieListMapper.toDTOList(movies);
    }

    public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
        if (movieDTO.getTitle() == null || movieDTO.getTitle().isEmpty()) {
            throw new BadRequestException("Movie title cannot be null or empty");
        }

        if (!movieRepository.existsById(id)) {
            throw new EntityNotFoundException("Movie not found with id: " + id);
        }

        movieDTO.setId(id);
        Movie movie = movieMapper.toEntity(movieDTO);
        movie = movieRepository.save(movie);
        return movieMapper.toDTO(movie);
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new EntityNotFoundException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }

    // New method to handle search for movies based on actor name, studio name, or movie title
    public List<MovieDTO> searchMovies(String query) {
        List<Movie> movies = null;

        // Search for movies by actor name
        List<Actor> actors = actorRepository.findByNameContaining(query);
        if (!actors.isEmpty()) {
            // For each actor, find movies where they are the lead actor
            movies = actors.stream()
                    .flatMap(actor -> movieRepository.findByLeadActorId(actor.getId()).stream())
                    .collect(Collectors.toList());
        } else {
            // Search for movies by studio name if no actors found
            List<Studio> studios = studioRepository.findByNameContaining(query);
            if (!studios.isEmpty()) {
                // For each studio, find movies associated with the studio
                movies = studios.stream()
                        .flatMap(studio -> movieRepository.findByStudioId(studio.getId()).stream())
                        .collect(Collectors.toList());
            } else {
                // Finally, search for movies by title
                movies = movieRepository.searchByTitle(query);
            }
        }

        // Convert the movie entities to DTOs and return
        return movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }
}
