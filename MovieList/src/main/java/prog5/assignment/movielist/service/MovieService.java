package prog5.assignment.movielist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prog5.assignment.movielist.entity.Movie;
import prog5.assignment.movielist.bean.MovieDTO;
import prog5.assignment.movielist.exception.custom.EntityNotFoundException;
import prog5.assignment.movielist.exception.custom.BadRequestException;
import prog5.assignment.movielist.repository.MovieRepository;
import prog5.assignment.movielist.service.mapping.MovieListMapper;
import prog5.assignment.movielist.service.mapping.MovieMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieListMapper movieListMapper;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper, MovieListMapper movieListMapper) {
        this.movieRepository = movieRepository;
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
}
