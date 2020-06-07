import React, {useEffect, useState} from 'react';
import {Box, Grid} from '@material-ui/core';
import MovieGridItem from "../components/movies/MovieGridItem";
import API from "../utils/API";
import {useParams} from "react-router-dom";

export default function Movies(props) {
    const [movies, setMovies] = useState([]);
    const [genreName, setGenreName] = useState(null);
    const urlParams = useParams();

    useEffect(() => {
        const fetchData = async () => {
            const genres = await API.get('/genres')
            const genre = genres.data.find(genre => genre.name.toLowerCase() === urlParams.genreName);

            let url = '/movies';
            if (genre) {
                setGenreName(genre.name);
                url = `/genres/${genre.id}/movies`
            }
            let result = await API.get(url);
            let movies = result.data;
            result = await API.get('/ratings');
            const ratings = result.data;
            for (let movie of movies) {
                const movieRatings = ratings.filter(r => r.movie.id === movie.id);
                const sum = movieRatings.reduce((a, b) => +a + +b.value, 0);
                if (movieRatings.length > 0) {
                    movie.rating = sum/movieRatings.length
                } else {
                    movie.rating = 'brak ocen'
                }
            }
            setMovies(movies);
        };

        fetchData();
    }, []);

    return (
        <Box m={2}>
            <h2>{genreName}</h2>
            <Grid container spacing={5}>
                {movies.map(movie => (
                    <MovieGridItem movie={movie}/>
                ))}
            </Grid>
        </Box>
    );
}