import API from "./API";
import {ratingApi} from "./rating.api";

class MovieApi {
    async getAll() {
        let movies = [];
        try {
            movies = await API.get('/movies');
            movies = movies.data;
        } catch (e) {
            console.error(e);
        }
        return movies;
    }

    async getForGenre(genreId) {
        let movies = [];
        try {
            movies = await API.get(`/genres/${genreId}/movies`);
            movies = movies.data;
        } catch (e) {
            console.error(e);
        }
        return movies;
    }

    async getMovieComments(movieId) {
        let comments = [];
        try {
            comments = await API.get(`/movies/${movieId}/comments`);
            comments = comments.data
        } catch (e) {
            console.error(e);
        }

        return comments;
    }

    async getMovieRatings(movieId) {
        let ratings = [];
        try {
            ratings = await API.get(`/movies/${movieId}/ratings`);
            ratings = ratings.data
        } catch (e) {
            console.error(e);
        }

        return ratings;
    }

    async getMovieDirectors(movieId) {
        let directors = [];
        try {
            directors = await API.get(`/movies/${movieId}/directors`);
            directors = directors.data
        } catch (e) {
            console.error(e);
        }

        return directors;
    }

    async getMovieActors(movieId) {
        let actors = [];
        try {
            actors = await API.get(`/movies/${movieId}/actors`);
            actors = actors.data
        } catch (e) {
            console.error(e);
        }

        return actors;
    }

    async addRatings(movies) {
        let ratings = [];
        try {
            ratings = await ratingApi.getAll();
        } catch (e) {
            console.error(e);
        }

        for (let movie of movies) {
            const movieRatings = ratings.filter(r => r.movie.id === movie.id);
            const sum = movieRatings.reduce((a, b) => +a + +b.value, 0);
            if (movieRatings.length > 0) {
                movie.rating = sum/movieRatings.length
            } else {
                movie.rating = 0
            }
        }
    }
}

export const movieApi = new MovieApi();